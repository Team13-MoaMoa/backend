package sku.moamoa.domain.user.dto.request;

import lombok.*;
import sku.moamoa.domain.user.entity.LoginPlatform;
import sku.moamoa.domain.user.entity.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpRequestDto {
    private String nickname;
    private String email;
    private LoginPlatform platform;
    private String imageUrl;
    private String portFolioUrl;
    private String githubUrl;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .platform(platform)
                .imageUrl(imageUrl)
                .portFolioUrl(portFolioUrl)
                .githubUrl(githubUrl)
                .build();
    }
}

