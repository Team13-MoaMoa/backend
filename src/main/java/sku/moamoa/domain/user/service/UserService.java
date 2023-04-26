package sku.moamoa.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.user.dto.SignUpRequest;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.exception.UserNotFoundException;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.mapper.UserMapper;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.error.exception.BadRequestException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto.DetailInfoResponse findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.toDetailInfoResponseDto(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
    public void join(UserDto.CreateRequest dto){
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
    }

    public Long createUser(SignUpRequest signUpRequest){ // 첫 소셜 로그인 시 회원가입
        if(userRepository.existsByIdAndAuthProvider(Long.valueOf(signUpRequest.getId()), signUpRequest.getAuthProvider())){
            throw new BadRequestException("aleady exist user");
        }

        return userRepository.save(
                User.builder()
                        .id(Long.valueOf(signUpRequest.getId()))
                        .nickname(signUpRequest.getNickname())
                        .email(signUpRequest.getEmail())
                        .portFolioUrl(signUpRequest.getPortFolioUrl())
                        .githubUrl(signUpRequest.getGithubUrl())
                        .authProvider(signUpRequest.getAuthProvider())
                        .build()).getId();
    }
}
