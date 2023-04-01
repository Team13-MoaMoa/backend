package sku.moamoa.domain.comment.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.comment.dto.request.CreateCommentReq;
import sku.moamoa.domain.comment.repository.CommentRepository;
import sku.moamoa.domain.comment.service.CommentService;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.domain.user.service.UserService;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/{pid}")
    public void createComment(@PathVariable Long pid, @RequestBody CreateCommentReq body) {
        User user = userService.findUserById(body.getUid());
        Post post = postService.findById(pid);
        commentService.registerComment(user, post, body);
    }

}
