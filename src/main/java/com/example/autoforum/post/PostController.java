package com.example.autoforum.post;

import com.example.autoforum.category.CategoryService;
import com.example.autoforum.user.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, CategoryService categoryService, UserService userService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;

    }

    @GetMapping(path = "/posts", produces = "application/json")
    public ResponseEntity<?> getPosts() {

        List<Post> postList = postService.getAllPosts();
        ArrayList<PostDTO> postListDTO = new ArrayList<>();

        if (postService.getAllPosts() == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var postInstance: postList) {
                PostDTO postDTO = new PostDTO();
                postDTO.setId(postInstance.getId());
                postDTO.setTitle(postInstance.getTitle());
                postDTO.setDescription(postInstance.getDescription());
                postDTO.setCreatedAt(postInstance.getCreatedAt());
                postDTO.setUpdatedAt(postInstance.getUpdatedAt());
                postListDTO.add(postDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(postListDTO);
        }
    }

    @GetMapping(path = "/posts/category", produces = "application/json")
    public ResponseEntity<?> getPostsCat(@RequestParam int id) {

        List<Post> postList = postService.getAllPostsByCategoryId(categoryService.getCategory(id));
        ArrayList<PostDTO> postListDTO = new ArrayList<>();

        if (postService.getAllPostsByCategoryId(categoryService.getCategory(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var postInstance: postList) {
                PostDTO postDTO = new PostDTO();
                postDTO.setId(postInstance.getId());
                postDTO.setTitle(postInstance.getTitle());
                postDTO.setDescription(postInstance.getDescription());
                postDTO.setCreatedAt(postInstance.getCreatedAt());
                postDTO.setUpdatedAt(postInstance.getUpdatedAt());
                postListDTO.add(postDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(postListDTO);
        }
    }

    @GetMapping(path = "/myposts", produces = "application/json")
    public ResponseEntity<?> getMyPosts(@RequestParam int id) {

        List<Post> postList = postService.getAllPostsByUserId(userService.getUser(id));
        ArrayList<PostDTO> postListDTO = new ArrayList<>();

        if (postService.getAllPostsByUserId(userService.getUser(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var postInstance: postList) {
                PostDTO postDTO = new PostDTO();
                postDTO.setId(postInstance.getId());
                postDTO.setTitle(postInstance.getTitle());
                postDTO.setDescription(postInstance.getDescription());
                postDTO.setCreatedAt(postInstance.getCreatedAt());
                postDTO.setUpdatedAt(postInstance.getUpdatedAt());
                postListDTO.add(postDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(postListDTO);
        }
    }

    @GetMapping(path = "/post", produces = "application/json")
    public ResponseEntity<?> getPost(@RequestParam int id) {

        Post post = postService.getPost(id);

        if (postService.getPost(id) == null) {
            return ResponseEntity.notFound().build();
        } else {

            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setUserId(post.getUserId());
            postDTO.setTitle(post.getTitle());
            postDTO.setDescription(post.getDescription());
            postDTO.setCreatedAt(post.getCreatedAt());
            postDTO.setUpdatedAt(post.getUpdatedAt());

            return ResponseEntity.ok().header("Content-Type", "application/json").body(postDTO);
        }
    }

    @GetMapping(path = "/search", produces = "application/json")
    public ResponseEntity<?> getSearch(@RequestParam String param) {

        List<Post> allPostsCompare = postService.getAllPosts();
        ArrayList<PostDTO> postListDTO = new ArrayList<>();

        for (var postInstance: allPostsCompare) {
            if (postInstance.getTitle().toLowerCase().contains(param.toLowerCase())) {

                PostDTO postDTO = new PostDTO();
                postDTO.setId(postInstance.getId());
                postDTO.setTitle(postInstance.getTitle());
                postDTO.setDescription(postInstance.getDescription());
                postDTO.setCreatedAt(postInstance.getCreatedAt());
                postDTO.setUpdatedAt(postInstance.getUpdatedAt());

                postListDTO.add(postDTO);
            }
        }

        if (postListDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(postListDTO);
        }
    }

    @PostMapping(path = "/post", consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody Post post) throws URISyntaxException {
        if (post == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(post.toString());
            URI location = new URI("http://www.concretepage.com/");
            postService.addPost(post);
            JSONObject jsonObject = new JSONObject();
            return ResponseEntity.created(location).body(jsonObject);
        }
    }

    @PutMapping(path = "/post", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody Post post, @RequestParam int id) {

        if (post == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {

            Post newPost = postService.getPost(id);

            newPost.setTitle(post.getTitle());
            newPost.setDescription(post.getDescription());

            postService.updatePost(id, newPost);

//            List<Post> allPostsCompare = postService.getAllPosts();
//
//            for (var postInstance: allPostsCompare) {
//                if (postInstance.getId() == id) {
//                    post.setCreatedAt(postInstance.getCreatedAt());
//                }
//            }
//
//
//
//            LOGGER.info(post.toString());
////            post.setId(id);
//            postService.updatePost(id, post);

            JSONObject jsonObject = new JSONObject();
            return ResponseEntity.ok().body(jsonObject);
        }
    }

    @DeleteMapping(path = "/post")
    public ResponseEntity<?> deleteUser(@RequestParam int id) {
        postService.deletePost(id);

//        return ResponseEntity.noContent().build();
        JSONObject jsonObject = new JSONObject();
        return ResponseEntity.ok().body(jsonObject);
    }
}
