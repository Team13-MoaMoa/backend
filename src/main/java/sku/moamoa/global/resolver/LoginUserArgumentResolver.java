package sku.moamoa.global.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.error.exception.BaseException;
import sku.moamoa.global.security.SecurityUtil;

import static sku.moamoa.global.error.ErrorCode.INVALID_ACCESS_TOKEN;
import static sku.moamoa.global.error.ErrorCode.USER_NOT_FOUND_ERROR;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private static final String BEARER = "Bearer";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws BaseException {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = extractToken(authorization);

        // 토큰 검사 및 회원 아이디 추출
        Long userId = Long.valueOf((String) securityUtil.get(token).get("userId"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND_ERROR));

        return user;
    }

    private static String extractToken(String authorization) throws BaseException {
        if (authorization == null || !authorization.startsWith(BEARER + " ")) {
            throw new BaseException(INVALID_ACCESS_TOKEN);
        }
        return authorization.substring(BEARER.length());
    }
}
