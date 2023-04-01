package sku.moamoa.domain.comment.entity;

import lombok.*;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMMENT")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
}
