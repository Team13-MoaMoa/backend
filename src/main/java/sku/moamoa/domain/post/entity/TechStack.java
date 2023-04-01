package sku.moamoa.domain.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TECH_STACK")
public class TechStack {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tech_stack_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "img_url")
    private String imageUrl;
    @OneToMany(mappedBy = "techStack")
    private List<PostSearch> postSearchList = new ArrayList<>();

    public TechStack(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
