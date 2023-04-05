package sku.moamoa.domain.post.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.post.dto.request.CreatePostRequestDto;
import sku.moamoa.domain.post.dto.response.CreatePostResponseDto;
import sku.moamoa.domain.post.dto.response.GetPostsResponseDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.UserService;
import sku.moamoa.global.result.ResultResponse;

import java.util.List;

import static sku.moamoa.global.result.ResultCode.*;

@Api(tags = "게시물 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ResultResponse> getPosts(@RequestParam(value = "page",defaultValue = "1") int page,
                                                   @RequestParam(value = "position", required = false)JobPosition position,
                                                   @RequestParam(value = "language", required = false) String language){
        List<GetPostsResponseDto> list = postService.findAllPostByTechStackNames(page,language,position);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_POST_SUCCESS,list));
    }

    @PostMapping("/{uid}")
    public ResponseEntity<ResultResponse> createPost(@PathVariable Long uid, @RequestBody CreatePostRequestDto body) {
        User user = userService.findUserById(uid);
        CreatePostResponseDto createPostResponseDto = postService.registerPost(body, user);
        return ResponseEntity.ok(ResultResponse.of(POST_CREATE_SUCCESS, createPostResponseDto));
    }

}
