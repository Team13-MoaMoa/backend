package sku.moamoa.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.dto.response.CreatePostResponseDto;
import sku.moamoa.domain.post.dto.response.GetPostsResponseDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.post.entity.TechStack;
import sku.moamoa.domain.post.exception.PostNotFoundException;
import sku.moamoa.domain.post.exception.TechStackNotFoundException;
import sku.moamoa.domain.post.mapper.PostMapper;
import sku.moamoa.domain.post.mapper.PostSearchMapper;
import sku.moamoa.domain.post.repository.PostRepository;
import sku.moamoa.domain.post.repository.PostSearchRepository;
import sku.moamoa.domain.post.repository.TechStackRepository;
import sku.moamoa.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostSearchRepository postSearchRepository;
    private final TechStackRepository techStackRepository;
    private final PostMapper postMapper;
    private final PostSearchMapper postSearchMapper;

    public CreatePostResponseDto registerPost(CreatePostRequestDto body, User user) {
        Post post = postMapper.toEntity(user,body);
        for(String name : body.getTechStackArr()) {
            TechStack techStack = techStackRepository.findTechStackByName(name).orElseThrow(TechStackNotFoundException::new);
            PostSearch postSearch = postSearchMapper.toEntity(post,techStack);
            postSearchRepository.save(postSearch);
        }
        return postMapper.toCreatePostResponseDto(postRepository.save(post));
    }

    public List<GetPostsResponseDto> findAllPostByTechStackNames(int page, String language, JobPosition position) {
        String[] names = language.split(",");
        PageRequest pageRequest = PageRequest.of(page-1,6);
        Page<Post> postList = postRepository.findAllByTechStackNames(pageRequest,names,position);
        List<TechStack> techStackList = new ArrayList<>();
        for(String name : names) {
            TechStack techStack = techStackRepository.findTechStackByName(name).orElseThrow(TechStackNotFoundException::new);
            techStackList.add(techStack);
        }
        return postMapper.toGetPostsResponseDtoList(postList,techStackList);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

}
