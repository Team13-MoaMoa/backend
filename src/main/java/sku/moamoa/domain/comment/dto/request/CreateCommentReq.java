package sku.moamoa.domain.comment.dto.request;

import lombok.*;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommentReq {
    private String content;
    private Long uid;
}
