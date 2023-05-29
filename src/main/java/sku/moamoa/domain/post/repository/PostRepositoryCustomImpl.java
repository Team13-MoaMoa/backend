package sku.moamoa.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import sku.moamoa.domain.post.entity.JobPosition;
import sku.moamoa.domain.post.entity.Post;

import java.util.List;

import static sku.moamoa.domain.post.entity.QPost.post;
import static sku.moamoa.domain.post.entity.QPostSearch.postSearch;


public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl() {
        super(Post.class);
    }

    @Override
    public Page<Post> findAllByTechStackNames(Pageable pageable, String[] names, JobPosition position, String search) {

        JPQLQuery<Post> query =  queryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postSearchList, postSearch)
                .where(eqJobPosition(position), inNames(names), containsSearch(search))
                .orderBy(post.createdAt.desc());

        List<Post> postList = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<Post>(postList, pageable, query.fetchCount());
    }
    private BooleanExpression inNames(String[] names) {
        if(names == null || names.length == 0) return null;
        return postSearch.techStack.name.in(names);
    }
    private BooleanExpression eqJobPosition(JobPosition position) {
        if(position ==  null) return null;
        return post.jobPosition.eq(position);
    }

    private BooleanExpression containsSearch(String search) {
        if(search == null ) return null;
        return post.title.contains(search);
    }
}
