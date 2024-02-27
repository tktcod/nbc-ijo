package com.spring.nbcijo.mypage;

import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.MyPageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MyPageServiceTest implements UserFixture {

    @InjectMocks
    MyPageService myPageService;

    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("마이페이지 내 정보 조회")
    class getMyInform {

    }
}
