package com.example.autoforum.category;

import com.example.autoforum.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryRepository.findAll());
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
