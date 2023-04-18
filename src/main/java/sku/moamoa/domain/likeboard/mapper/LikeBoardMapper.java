package sku.moamoa.domain.likeboard.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Component
public class LikeBoardMapper {
    public LikeBoard toEntity(User user, Post post) {
        return LikeBoard.builder()
                .user(user)
                .post(post)
                .build();
    }
}
