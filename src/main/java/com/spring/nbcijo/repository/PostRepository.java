package com.spring.nbcijo.repository;

import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.repository.query.PostRepositoryQuery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryQuery {

    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
