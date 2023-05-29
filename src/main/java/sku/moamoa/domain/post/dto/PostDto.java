package sku.moamoa.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.*;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @ApiModel("PostCreateRequest")
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateRequest {
        private String title;
        private String projectName;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime deadline;
        private int headcount;
        private JobPosition jobPosition;
        private String[] techStackArr;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InfoResponse {
        private Long id;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @ApiModel("PostGetPostResponse")
    public static class GetPostResponse {
        public Long id;
        public String title;
        private String projectName;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime deadline;
        private int headcount;
        private JobPosition jobPosition;
        private UserDto.InfoResponse user;
        private List<TechStackDto.InfoResponse> techStackList;
        private List<CommentDto.InfoResponse> commentList;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @ApiModel("PostGetPostsResponse")
    public static class GetPostsResponse {
        private Long id;
        private String title;
        private String projectName;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime deadline;
        private int headcount;
        private JobPosition jobPosition;
        private UserDto.InfoResponse user;
        private List<TechStackDto.InfoResponse> techStackList;
        private int commentCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
    }
}
