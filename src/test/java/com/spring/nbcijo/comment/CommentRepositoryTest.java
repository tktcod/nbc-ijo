package com.spring.nbcijo.comment;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CommentRepositoryTest implements CommentFixture, PostFixture {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
        postRepository.save(TEST_POST);
    }

    @DisplayName("생성일시 기준 내림차순 정렬 조회")
    @Test
    void findAll() {
        //given
        var testComment1 = CommentTestUtils.get(TEST_COMMENT, 1L,
            LocalDateTime.now(), TEST_USER, TEST_POST);
        var testComment2 = CommentTestUtils.get(TEST_COMMENT, 2L,
            LocalDateTime.now().minusMinutes(1), TEST_USER, TEST_POST);

        commentRepository.save(testComment1);
        commentRepository.save(testComment2);

        //when
        var result = commentRepository.findAllByPostIdOrderByCreatedAtDesc(
            TEST_POST_ID);

        //then
        assertThat(result.get(1).getCreatedAt()).isBefore(result.get(0).getCreatedAt());
    }
}
