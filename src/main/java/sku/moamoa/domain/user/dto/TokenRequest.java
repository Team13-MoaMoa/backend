package sku.moamoa.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequest {
    private String registrationId;
    private String code;
    private String refreshToken;

    @Builder
    public TokenRequest(String registrationId, String code, String refreshToken){
        this.registrationId = registrationId;
        this.code = code;
        this.refreshToken = refreshToken;
    }
}
