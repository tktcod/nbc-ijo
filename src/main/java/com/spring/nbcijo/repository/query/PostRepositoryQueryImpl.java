package com.spring.nbcijo.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.QPost;
import com.spring.nbcijo.entity.QUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryQueryImpl implements PostRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<PostResponseDto> getPostListWithPaging(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        QPost post = QPost.post;

        return jpaQueryFactory
            .select(Projections.constructor(PostResponseDto.class,
                post.id,
                post.title,
                post.content,
                post.createdAt,
                post.modifiedAt))
            .from(post)
            .orderBy(post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
