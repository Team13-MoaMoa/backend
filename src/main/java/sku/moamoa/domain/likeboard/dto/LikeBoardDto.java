package sku.moamoa.domain.likeboard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.*;

public class LikeBoardDto {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ApiModel("LikeBoardCreateRequest")
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateRequest {
        private Long userId;
    }
}
