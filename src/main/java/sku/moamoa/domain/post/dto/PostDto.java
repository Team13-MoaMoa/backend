package sku.moamoa.domain.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.user.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public class PostDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateRequest {
        private String title;
        private String projectName;
        private String content;
        private LocalDate deadline;
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
    public static class GetPostResponse {
        public Long id;
        public String title;
        private String projectName;
        private String content;
        private LocalDate deadline;
        private int headcount;
        private JobPosition jobPosition;
        private UserDto.InfoResponse user;
        private List<TechStackDto.InfoResponse> techStackList;
        private List<CommentDto.InfoResponse> commentList;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetPostsResponse {
        private Long id;
        private String title;
        private String projectName;
        private String content;
        private LocalDate deadline;
        private int headcount;
        private JobPosition jobPosition;
        private UserDto.InfoResponse user;
        private List<TechStackDto.InfoResponse> techStackList;
        private int commentCount;
    }


}
