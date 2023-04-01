package sku.moamoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
