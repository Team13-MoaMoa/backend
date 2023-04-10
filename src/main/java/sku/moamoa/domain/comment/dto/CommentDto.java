package sku.moamoa.domain.comment.dto;

import lombok.*;
import sku.moamoa.domain.user.dto.UserDto;

public class CommentDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateRequest {
        private Long uid;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InfoResponse {
        private Long id;
        private String content;
        private UserDto.InfoResponse user;
    }
}
