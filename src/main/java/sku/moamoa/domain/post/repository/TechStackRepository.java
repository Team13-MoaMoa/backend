package sku.moamoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.post.entity.TechStack;

import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findTechStackByName(String name);
}
