package sku.moamoa.domain.comment.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Component
public class CommentMapper {
    public  Comment toEntity(User user, Post post, String content){
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
