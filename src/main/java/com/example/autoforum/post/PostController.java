package com.example.autoforum.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/posts", produces = "application/json")
    public ResponseEntity<?> postResponseEntity(@RequestBody Post post) {
        if (postService.getAllPosts() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postService.getAllPosts());
        }
    }
}
