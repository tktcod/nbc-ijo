package com.spring.nbcijo.repository.query;

import com.spring.nbcijo.dto.response.PostResponseDto;
import java.util.List;

public interface PostRepositoryQuery {

    public List<PostResponseDto> getPostListWithPaging(Integer page, Integer size, String search);
}
