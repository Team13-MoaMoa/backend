package sku.moamoa.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.entity.Post;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePostResponseDto {
    private Long id;
}
