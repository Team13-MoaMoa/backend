package sku.moamoa.domain.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.user.exception.UserNotFoundException;
import sku.moamoa.domain.user.dto.request.SignUpRequestDto;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
    public void join(SignUpRequestDto dto){
        User user = dto.toEntity();
        userRepository.save(user);
    }
}
