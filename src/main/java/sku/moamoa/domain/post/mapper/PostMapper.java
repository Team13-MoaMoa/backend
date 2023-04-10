package sku.moamoa.domain.post.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.dto.response.CreatePostResponseDto;
import sku.moamoa.domain.post.dto.response.GetPostResponseDto;
import sku.moamoa.domain.post.dto.response.GetPostsResponseDto;
import sku.moamoa.domain.post.dto.response.PostInfoTechStackRes;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.user.dto.UserDto;
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
    public UserDto.InfoResponse toPostInfoResDto(User user){
        return UserDto.InfoResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public PostInfoTechStackRes toPostInfoResDto(PostSearch postSearch) {
        return PostInfoTechStackRes.builder()
                .id(postSearch.getTechStack().getId())
                .build();
    }

    public List<PostInfoTechStackRes> toPostInfoResDtoList(List<PostSearch> techStackList) {
        return techStackList.stream().map(this::toPostInfoResDto).collect(Collectors.toList());
    }

    public GetPostsResponseDto toGetPostsResponseDto(Post post) {
        return GetPostsResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .projectName(post.getProjectName())
                .content(post.getContent())
                .headcount(post.getHeadcount())
                .jobPosition(post.getJobPosition())
                .deadline(post.getDeadline())
                .user(toPostInfoResDto(post.getUser()))
                .techStackList(toPostInfoResDtoList(post.getPostSearchList()))
                .commentCount(post.getCommentList().size())
                .build();
    }

    public Page<GetPostsResponseDto> toGetPostsResponseDtoList(Page<Post> postList) {
        return new PageImpl<>(postList.stream().map(this::toGetPostsResponseDto).collect(Collectors.toList()));
    }

    public CommentDto.InfoResponse toCommentInfoResDto(Comment comment){
        return CommentDto.InfoResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(toPostInfoResDto(comment.getUser()))
                .build();
    }

    public List<CommentDto.InfoResponse> toCommentInfoResDtoList(List<Comment> commentList) {
        return commentList.stream().map(this::toCommentInfoResDto).collect(Collectors.toList());
    }

    public GetPostResponseDto toGetPostResponseDto(Post post) {
        return GetPostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .projectName(post.getProjectName())
                .content(post.getContent())
                .deadline(post.getDeadline())
                .headcount(post.getHeadcount())
                .jobPosition(post.getJobPosition())
                .user(toPostInfoResDto(post.getUser()))
                .techStackList(toPostInfoResDtoList(post.getPostSearchList()))
                .commentList(toCommentInfoResDtoList(post.getCommentList()))
                .build();
    }
}
