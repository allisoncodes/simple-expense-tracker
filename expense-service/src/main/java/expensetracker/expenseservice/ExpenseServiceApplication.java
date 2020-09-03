package expensetracker.expenseservice;

import expensetracker.expenseservice.model.Category;
import expensetracker.expenseservice.model.Expense;
import expensetracker.expenseservice.repositories.CategoryRepository;
import expensetracker.expenseservice.repositories.ExpenseRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@EnableDiscoveryClient
@SpringBootApplication
public class ExpenseServiceApplication {

    private static final Logger log =
            LoggerFactory.getLogger(ExpenseServiceApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(ExpenseServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            // save a few categories
            Category shelter = categoryRepository.save(new Category("Shelter"));
            Category food = categoryRepository.save(new Category("Food"));
            Category transport = categoryRepository.save(new Category("Transport"));
            Category entertainment = categoryRepository.save(new Category("Entertainment"));

            // create some dates
            LocalDate now = LocalDate.now();
            LocalDate date1 = now.minusDays(5);
            LocalDate date2 = now.minusDays(6);
            LocalDate date3 = now.minusDays(10);
            LocalDate date4 = now.minusMonths(1);
            LocalDate rentDate = LocalDate.of(date4.getYear(), date4.getMonth(), 1);
            LocalDate date5 = now.minusDays(18);

            // save a few expenses
            expenseRepository.save(new Expense(date1, new BigDecimal(200), transport, "Car payment"));
            expenseRepository.save(new Expense(date2, new BigDecimal(74.98), food, "Groceries"));
            expenseRepository.save(new Expense(date3, new BigDecimal(20), entertainment, "Movie tickets"));
            expenseRepository.save(new Expense(rentDate, new BigDecimal(750), shelter, "Rent payment"));
            expenseRepository.save(new Expense(date5, new BigDecimal(40), transport, "Gas for car"));

            // fetch all expenses
            log.info("Expenses found with findAll():");
            log.info("------------------------------");
            for (Expense expense : expenseRepository.findAll()) {
                log.info(expense.toString());
            }
            log.info("");

            // fetch an individual expense by ID
            Optional<Expense> expense = expenseRepository.findById(1L);
            log.info("Expense found with findById(1L):");
            log.info("--------------------------------");
            if (expense.isPresent()) {
                log.info(expense.get().toString());
            } else {
                log.info("Expense not found!");
            }
            log.info("");

            // fetch expenses by category
            log.info("Expense found with findByCategory(transport):");
            log.info("---------------------------------------------");
            expenseRepository.findByCategory(transport).forEach(transportExpense -> {
                log.info(transportExpense.toString());
            });
            log.info("");
        };
    }
}
