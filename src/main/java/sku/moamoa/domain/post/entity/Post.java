package sku.moamoa.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.global.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SQLDelete(sql = "UPDATE posts SET is_deleted = true WHERE post_id = ?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POSTS")
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "content")
    private String content;
    @Column(name = "deadline")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;
    @Column(name = "headcount")
    private int headcount;
    @Column(name = "job_tag")
    private String jobTag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostSearch> postSearchList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<LikeBoard> likeBoardList = new ArrayList<>();

    @Builder
    public Post(String title, String projectName, String content, LocalDateTime deadline, int headcount, String[] jobTag, User user) {
        this.title = title;
        this.projectName = projectName;
        this.content = content;
        this.deadline = deadline;
        this.headcount = headcount;
        this.jobTag = String.join(",", jobTag);
        this.user = user;
    }

    public Post updatePost(PostDto.CreateRequest body) {
        this.title = body.getTitle();
        this.projectName = body.getProjectName();
        this.content = body.getContent();
        this.deadline = body.getDeadline();
        this.headcount = body.getHeadcount();
        this.jobTag = String.join(",", body.getJobTag());
        return this;
    }
}

