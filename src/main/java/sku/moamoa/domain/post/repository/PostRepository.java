package sku.moamoa.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {
//    // join 사용 시 페이지네이션 에러, countQuery를 따로 작성해야함
//    @Query(value = "select DISTINCT p from Post p left join fetch p.postSearchList ps where ps.techStack.name in :names and p.jobPosition = :position",
//    countQuery = "select count(p) from Post p, PostSearch ps where ps.techStack.name in :names and p.jobPosition = :position")
//    Page<Post> findAllByTechStackNames(Pageable pageable, @Param("names") String[] names, @Param("position") JobPosition position);
    Page<Post> findAllByUser(Pageable pageable, User user);
}
