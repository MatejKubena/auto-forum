package com.example.autoforum.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(postRepository.findAll());
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }

    public void updatePost(int id, Post post) {
        postRepository.save(post);
    }

    public void deletePost(int id) {
        postRepository.deleteById(id);
    }
}
