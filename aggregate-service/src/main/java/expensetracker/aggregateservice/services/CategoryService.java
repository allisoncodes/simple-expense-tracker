package expensetracker.aggregateservice.services;

import expensetracker.aggregateservice.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories(String token);
}
