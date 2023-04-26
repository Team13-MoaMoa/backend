package sku.moamoa.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sku.moamoa.domain.user.dto.GithubUserInfo;
import sku.moamoa.domain.user.dto.SignInResponse;
import sku.moamoa.domain.user.dto.TokenRequest;
import sku.moamoa.domain.user.dto.TokenResponse;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.error.exception.BadRequestException;
import sku.moamoa.global.security.SecurityUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoRequestService kakaoRequestService;
//    private final GithubRequestService githubRequestService;

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public SignInResponse redirect(TokenRequest tokenRequest){
        if(AuthProvider.KAKAO.getAuthProvider().equals(tokenRequest.getRegistrationId())){
            return kakaoRequestService.redirect(tokenRequest);
        }
        else if(AuthProvider.GITHUB.getAuthProvider().equals(tokenRequest.getRegistrationId())){
//            return githubRequestService.redirect(tokenRequest);
        }

        throw new BadRequestException("not supported oauth provider");
    }

    public SignInResponse refreshToken(TokenRequest tokenRequest){
        Long userId = Long.valueOf((String) securityUtil.get(tokenRequest.getRefreshToken()).get("userId"));
        String provider = (String) securityUtil.get(tokenRequest.getRefreshToken()).get("provider");
        String oldRefreshToken = (String) securityUtil.get(tokenRequest.getRefreshToken()).get("refreshToken");

        if(!userRepository.existsByIdAndAuthProvider(userId, AuthProvider.KAKAO)){
            String msg = String.format("CANNOT_FOUND_USER %s", userId.toString());
            throw new BadRequestException(msg);
        }

        TokenResponse tokenResponse = null;
        if(AuthProvider.KAKAO.getAuthProvider().equals(provider.toLowerCase())){
            tokenResponse = kakaoRequestService.getRefreshToken(provider, oldRefreshToken);
        } else if(AuthProvider.GITHUB.getAuthProvider().equals(provider.toLowerCase())){
//            tokenResponse = githubRequestService.getRefreshToken(provider, oldRefreshToken);
        }

        String accessToken = securityUtil.createAccessToken(
                userId, AuthProvider.findByCode(provider.toLowerCase()), tokenResponse.getAccessToken());

        return SignInResponse.builder()
                .authProvider(AuthProvider.findByCode(provider.toLowerCase()))
                .kakaoUserInfo(null)
                .accessToken(accessToken)
                .refreshToken(null)
                .build();
    }
}
