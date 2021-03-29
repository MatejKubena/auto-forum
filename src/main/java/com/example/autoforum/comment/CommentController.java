package com.example.autoforum.comment;

import com.example.autoforum.user.User;
import com.example.autoforum.user.UserService;
import com.example.autoforum.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping(path = "/comments/{id}", produces = "application/json")
    public ResponseEntity<?> getComments(@PathVariable int id) {
        if (commentService.getAllCommentsByUserId(userService.getUser(id)) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentService.getAllCommentsByUserId(userService.getUser(id)));
        }
    }

    @GetMapping(path = "/comment/{id}", produces = "application/json")
    public ResponseEntity<?> getComment(@PathVariable int id) {
        if (commentService.getComment(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentService.getComment(id));
        }
    }

    @PostMapping(path = "/comment", consumes = "multipart/form-data")
    public ResponseEntity<?> addComment(@RequestPart Comment comment) throws URISyntaxException {
        if (comment == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(comment.toString());
            URI location = new URI("http://www.concretepage.com/");
            commentService.addComment(comment);
            return ResponseEntity.created(location).build();
        }
    }

//    @PostMapping(path = "/comment", consumes = "application/json")
//    public ResponseEntity<?> addComment(@RequestBody Comment comment) throws URISyntaxException {
//        LOGGER.info(comment.toString());
//        if (comment == null) {
//            return ResponseEntity.unprocessableEntity().build();
//        } else {
//            URI location = new URI("http://www.concretepage.com/");
//            commentService.addComment(comment);
//            return ResponseEntity.created(location).build();
//        }
//    }

//    @PutMapping(path = "/user/{id}", consumes = "multipart/form-data")
//    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestPart User user, @RequestPart MultipartFile picture) throws IOException {
//        User existingUser = userService.getUser(id);
//        if (existingUser == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            user.setPicture(picture.getBytes());
//            LOGGER.info(user.toString());
//            user.setId(id);
//            userService.updateUser(id, user);
//            return ResponseEntity.ok().build();
//        }
//    }

    @PutMapping(path = "/comment/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateComment(@RequestPart Comment comment, @PathVariable int id) {
        if (comment == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            LOGGER.info(comment.toString());
            comment.setId(id);
            commentService.updateComment(id, comment);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping(path = "/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
