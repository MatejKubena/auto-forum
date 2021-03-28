package com.example.autoforum.post;

import com.example.autoforum.category.Category;
import com.example.autoforum.category.CategoryService;
import com.example.autoforum.user.User;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @Autowired
    public PostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;

    }

    @GetMapping(path = "/posts", produces = "application/json")
    public ResponseEntity<?> getPosts() {
        if (postService.getAllPosts() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postService.getAllPosts());
        }
    }

    @GetMapping(path = "/posts/category/{id}", produces = "application/json")
    public ResponseEntity<?> getPostsCat(@PathVariable int id) {
        if (postService.getAllPostsByCategoryId(categoryService.getCategory(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postService.getAllPostsByCategoryId(categoryService.getCategory(id)));
        }
    }

    @GetMapping(path = "/posts/{id}", produces = "application/json")
    public ResponseEntity<?> getMyPosts(@PathVariable int id) {
        if (postService.getAllPosts() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postService.getAllPosts());
        }
    }

    @GetMapping(path = "/post/{id}", produces = "application/json")
    public ResponseEntity<?> getPost(@PathVariable int id) {
        if (postService.getPost(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postService.getPost(id));
        }
    }

    @PostMapping(path = "/post", consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody Post post) throws URISyntaxException {
        if (post == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            URI location = new URI("http://www.concretepage.com/");
            postService.addPost(post);
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(path = "/post/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody Post post, @PathVariable int id) {
        postService.updatePost(id, post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/post/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
