package sku.moamoa.global.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 도메인 별로 나눠서 관리(ex: User 도메인)
    // User
    USER_REGISTRATION_SUCCESS("U001", "사용자 등록 성공"),
    USER_USERNAME_DUPLICATED("U002", "회원 아이디 중복"),
    USER_USERNAME_NOT_DUPLICATED("U003", "회원 아이디 중복되지 않음"),
    USER_LOGIN_SUCCESS("U004", "회원 로그인 성공"),
    USER_LOGOUT_SUCCESS("U005", "회원 로그아웃 성공"),
    GET_LOGIN_USER_SUCCESS("U006", "로그인 되어있는 회원 조회 성공"),
    USER_LIKE_SUCCESS("U007","찜하기 성공"),
    GET_USERINFO_SUCCESS("U008", "단일 회원 조회 성공"),
    GET_USER_POSTS_SUCCESS("U009", "회원의 게시물 조회 성공"),
    GET_USER_LIKE_POSTS_SUCCESS("U010", "찜한 게시물 조회"),


    // Post
    POST_CREATE_SUCCESS("P001","게시물 생성 성공"),
    GET_POST_SUCCESS("P002","단일 게시물 조회 성공"),
    GET_ALL_POST_SUCCESS("P003","게시물 리스트 조회 성공"),

    // Comment,
    COMMENT_CREATE_SUCCESS("C001", "댓글 생성 성공"),

    // Note
    NOTE_CREATE_SUCCESS("N001", "쪽지 생성 성공"),
    ;

    private final String code;
    private final String message;
}
