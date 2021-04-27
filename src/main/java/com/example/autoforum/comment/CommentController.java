package com.example.autoforum.comment;

import com.example.autoforum.post.PostService;
import com.example.autoforum.user.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = "/mycomments", produces = "application/json")
    public ResponseEntity<?> getMyComments(@RequestParam int id) {

        List<Comment> commentList = commentService.getAllCommentsByUserId(userService.getUser(id));
        ArrayList<CommentDTO> commentListDTO = new ArrayList<>();

        if (commentService.getAllCommentsByUserId(userService.getUser(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var commentInstance: commentList) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(commentInstance.getId());
                commentDTO.setDescription(commentInstance.getDescription());
                commentDTO.setCreatedAt(commentInstance.getCreatedAt());
                commentDTO.setUpdatedAt(commentInstance.getUpdatedAt());
                commentListDTO.add(commentDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentListDTO);
        }
    }

    @GetMapping(path = "/comments", produces = "application/json")
    public ResponseEntity<?> getComments(@RequestParam int id) {

        List<Comment> commentList = commentService.getAllCommentsByPostId(postService.getPost(id));
        ArrayList<CommentDTO> commentListDTO = new ArrayList<>();

        if (commentService.getAllCommentsByPostId(postService.getPost(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var commentInstance: commentList) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(commentInstance.getId());
                commentDTO.setUserId(commentInstance.getUserId());
                commentDTO.setDescription(commentInstance.getDescription());
                commentDTO.setCreatedAt(commentInstance.getCreatedAt());
                commentDTO.setUpdatedAt(commentInstance.getUpdatedAt());
                commentListDTO.add(commentDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentListDTO);


        }
    }

    @GetMapping(path = "/comment", produces = "application/json")
    public ResponseEntity<?> getComment(@RequestParam int id) {

        Comment comment = commentService.getComment(id);

        if (commentService.getComment(id) == null) {
            return ResponseEntity.notFound().build();
        } else {

            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setUserId(comment.getUserId());
            commentDTO.setDescription(comment.getDescription());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setUpdatedAt(comment.getUpdatedAt());

            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentDTO);

//            return ResponseEntity.ok().header("Content-Type", "application/json").body(comment);
        }
    }

    @PostMapping(path = "/comment", consumes = "application/json")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) throws URISyntaxException {
        if (comment == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(comment.toString());
            URI location = new URI("http://www.concretepage.com/");
            commentService.addComment(comment);
            JSONObject jsonObject = new JSONObject();
            return ResponseEntity.created(location).body(jsonObject);
        }
    }

    @PutMapping(path = "/comment", consumes = "application/json")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment, @RequestParam int id) {
        if (comment == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {

            List<Comment> allCommentsCompare = commentService.getAllComments();

            for (var serviceInstance: allCommentsCompare) {
                if (serviceInstance.getId() == id) {
                    comment.setCreatedAt(serviceInstance.getCreatedAt());
                }
            }

            LOGGER.info(comment.toString());
            comment.setId(id);
            commentService.updateComment(id, comment);

            JSONObject jsonObject = new JSONObject();
            return ResponseEntity.ok().body(jsonObject);
        }
    }

    @DeleteMapping(path = "/comment")
    public ResponseEntity<?> deleteComment(@RequestParam int id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
