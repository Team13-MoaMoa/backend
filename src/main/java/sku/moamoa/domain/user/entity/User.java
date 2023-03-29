package sku.moamoa.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sku.moamoa.domain.user.entity.LoginPlatform;
import sku.moamoa.global.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "platform_type")
    @Enumerated(value = EnumType.STRING)
    private LoginPlatform platform;
    @Column(name = "img_url")
    private String imageUrl;
    @Column(name = "portfolio_url")
    private String portFolioUrl;
    @Column(name = "github_url")
    private String githubUrl;
    @Builder
    public User(String nickname, String email, LoginPlatform platform, String imageUrl, String portFolioUrl, String githubUrl) {
        this.nickname = nickname;
        this.email = email;
        this.platform = platform;
        this.imageUrl = imageUrl;
        this.portFolioUrl = portFolioUrl;
        this.githubUrl = githubUrl;
    }
}
