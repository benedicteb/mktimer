package io.brkn.mktimer.web;

import io.brkn.mktimer.domain.Category;
import io.brkn.mktimer.repository.CategoryRepository;
import io.brkn.mktimer.web.forms.CreateCategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/category")
    public Category createCategory(@RequestBody CreateCategoryForm form) {
        Category newCategory = new Category(form.getName());
        categoryRepository.save(newCategory);
        return newCategory;
    }
}
