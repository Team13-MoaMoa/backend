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

    public static final UserDto.InfoResponse subUser1 = UserDto.InfoResponse.builder()
            .id(1L)
            .nickname("전회원")
            .imageUrl("www.junUserImageUrl.com")
            .build();

    public static final UserDto.InfoResponse subUser2 = UserDto.InfoResponse.builder()
            .id(1L)
            .nickname("이회원")
            .imageUrl("www.leeUserImageUrl.com")
            .build();

    public static final UserDto.InfoResponse subUser3 = UserDto.InfoResponse.builder()
            .id(1L)
            .nickname("최회원")
            .imageUrl("www.choiUserImageUrl.com")
            .build();
}
