package sku.moamoa.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import sku.moamoa.domain.user.dto.*;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.factory.SimpleClientHttpRequestWithBodyFactory;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.security.SecurityUtil;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GithubRequestService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;
    private final RestTemplate rest = new RestTemplate(new SimpleClientHttpRequestWithBodyFactory());
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
    public ResponseEntity<Void> logout(String accessToken) {
        // WebClient로 delete 요청 시 body 값 설정 불가능 -> restTemplate 사용 -> 구현체의 delete 확장
        URI uri = URI.create("https://api.github.com" + "/applications/" + CLIENT_ID + "/token");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("access_token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.github+json");
        headers.add("X-GitHub-Api-Version", "2022-11-28");
        headers.setBearerAuth(accessToken);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        ResponseEntity<Void> response = rest.exchange( uri,
                HttpMethod.DELETE, new HttpEntity<>("{\"access_token\":\""+accessToken+"\"}", headers), Void.class);
        return response;

//        return webClient.mutate()
//                .baseUrl("https://api.github.com")
//                .build()
//                .delete()
//                .uri("/applications/{client_id}/token", CLIENT_ID)
//                .header("Accept: application/vnd.github+json")
//                .header("X-GitHub-Api-Version: 2022-11-28")
//                .headers(h -> {
//                    h.setBearerAuth(accessToken);
//                    h.setBasicAuth(CLIENT_ID,CLIENT_SECRET);
//                })
//                .retrieve()
//                .bodyToMono(Void.class)
//                .block();
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
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
