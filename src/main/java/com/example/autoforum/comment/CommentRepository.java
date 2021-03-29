package com.example.autoforum.comment;

import com.example.autoforum.post.Post;
import com.example.autoforum.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByUserId(User user);
}
