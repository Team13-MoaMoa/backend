package sku.moamoa.fixture;

import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.entity.User;

public class UserFixtures {
    public static final User mainUser = User.builder()
            .id(1L)
            .nickname("송회원")
            .email("songuser@naver.com")
            .authProvider(AuthProvider.KAKAO)
            .imageUrl("www.songUserImageUrl.com")
            .portFolioUrl("www.songUserPortFolioUrl.com")
            .githubUrl("www.songUserGithubUrl.com")
            .build();

    public static final UserDto.InfoResponse mainUserInfoResponse = UserDto.InfoResponse.builder()
            .id(1L)
            .nickname("송회원")
            .imageUrl("www.songUserImageUrl.com")
            .build();
}
