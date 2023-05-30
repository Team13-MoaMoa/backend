package sku.moamoa.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;

public interface PostRepositoryCustom {
    Page<Post> findAllByTechStackNames(Pageable pageable, @Param("names") String[] names, @Param("position") String position, @Param("search") String search);
}
