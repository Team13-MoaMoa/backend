package sku.moamoa.domain.user.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private Long id;
    private KakaoAccount kakaoAccount;

    @Getter
    private static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter
        private static class Profile {
            private String nickname;
            private String profileImageUrl;
        }
    }
}
