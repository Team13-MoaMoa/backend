package sku.moamoa.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),
    INVALID_ACCESS_TOKEN(401,"G003", "인증되지 않은 토큰"),

    // User 도메인
    INVALID_PASSWORD(400, "U001", "잘못된 비밀번호"),
    USER_NOT_FOUND_ERROR(400, "U002", "사용자를 찾을 수 없음"),
    UNAUTHORIZED_ACCESS_ERROR(403, "U003", "승인되지 않은 접근"),
    USER_USERNAME_DUPLICATED(409, "U004", "회원 아이디 중복"),

    // Post 도메인
    POST_NOT_FOUND_ERROR(400, "P001","게시물을 찾을 수 없음"),
    TECH_STACK_NOT_FOUND_ERROR(400, "P002", "기술 스택을 찾을 수 없음")


    ;

    private final int status;
    private final String code;
    private final String message;
}
