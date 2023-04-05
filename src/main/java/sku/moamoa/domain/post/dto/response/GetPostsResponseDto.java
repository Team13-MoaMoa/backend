package sku.moamoa.domain.post.dto.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.PostSearch;
import sku.moamoa.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPostsResponseDto {
    private String title;
    private String projectName;
    private String content;
    private LocalDate deadline;
    private int headcount;
    private JobPosition jobPosition;
    private User user;
    private List<PostSearch> postSearchList;
}
