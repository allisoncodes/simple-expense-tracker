package expensetracker.expenseservice.controllers;

import expensetracker.expenseservice.model.Category;
import expensetracker.expenseservice.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {

        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable Long id) {

        return id != null ? categoryRepository.findById(id).get() : null;
    }

}
