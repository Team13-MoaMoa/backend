package sku.moamoa.domain.likeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.likeboard.entity.LikeBoard;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
}
