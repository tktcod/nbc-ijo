package com.spring.nbcijo.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.QPost;
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
    public List<Post> getPostListWithPaging(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        QPost post = QPost.post;

        return jpaQueryFactory.selectFrom(post)
            .orderBy(post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    }
}
