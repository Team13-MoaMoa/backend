package sku.moamoa.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import sku.moamoa.domain.user.entity.AuthProvider;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignInResponse {
    private AuthProvider authProvider;
    private KakaoUserInfo kakaoUserInfo;
    private GithubUserInfo githubUserInfo;
    private String accessToken;
    private String refreshToken;

    @Builder
    public SignInResponse(
            AuthProvider authProvider
            ,KakaoUserInfo kakaoUserInfo
            ,GithubUserInfo githubUserInfo
            ,String accessToken
            ,String refreshToken
    ){
        this.authProvider = authProvider;
        this.kakaoUserInfo = kakaoUserInfo;
        this.githubUserInfo = githubUserInfo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

