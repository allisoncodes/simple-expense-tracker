package expensetracker.expenseservice.converters;

import expensetracker.expenseservice.dto.ExpenseDTO;
import expensetracker.expenseservice.model.Category;
import expensetracker.expenseservice.model.Expense;
import expensetracker.expenseservice.repositories.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExpenseDTOToExpense implements Converter<ExpenseDTO, Expense> {

    private final CategoryRepository categoryRepository;

    public ExpenseDTOToExpense(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Expense convert(ExpenseDTO source) {

        if (source == null) {
            return null;
        }

        Optional<Category> categoryOptional = categoryRepository.findById(source.getCategoryId());

        if (!categoryOptional.isPresent()) {
            return null;
        }

        final Expense expense = new Expense(
                source.getDate(),
                source.getAmount(),
                categoryOptional.get(),
                source.getDescription()
        );

        return expense;
    }
}
