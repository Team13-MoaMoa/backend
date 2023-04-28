package sku.moamoa.domain.post.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.result.ResultResponse;

import static sku.moamoa.global.result.ResultCode.*;

@Api(tags = "게시물 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<ResultResponse> getPosts(@RequestParam(value = "page",defaultValue = "1") int page,
                                                   @RequestParam(value = "position", required = false)JobPosition position,
                                                   @RequestParam(value = "language", required = false) String language){
        Page<PostDto.GetPostsResponse> list = postService.findAllPostByTechStackNames(page,language,position);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_POST_SUCCESS,list));
    }

    @GetMapping("/{pid}")
    public ResponseEntity<ResultResponse> getPost(@PathVariable Long pid){
        PostDto.GetPostResponse getPostResponseDto = postService.findPostById(pid);
        return ResponseEntity.ok(ResultResponse.of(GET_POST_SUCCESS,getPostResponseDto));
    }

    @PostMapping()
    public ResponseEntity<ResultResponse> createPost(@LoginUser User user, @RequestBody PostDto.CreateRequest body) {
        PostDto.InfoResponse createPostResponseDto = postService.registerPost(body, user);
        return ResponseEntity.ok(ResultResponse.of(POST_CREATE_SUCCESS, createPostResponseDto));
    }

}
