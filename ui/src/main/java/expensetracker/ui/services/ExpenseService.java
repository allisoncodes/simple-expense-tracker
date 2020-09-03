package expensetracker.ui.services;


import expensetracker.ui.commands.ExpenseCommand;
import expensetracker.ui.dto.ExpenseDTO;
import expensetracker.ui.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpenses(String token);

    Expense saveExpenseCommand(ExpenseCommand command, String token);
}
