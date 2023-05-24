package sku.moamoa.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.global.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "auth_provider")
    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;
    @Column(name = "img_url")
    private String imageUrl;
    @Column(name = "portfolio_url")
    private String portFolioUrl;
    @Column(name = "github_url")
    private String githubUrl;
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<LikeBoard> likeBoardList = new ArrayList<>();

    @Builder
    public User(Long id, String nickname, String email, AuthProvider authProvider, String imageUrl, String portFolioUrl, String githubUrl) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.authProvider = authProvider;
        this.imageUrl = imageUrl;
        this.portFolioUrl = portFolioUrl;
        this.githubUrl = githubUrl;
    }

}

//{
//        "email": "email@naver.com",
//        "githubUrl": "github@githum.com",
//        "imageUrl": "imgurl.com",
//        "nickname": "송지민",
//        "platform": "GITHUB",
//        "portFolioUrl": "portfolio.com"
//}