package sku.moamoa.domain.likeboard.dto;

import lombok.*;

public class LikeBoardDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateRequest {
        private Long user_id;
    }
}
