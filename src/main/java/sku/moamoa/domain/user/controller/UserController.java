package sku.moamoa.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sku.moamoa.domain.user.dto.request.SignUpRequestDto;
import sku.moamoa.domain.user.service.UserService;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "임시 회원가입")
    @PostMapping("/local/signup")
    public void signUp(@RequestBody SignUpRequestDto body) {
        userService.join(body);
    }
}
