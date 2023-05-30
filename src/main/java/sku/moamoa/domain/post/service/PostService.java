package sku.moamoa.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.post.entity.TechStack;
import sku.moamoa.domain.post.exception.PostNotFoundException;
import sku.moamoa.domain.post.exception.TechStackNotFoundException;
import sku.moamoa.domain.post.mapper.PostMapper;
import sku.moamoa.domain.post.mapper.PostSearchMapper;
import sku.moamoa.domain.post.repository.PostRepository;
import sku.moamoa.domain.post.repository.PostRepositoryCustomImpl;
import sku.moamoa.domain.post.repository.PostSearchRepository;
import sku.moamoa.domain.post.repository.TechStackRepository;
import sku.moamoa.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRepositoryCustomImpl postRepositoryCustom;
    private final PostSearchRepository postSearchRepository;
    private final TechStackRepository techStackRepository;
    private final PostMapper postMapper;
    private final PostSearchMapper postSearchMapper;

    public PostDto.InfoResponse registerPost(PostDto.CreateRequest body, User user) {
        Post post = postMapper.toEntity(user,body);
        for(String name : body.getTechStackArr()) {
            TechStack techStack = techStackRepository.findTechStackByName(name).orElseThrow(TechStackNotFoundException::new);
            PostSearch postSearch = postSearchMapper.toEntity(post,techStack);
            postSearchRepository.save(postSearch);
        }
        return postMapper.toCreatePostResponseDto(postRepository.save(post));
    }

    public Page<PostDto.GetPostsResponse> findPostByUser(int page, User user) {
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postList = postRepository.findAllByUser(pageRequest, user);
        return postMapper.toGetPostsResponseDtoList(postList);
    }

    public Page<PostDto.GetPostsResponse> findAllPostByTechStackNames(int page, String language, String position, String search) {
        String[] names = language == null ? null : language.split(",");
        PageRequest pageRequest = PageRequest.of(page-1,6);
        Page<Post> postList = postRepositoryCustom.findAllByTechStackNames(pageRequest,names,position,search);
        return postMapper.toGetPostsResponseDtoList(postList);
    }

    public PostDto.GetPostResponse findPostById(Long pid){
        Post post = findById(pid);
        return postMapper.toGetPostResponseDto(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

}
