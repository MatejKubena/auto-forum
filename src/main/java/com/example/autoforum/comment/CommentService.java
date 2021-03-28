package com.example.autoforum.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return new ArrayList<>(commentRepository.findAll());
    }

    public Comment getComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void updateComment(int id, Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getAllCommentsByUserId(int id) {
        return new ArrayList<>(commentRepository.findCommentsByUserId(id));
    }
}
