package sku.moamoa.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.comment.dto.response.CommentInfoRes;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.user.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPostResponseDto {
    public Long id;
    public String title;
    private String projectName;
    private String content;
    private LocalDate deadline;
    private int headcount;
    private JobPosition jobPosition;
    private UserDto.InfoResponse user;
    private List<PostInfoTechStackRes> techStackList;
    private List<CommentInfoRes> commentList;
}
