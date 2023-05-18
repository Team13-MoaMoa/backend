package sku.moamoa.domain.user.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.user.dto.LogoutRequest;
import sku.moamoa.domain.user.dto.SignInResponse;
import sku.moamoa.domain.user.dto.TokenRequest;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.AuthService;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.result.ResultResponse;

import static sku.moamoa.global.result.ResultCode.USER_LOGOUT_SUCCESS;

// 소셜 로그인용 컨트롤러
@Api(tags = "소셜 로그인 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<SignInResponse> redirect(
            @PathVariable("registrationId") String registrationId
            , @RequestParam("code") String code) {
        return ResponseEntity.ok(
                authService.redirect(
                        TokenRequest.builder()
                                .registrationId(registrationId)
                                .code(code)
                                .build()));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<SignInResponse> refreshToken(@LoginUser User user){
        return ResponseEntity.ok(authService.refreshToken(user.getId()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@LoginUser User user, @RequestBody LogoutRequest logoutRequest) {
        authService.logout(user, logoutRequest);
        return ResponseEntity.ok(ResultResponse.of(USER_LOGOUT_SUCCESS));
    }
}
