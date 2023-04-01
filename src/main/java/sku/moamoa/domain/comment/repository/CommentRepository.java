package sku.moamoa.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
