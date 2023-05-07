package sku.moamoa.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogoutRequest {
    private String accessToken;
    private String registrationId;

    @Builder
    public LogoutRequest(String accessToken, String registrationId) {
        this.accessToken = accessToken;
        this.registrationId = registrationId;
    }
}
