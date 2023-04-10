package sku.moamoa.domain.comment.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.user.dto.UserDto;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentInfoRes {
    private Long id;
    private String content;
    private UserDto.InfoResponse user;
}
