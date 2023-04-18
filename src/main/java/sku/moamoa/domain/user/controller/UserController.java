package sku.moamoa.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.likeboard.dto.LikeBoardDto;
import sku.moamoa.domain.likeboard.service.LikeBoardService;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.UserService;
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

    @ApiOperation(value = "임시 회원가입")
    @PostMapping("/local/signup")
    public void signUp(@RequestBody UserDto.CreateRequest body) {
        userService.join(body);
    }

    @PostMapping("/likes/{pid}")
    public ResponseEntity<ResultResponse> like(@PathVariable Long pid, @RequestBody LikeBoardDto.CreateRequest body) {
        Post post = postService.findById(pid);
        User user = userService.findUserById(body.getUser_id());
        likeBoardService.registerLikeBoard(user,post);
        return ResponseEntity.ok(ResultResponse.of(USER_LIKE_SUCCESS));
    }
}
