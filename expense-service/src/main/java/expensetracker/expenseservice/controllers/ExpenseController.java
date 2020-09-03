package expensetracker.expenseservice.controllers;

import expensetracker.expenseservice.converters.ExpenseDTOToExpense;
import expensetracker.expenseservice.dto.ExpenseDTO;
import expensetracker.expenseservice.model.Category;
import expensetracker.expenseservice.model.Expense;
import expensetracker.expenseservice.repositories.CategoryRepository;
import expensetracker.expenseservice.repositories.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseDTOToExpense expenseDTOToExpense;

    ExpenseController(ExpenseRepository expenseRepository,
                      CategoryRepository categoryRepository,
                      ExpenseDTOToExpense expenseDTOToExpense) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.expenseDTOToExpense = expenseDTOToExpense;
    }

    @GetMapping("/expenses")
    public List<Expense> getExpenses() {

        List<Expense> expenses = expenseRepository.findAll();

        List<Expense> sortedExpenses = expenses.stream()
                .sorted()
                .collect(Collectors.toList());

        return sortedExpenses;
    }

    @GetMapping("/expenses/{categoryDescription}")
    public List<Expense> getExpensesByCategory(@PathVariable String categoryDescription) {

        Optional<Category> category = categoryRepository.findByDescription(categoryDescription);

        List<Expense> expenses = category.isPresent() ? expenseRepository.findByCategory(category.get()) : new ArrayList<Expense>();

        return expenses;
    }

    @PostMapping("expense")
    public Expense addExpense(@RequestBody ExpenseDTO expenseDTO) {

        Expense detachedExpense = expenseDTOToExpense.convert(expenseDTO);

        if (detachedExpense == null) {
            return null;
        }

        Expense savedExpense = expenseRepository.save(detachedExpense);

        return savedExpense;
    }
}
