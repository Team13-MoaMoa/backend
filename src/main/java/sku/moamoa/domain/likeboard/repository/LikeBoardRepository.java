package sku.moamoa.domain.likeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.user.entity.User;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    Page<LikeBoard> findAllByUser(Pageable pageable, User user);
}
