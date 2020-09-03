package expensetracker.aggregateservice.services;

import expensetracker.aggregateservice.model.Category;
import expensetracker.aggregateservice.model.Expense;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AggregateServiceImpl implements AggregateService {

    private final ExpenseService expenseService;

    public AggregateServiceImpl(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public BigDecimal aggregate(Category category, String token) {

        List<Expense> expenses = expenseService.getExpensesByCategory(category, token);

        BigDecimal sum = new BigDecimal(0);
        for (Expense expense : expenses) {
            sum = expense.getAmount().add(sum);
        }

        return sum;
    }
}
