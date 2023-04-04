package sku.moamoa.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class QuerydslConfiguration {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
