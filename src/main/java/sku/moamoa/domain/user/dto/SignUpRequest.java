package sku.moamoa.domain.user.dto;

import lombok.Getter;
import sku.moamoa.domain.user.entity.AuthProvider;

@Getter
public class SignUpRequest {
    private Long id;
    private String nickname;
    private String email;
    private AuthProvider authProvider;
    private String imageUrl;
    private String portFolioUrl;
    private String githubUrl;
}