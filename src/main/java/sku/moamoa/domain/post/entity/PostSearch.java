package sku.moamoa.domain.post.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_SEARCH")
public class PostSearch {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pt_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tech_stack_id")
    private TechStack techStack;

    @Builder
    public PostSearch(Post post, TechStack techStack) {
        this.post = post;
        this.techStack = techStack;
    }
}
