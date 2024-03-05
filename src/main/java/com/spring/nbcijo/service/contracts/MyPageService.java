package com.spring.nbcijo.service.contracts;

import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.request.UpdatePasswordRequestDto;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.User;
import java.util.List;

public interface MyPageService {

    public MyInformResponseDto getMyInform(User user);

    public void updateMyDescription(User user,
        UpdateDescriptionRequestDto updateDescriptionRequestDto);

    public void updateMyPassword(User user, UpdatePasswordRequestDto updatePasswordRequestDto);

    public List<PostResponseDto> getMyPosts(User user);

    public List<CommentResponseDto> getMyComments(User user);

}
