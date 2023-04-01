package sku.moamoa.domain.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sku.moamoa.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POSTS")
public class Post {
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
    private LocalDate deadline;
    @Column(name = "headcount")
    private int headcount;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "job_position")
    private JobPosition jobPosition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostSearch> postSearchList = new ArrayList<>();

    @Builder
    public Post(String title, String projectName, String content, LocalDate deadline, int headcount, JobPosition jobPosition, User user) {
        this.title = title;
        this.projectName = projectName;
        this.content = content;
        this.deadline = deadline;
        this.headcount = headcount;
        this.jobPosition = jobPosition;
        this.user = user;
    }
}

