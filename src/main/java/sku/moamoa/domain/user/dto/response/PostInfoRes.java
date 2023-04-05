package sku.moamoa.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostInfoRes {
    private Long id;
    private String nickname;
    private String imageUrl;
}
