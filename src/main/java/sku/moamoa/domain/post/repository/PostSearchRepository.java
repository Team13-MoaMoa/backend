package sku.moamoa.domain.post.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sku.moamoa.domain.post.entity.PostSearch;

import java.util.List;

public interface PostSearchRepository extends JpaRepository<PostSearch,Long> {

}
