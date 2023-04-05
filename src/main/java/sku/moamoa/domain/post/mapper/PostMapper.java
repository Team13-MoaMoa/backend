package sku.moamoa.domain.post.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.dto.response.CreatePostResponseDto;
import sku.moamoa.domain.post.dto.response.GetPostsResponseDto;
import sku.moamoa.domain.post.dto.response.PostInfoTechStackRes;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.TechStack;
import sku.moamoa.domain.user.dto.response.PostInfoRes;
import sku.moamoa.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

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

    public CreatePostResponseDto toCreatePostResponseDto(Post post){
        return CreatePostResponseDto.builder()
                .id(post.getId())
                .build();
    }
    public PostInfoRes toPostInfoResDto(User user){
        return PostInfoRes.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public PostInfoTechStackRes toPostInfoResDto(TechStack techStack) {
        return PostInfoTechStackRes.builder()
                .name(techStack.getName())
                .imageUrl(techStack.getImageUrl())
                .build();
    }

    public List<PostInfoTechStackRes> toPostInfoResDtoList(List<TechStack> techStackList) {
        return techStackList.stream().map(this::toPostInfoResDto).collect(Collectors.toList());
    }

    public GetPostsResponseDto toGetPostsResponseDto(Post post, List<TechStack> techStackList) {
        return GetPostsResponseDto.builder()
                .title(post.getTitle())
                .projectName(post.getProjectName())
                .content(post.getContent())
                .headcount(post.getHeadcount())
                .jobPosition(post.getJobPosition())
                .deadline(post.getDeadline())
                .user(toPostInfoResDto(post.getUser()))
                .techStackList(toPostInfoResDtoList(techStackList))
                .build();
    }

    public List<GetPostsResponseDto> toGetPostsResponseDtoList(Page<Post> postList, List<TechStack> techStackList) {
        return postList.stream().map(post -> toGetPostsResponseDto(post,techStackList)).collect(Collectors.toList());
    }
}
