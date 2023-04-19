package sku.moamoa.domain.post.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.dto.TechStackDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    public Post toEntity(User user, PostDto.CreateRequest body) {
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

    public PostDto.InfoResponse toCreatePostResponseDto(Post post){
        return PostDto.InfoResponse.builder()
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

    public TechStackDto.InfoResponse toPostInfoResDto(PostSearch postSearch) {
        return TechStackDto.InfoResponse.builder()
                .id(postSearch.getTechStack().getId())
                .build();
    }

    public List<TechStackDto.InfoResponse> toPostInfoResDtoList(List<PostSearch> techStackList) {
        return techStackList.stream().map(this::toPostInfoResDto).collect(Collectors.toList());
    }

    public PostDto.GetPostsResponse toGetPostsResponseDto(Post post) {
        return PostDto.GetPostsResponse.builder()
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

    public Page<PostDto.GetPostsResponse> toGetPostsResponseDtoList(Page<Post> postList) {
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

    public PostDto.GetPostResponse toGetPostResponseDto(Post post) {
        return PostDto.GetPostResponse.builder()
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

    public PostDto.GetPostsResponse toGetPostsResponseDto(LikeBoard likeBoard) {
        Post post = likeBoard.getPost();
        return PostDto.GetPostsResponse.builder()
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

    public Page<PostDto.GetPostsResponse> toGetPostResponseDto(Page<LikeBoard> likeBoardList) {
        return new PageImpl<>(likeBoardList.stream().map(this::toGetPostsResponseDto).collect(Collectors.toList()));
    }
}
