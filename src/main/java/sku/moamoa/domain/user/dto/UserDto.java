package sku.moamoa.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.user.entity.AuthProvider;


public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateRequest {
        private String nickname;
        private String email;
        private AuthProvider authProvider;
        private String imageUrl;
        private String portFolioUrl;
        private String githubUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InfoResponse {
        private Long id;
        private String nickname;
        private String imageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DetailInfoResponse {
        private Long id;
        private String nickname;
        private String imageUrl;
        private String githubUrl;
        private String portfolioUrl;
    }
}
