package sku.moamoa.domain.post.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Component
public class PostMapper {
    public Post toEntity(User user, CreatePostRequestDto body) {
        return Post.builder()
                .title(body.getTitle())
                .projectName(body.getProjectName())
                .content(body.getContent())
                .deadline(body.getDeadline())
                .headcount(body.getHeadcount())
                .jobPosition(body.getJobPosition())
                .user(user)
                .build();
    }
}
