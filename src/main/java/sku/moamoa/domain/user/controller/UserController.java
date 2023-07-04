package sku.moamoa.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.likeboard.service.LikeBoardService;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.dto.SignUpRequest;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.UserService;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.result.ResultResponse;

import static sku.moamoa.global.result.ResultCode.*;

@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final LikeBoardService likeBoardService;

    @GetMapping("/{uid}")
    public ResponseEntity<ResultResponse> getUser(@LoginUser User user, @PathVariable Long uid) {
        UserDto.DetailInfoResponse detailInfoResponse = userService.findUserById(uid);
        return ResponseEntity.ok(ResultResponse.of(GET_USERINFO_SUCCESS,detailInfoResponse));
    }

    @GetMapping("/projects")
    public ResponseEntity<ResultResponse> getMyPosts(@LoginUser User user, @RequestParam(value = "page",defaultValue = "1") int page) {
        Page<PostDto.GetPostsResponse> postList = postService.findPostByUser(page, user);
        return ResponseEntity.ok(ResultResponse.of(GET_USER_POSTS_SUCCESS,postList));
    }

    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getMyPostsByLike(@LoginUser User user, @RequestParam(value = "page",defaultValue = "1") int page) {
        Page<PostDto.GetPostsResponse> postList = likeBoardService.findAllByUser(page, user);
        return ResponseEntity.ok(ResultResponse.of(GET_USER_LIKE_POSTS_SUCCESS,postList));
    }

    @PostMapping("/likes/{pid}")
    public ResponseEntity<ResultResponse> like(@LoginUser User user, @PathVariable Long pid) {
        Post post = postService.findById(pid);
        likeBoardService.registerLikeBoard(user,post);
        return ResponseEntity.ok(ResultResponse.of(USER_LIKE_SUCCESS));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> createUser(@RequestBody SignUpRequest signUpRequest){
        Long id = userService.createUser(signUpRequest);
        return ResponseEntity.ok(ResultResponse.of(USER_REGISTRATION_SUCCESS, id));
    }


}
