package sku.moamoa.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.user.entity.LoginPlatform;

public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ApiModel("UserCreateRequest")
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class InfoResponse {
        private Long id;
        private String nickname;
        private String imageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DetailInfoResponse {
        private Long id;
        private String nickname;
        private String imageUrl;
        private String githubUrl;
        private String portfolioUrl;
    }
}
