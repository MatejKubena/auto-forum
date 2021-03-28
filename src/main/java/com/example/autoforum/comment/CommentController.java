package com.example.autoforum.comment;

import com.example.autoforum.user.User;
import com.example.autoforum.user.UserService;
import com.example.autoforum.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/comments/{id}", produces = "application/json")
    public ResponseEntity<?> getComments(@PathVariable int id) {
        if (commentService.getAllCommentsByUserId(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().header("Content-Type", "application/json").body(commentService.getAllCommentsByUserId(id));
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

    @PostMapping(path = "/comment", consumes = "application/json")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) throws URISyntaxException {
        if (comment == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            URI location = new URI("http://www.concretepage.com/");
            commentService.addComment(comment);
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(path = "/comment/{id}", consumes = "application/json")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment, @PathVariable int id) {
        commentService.updateComment(id, comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
