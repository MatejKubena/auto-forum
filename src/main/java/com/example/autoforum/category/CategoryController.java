package com.example.autoforum.category;

import com.example.autoforum.post.Post;
import com.example.autoforum.post.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/home", produces = "application/json")
    public ResponseEntity<?> getCategories() {

        List<Category> categoryList = categoryService.getAllCategories();
        ArrayList<CategoryDTO> categoryDTOList = new ArrayList<>();

        if (categoryService.getAllCategories() == null) {
            return ResponseEntity.notFound().build();
        } else {

            for (var categoryInstance: categoryList) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(categoryInstance.getId());
                categoryDTO.setName(categoryInstance.getName());
                categoryDTOList.add(categoryDTO);
            }

            return ResponseEntity.ok().header("Content-Type", "application/json").body(categoryDTOList);
        }
    }

    @GetMapping(path = "/category", produces = "application/json")
    public ResponseEntity<?> getPost(@RequestParam int id) {

        Category category = categoryService.getCategory(id);

        if (category == null) {
            return ResponseEntity.notFound().build();
        } else {

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(category.getName());

            return ResponseEntity.ok().header("Content-Type", "application/json").body(categoryDTO);
        }
    }
}
