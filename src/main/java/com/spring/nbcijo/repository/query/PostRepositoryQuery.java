package com.spring.nbcijo.repository.query;

import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.Post;
import java.util.List;

public interface PostRepositoryQuery {

    public List<Post> getPostListWithPaging(Integer page, Integer size);
}
