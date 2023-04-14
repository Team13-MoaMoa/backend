package sku.moamoa.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.user.entity.LoginPlatform;

public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateRequest {
        private String nickname;
        private String email;
        private LoginPlatform platform;
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
}
