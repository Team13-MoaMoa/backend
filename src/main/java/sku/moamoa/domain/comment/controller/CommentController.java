package sku.moamoa.domain.comment.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.comment.service.CommentService;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.UserService;
import sku.moamoa.global.result.ResultResponse;

import static sku.moamoa.global.result.ResultCode.*;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/{pid}")
    public ResponseEntity<ResultResponse> createComment(@PathVariable Long pid, @RequestBody CommentDto.CreateRequest body) {
        User user = userService.findUserById(body.getUid());
        Post post = postService.findById(pid);
        commentService.registerComment(user, post, body);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_CREATE_SUCCESS));
    }

}
