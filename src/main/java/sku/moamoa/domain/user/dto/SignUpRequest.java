package sku.moamoa.domain.user.dto;

import lombok.Getter;
import sku.moamoa.domain.user.entity.AuthProvider;

@Getter
public class SignUpRequest {
    private String id;
    private String nickname;
    private String email;
    private AuthProvider authProvider;
    private String imageUrl;
    private String portFolioUrl;
    private String githubUrl;
}

//{
//        "authProvider": "KAKAO",
//        "email": "misosjm@naver.com",
//        "githubUrl": "githubUrl.com",
//        "id": 275849432,
//        "imageUrl": "imageUrl.com",
//        "nickname": "일이삼",
//        "portFolioUrl": "portFolioUrl.com"
//        }