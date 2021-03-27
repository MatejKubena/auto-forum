package com.example.autoforum.category;

import com.example.autoforum.favorite.Favorite;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryRepository.findAll());
    }
}
