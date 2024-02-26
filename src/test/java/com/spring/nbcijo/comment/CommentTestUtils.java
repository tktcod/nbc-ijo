package com.spring.nbcijo.comment;

import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.User;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

public class CommentTestUtils {

    public static Comment get(Comment comment, User user, Post post) {
        return get(comment, 1L, LocalDateTime.now(), user, post);
    }

    public static Comment get(
        Comment comment,
        Long id,
        LocalDateTime createdAt,
        User user,
        Post post) {
        var newComment = SerializationUtils.clone(comment);
        ReflectionTestUtils.setField(newComment, "id", id, Long.class);
        ReflectionTestUtils.setField(newComment, "createdAt", createdAt, LocalDateTime.class);
        ReflectionTestUtils.setField(newComment, "user", user, User.class);
        ReflectionTestUtils.setField(newComment, "post", post, Post.class);
        return newComment;
    }
}
