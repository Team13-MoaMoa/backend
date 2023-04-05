package sku.moamoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(value = "select DISTINCT p from Post p left join fetch p.postSearchList ps where ps.techStack.name in :names and p.jobPosition = :position")
    List<Post> findAllByTechStackNames(String[] names, JobPosition position);
}
