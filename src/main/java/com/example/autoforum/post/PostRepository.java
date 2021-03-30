package com.example.autoforum.post;

import com.example.autoforum.category.Category;
import com.example.autoforum.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findPostsByCategoryId(Category category);
    List<Post> findPostsByUserId(User user);
}
