package sku.moamoa.domain.user.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserDto.CreateRequest dto) {
        return User.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .platform(dto.getPlatform())
                .imageUrl(dto.getImageUrl())
                .portFolioUrl(dto.getPortFolioUrl())
                .githubUrl(dto.getGithubUrl())
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
