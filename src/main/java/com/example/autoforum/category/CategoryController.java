package com.example.autoforum.category;


import org.springframework.stereotype.Service;

@Service
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


}
