package sku.moamoa.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.exception.UserNotFoundException;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.mapper.UserMapper;
import sku.moamoa.domain.user.repository.UserRepository;

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
}
