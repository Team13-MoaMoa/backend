package sku.moamoa.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByIdAndAuthProvider(Long id, AuthProvider authProvider);
}
