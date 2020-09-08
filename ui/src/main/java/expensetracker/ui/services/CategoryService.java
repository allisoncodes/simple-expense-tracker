package expensetracker.ui.services;


import expensetracker.ui.model.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getCategories(String token);

    Category getCategory(Long id, String token);

    String getCategoryDescription(Long id, String token);
}
