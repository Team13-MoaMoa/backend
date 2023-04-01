package sku.moamoa.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.comment.dto.request.CreateCommentReq;
import sku.moamoa.domain.comment.entity.Comment;
import sku.moamoa.domain.comment.repository.CommentRepository;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void registerComment(User user, Post post, CreateCommentReq body) {
        Comment comment = body.toEntity(user,post, body.getContent());
        commentRepository.save(comment);

    }
}
