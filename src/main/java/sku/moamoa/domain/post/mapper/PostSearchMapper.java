package sku.moamoa.domain.post.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.post.entity.TechStack;

@Component
public class PostSearchMapper {

    public PostSearch toEntity(Post post, TechStack techStack){
        return PostSearch.builder()
                .post(post)
                .techStack(techStack)
                .build();
    }

}
