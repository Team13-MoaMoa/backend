package sku.moamoa.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import sku.moamoa.domain.user.dto.*;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.security.SecurityUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GithubRequestService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.provider.github.token_uri}")
    private String TOKEN_URI;

    public SignInResponse redirect(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = getToken(tokenRequest);
        GithubUserInfo githubUserInfo = getUserInfo(tokenResponse.getAccessToken());

        if(userRepository.existsById(githubUserInfo.getId())){
            String accessToken = securityUtil.createAccessToken(
                    githubUserInfo.getId(), AuthProvider.GITHUB, tokenResponse.getAccessToken());
            String refreshToken = securityUtil.createRefreshToken(
                    githubUserInfo.getId(), AuthProvider.GITHUB, tokenResponse.getRefreshToken());
            redisTemplate.opsForValue().set("id:" + githubUserInfo.getId(), refreshToken,
                    securityUtil.getRefreshTokenExpiresTime(refreshToken), TimeUnit.MILLISECONDS);
            return SignInResponse.builder()
                    .authProvider(AuthProvider.GITHUB)
                    .githubUserInfo(null)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return SignInResponse.builder()
                    .authProvider(AuthProvider.GITHUB)
                    .githubUserInfo(githubUserInfo)
                    .build();
        }
    }
    public HttpStatus logout(String accessToken) {
        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .delete()
                .uri("/applications/"+CLIENT_ID+"/token")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(HttpStatus.class)
                .block();
    }
    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", CLIENT_ID);
        formData.add("client_secret", CLIENT_SECRET);
        formData.add("code", tokenRequest.getCode());

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public GithubUserInfo getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl("https://api.github.com")
                .build()
                .get()
                .uri("/user")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GithubUserInfo.class)
                .block();
    }
    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("client_secret",CLIENT_SECRET);
        formData.add("refresh_token", refreshToken);

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
