package sku.moamoa.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.post.entity.TechStack;
import sku.moamoa.domain.post.repository.PostRepository;
import sku.moamoa.domain.post.repository.PostSearchRepository;
import sku.moamoa.domain.post.repository.TechStackRepository;
import sku.moamoa.domain.user.entity.User;

import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostSearchRepository postSearchRepository;
    private final TechStackRepository techStackRepository;

    public Post registerPost(CreatePostRequestDto dto, User user) {
        Post post = dto.toEntity(user);
        for(String name : dto.getTechStackArr()) {
            TechStack techStack = techStackRepository.findTechStackByName(name).orElseThrow(null);
            PostSearch postSearch = PostSearch.builder()
                    .post(post)
                    .techStack(techStack)
                    .build();
            postSearchRepository.save(postSearch);
        }
        return postRepository.save(post);
    }

}
