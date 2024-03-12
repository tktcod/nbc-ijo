package com.spring.nbcijo.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<PostResponseDto> getPostListWithPaging(Integer page, Integer size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        QPost post = QPost.post;

        // 검색 조건 추가
        BooleanBuilder whereClause = new BooleanBuilder();
        if (search != null && !search.isEmpty()) {
            whereClause.and(post.title.containsIgnoreCase(search));
        }

        return jpaQueryFactory
            .select(Projections.constructor(PostResponseDto.class,
                post.id,
                post.title,
                post.content,
                post.createdAt,
                post.modifiedAt))
            .from(post)
            .where(whereClause)
            .orderBy(post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
