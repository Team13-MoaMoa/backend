package sku.moamoa.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import sku.moamoa.domain.user.entity.AuthProvider;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequest {
    private String id;
    private String nickname;
    private String email;
    private AuthProvider authProvider;
    private String imageUrl;
    private String portFolioUrl;
    private String githubUrl;
}