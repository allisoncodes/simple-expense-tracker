package expensetracker.expenseservice.repositories;

import expensetracker.expenseservice.model.Category;
import expensetracker.expenseservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    List<Expense> findByCategory(Category category);
}
