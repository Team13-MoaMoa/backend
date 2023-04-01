package sku.moamoa.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.entity.TechStack;
import sku.moamoa.domain.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePostRequestDto {
    private String title;
    private String projectName;
    private String content;
    private LocalDate deadline;
    private int headcount;
    private JobPosition jobPosition;
    private String[] techStackArr;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .projectName(projectName)
                .content(content)
                .deadline(deadline)
                .headcount(headcount)
                .jobPosition(jobPosition)
                .user(user)
                .build();
    }
}
