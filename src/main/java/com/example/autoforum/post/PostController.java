package com.example.autoforum.post;

import com.example.autoforum.category.Category;
import com.example.autoforum.category.CategoryService;
import com.example.autoforum.comment.Comment;
import com.example.autoforum.comment.CommentController;
import com.example.autoforum.user.User;
import org.aspectj.bridge.MessageUtil;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

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

//    @PostMapping(path = "/comment", consumes = "multipart/form-data")
//    public ResponseEntity<?> addComment(@RequestPart Comment comment) throws URISyntaxException {
//        LOGGER.info(comment.toString());
//        if (comment == null) {
//            return ResponseEntity.unprocessableEntity().build();
//        } else {
//            LOGGER.info(comment.toString());
//            URI location = new URI("http://www.concretepage.com/");
//            commentService.addComment(comment);
//            return ResponseEntity.created(location).build();
//        }
//    }

    @PostMapping(path = "/post", consumes = "multipart/form-data")
    public ResponseEntity<?> addUser(@RequestPart Post post) throws URISyntaxException {
        if (post == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(post.toString());
            URI location = new URI("http://www.concretepage.com/");
            postService.addPost(post);
            return ResponseEntity.created(location).build();
        }
    }

//    @PostMapping(path = "/post", consumes = "application/json")
//    public ResponseEntity<?> addUser(@RequestBody Post post) throws URISyntaxException {
//        if (post == null) {
//            return ResponseEntity.unprocessableEntity().build();
//        } else {
//            URI location = new URI("http://www.concretepage.com/");
//            postService.addPost(post);
//            return ResponseEntity.created(location).build();
//        }
//    }

    @PutMapping(path = "/post/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUser(@RequestPart Post post, @PathVariable int id) {
        if (post == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(post.toString());
            post.setId(id);
            postService.updatePost(id, post);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping(path = "/post/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
