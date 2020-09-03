package expensetracker.aggregateservice.services;

import expensetracker.aggregateservice.model.Category;
import expensetracker.aggregateservice.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpensesByCategory(Category category, String token);
}
