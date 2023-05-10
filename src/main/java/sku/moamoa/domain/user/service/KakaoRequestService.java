package sku.moamoa.domain.user.service;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import sku.moamoa.domain.user.dto.KakaoUserInfo;
import sku.moamoa.domain.user.dto.SignInResponse;
import sku.moamoa.domain.user.dto.TokenRequest;
import sku.moamoa.domain.user.dto.TokenResponse;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.error.exception.BadRequestException;
import sku.moamoa.global.security.SecurityUtil;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KakaoRequestService{
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.token_uri}")
    private String TOKEN_URI;

    public SignInResponse redirect(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = getToken(tokenRequest);
        KakaoUserInfo kakaoUserInfo = getUserInfo(tokenResponse.getAccessToken());

        if(userRepository.existsById(kakaoUserInfo.getId())){
            String accessToken = securityUtil.createAccessToken(
                    kakaoUserInfo.getId(), AuthProvider.KAKAO, tokenResponse.getAccessToken());
            String refreshToken = securityUtil.createRefreshToken(
                    kakaoUserInfo.getId(), AuthProvider.KAKAO, tokenResponse.getRefreshToken());
            // redis에 id: {user_id(key)} / {refresh_token(value)}형태로 저장
            redisTemplate.opsForValue().set("id:" + kakaoUserInfo.getId(), refreshToken,
                    securityUtil.getRefreshTokenExpiresTime(refreshToken), TimeUnit.MILLISECONDS);
            return SignInResponse.builder()
                    .authProvider(AuthProvider.KAKAO)
                    .kakaoUserInfo(null)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return SignInResponse.builder()
                    .authProvider(AuthProvider.KAKAO)
                    .kakaoUserInfo(kakaoUserInfo)
                    .build();
        }
    }

    public Long logout(String accessToken) {
         return webClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .post()
                .uri("/v1/user/logout")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException("asdf")))
                .bodyToMono(Long.class)
                .block();
    }

    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GRANT_TYPE);
        formData.add("redirect_uri", REDIRECT_URI);
        formData.add("client_id", CLIENT_ID);
        formData.add("code", tokenRequest.getCode());

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("refresh_token", refreshToken);

        return webClient.mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}

