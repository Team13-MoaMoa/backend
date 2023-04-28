package sku.moamoa.domain.user.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final LikeBoardService likeBoardService;

    @GetMapping("/{uid}")
    public ResponseEntity<ResultResponse> getUser(@PathVariable Long uid) {
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

    @PostMapping("/local/signup")
    public void signUp(@RequestBody UserDto.CreateRequest body) {
        userService.join(body);
    }

    @PostMapping("/likes/{pid}")
    public ResponseEntity<ResultResponse> like(@PathVariable Long pid, @LoginUser User user) {
        Post post = postService.findById(pid);
        likeBoardService.registerLikeBoard(user,post);
        return ResponseEntity.ok(ResultResponse.of(USER_LIKE_SUCCESS));
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(userService.createUser(signUpRequest));
    }
}
