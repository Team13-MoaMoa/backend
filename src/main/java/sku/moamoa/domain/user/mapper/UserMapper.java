package sku.moamoa.domain.user.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserDto.CreateRequest dto) {
        return User.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .authProvider(dto.getAuthProvider())
                .imageUrl(dto.getImageUrl())
                .portFolioUrl(dto.getPortFolioUrl())
                .githubUrl(dto.getGithubUrl())
                .build();
    }

    public List<UserDto.InfoResponse> toUserInfoResDtoList(List<User> userList) {
        return userList.stream().distinct().map(this::toUserInfoResDto).collect(Collectors.toList());
    }



    public UserDto.InfoResponse toUserInfoResDto(User user) {
        return UserDto.InfoResponse.builder()
                .id(user.getId())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .build();
    }

    public UserDto.DetailInfoResponse toDetailInfoResponseDto(User user) {
        return UserDto.DetailInfoResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .githubUrl(user.getGithubUrl())
                .portfolioUrl(user.getPortFolioUrl())
                .build();
    }
}
