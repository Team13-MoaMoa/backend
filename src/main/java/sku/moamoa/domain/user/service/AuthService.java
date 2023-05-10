package sku.moamoa.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sku.moamoa.domain.user.dto.LogoutRequest;
import sku.moamoa.domain.user.dto.SignInResponse;
import sku.moamoa.domain.user.dto.TokenRequest;
import sku.moamoa.domain.user.dto.TokenResponse;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.error.exception.BadRequestException;
import sku.moamoa.global.security.SecurityUtil;

import java.util.concurrent.TimeUnit;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoRequestService kakaoRequestService;
    private final GithubRequestService githubRequestService;

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public SignInResponse redirect(TokenRequest tokenRequest){
        if(AuthProvider.KAKAO.getAuthProvider().equals(tokenRequest.getRegistrationId())){
            return kakaoRequestService.redirect(tokenRequest);
        }
        else if(AuthProvider.GITHUB.getAuthProvider().equals(tokenRequest.getRegistrationId())){
            return githubRequestService.redirect(tokenRequest);
        }

        throw new BadRequestException("CANNOT_SUPPORTED_OAUTH_PROVIDER");
    }

    public void logout(User user, LogoutRequest logoutRequest){
        Long userId = user.getId();
        String accessToken = logoutRequest.getAccessToken();
        if(AuthProvider.KAKAO.getAuthProvider().equals(logoutRequest.getRegistrationId())) {
            Long id = kakaoRequestService.logout(accessToken);
            log.debug("fdsafdsafdsa"+id.toString());
        }
        else if(AuthProvider.GITHUB.getAuthProvider().equals(logoutRequest.getRegistrationId())){
            githubRequestService.logout(accessToken);
        }
        String refreshToken = redisTemplate.opsForValue().get("id:"+userId).toString();
        if(refreshToken != null) { // redis에서 refresh_token 삭제
            redisTemplate.delete("id:"+userId);
        }
        Long exp = securityUtil.getRefreshTokenExpiresTime(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logout", exp, TimeUnit.MILLISECONDS);
    }

    public SignInResponse refreshToken(Long uid){
        String refreshToken = redisTemplate.opsForValue().get("id:"+uid).toString();
        if(refreshToken == null) {
            throw new BadRequestException("CANNOT_FOUND_REFRESH_TOKEN");
        }
        Long userId = Long.valueOf((String) securityUtil.get(refreshToken).get("userId"));
        String provider = (String) securityUtil.get(refreshToken).get("provider");
        String oauthRefreshToken = (String) securityUtil.get(refreshToken).get("refreshToken");

        if(!userRepository.existsByIdAndAuthProvider(userId, AuthProvider.findByCode(provider))){
            throw new BadRequestException("CANNOT_FOUND_USER");
        }

        TokenResponse tokenResponse = null;
        if(AuthProvider.KAKAO.getAuthProvider().equals(provider.toLowerCase())){
            tokenResponse = kakaoRequestService.getRefreshToken(provider, oauthRefreshToken);
        } else if(AuthProvider.GITHUB.getAuthProvider().equals(provider.toLowerCase())){
            tokenResponse = githubRequestService.getRefreshToken(provider, oauthRefreshToken);
        }

        String accessToken = securityUtil.createAccessToken(
                userId, AuthProvider.findByCode(provider), tokenResponse.getAccessToken());
        String newRefreshToken = securityUtil.createRefreshToken(
                userId, AuthProvider.findByCode(provider), tokenResponse.getRefreshToken());

        return SignInResponse.builder()
                .authProvider(AuthProvider.findByCode(provider))
                .kakaoUserInfo(null)
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
