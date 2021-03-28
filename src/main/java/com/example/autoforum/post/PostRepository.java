package com.example.autoforum.post;

import com.example.autoforum.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findPostsByCategoryId(Category category);
}
