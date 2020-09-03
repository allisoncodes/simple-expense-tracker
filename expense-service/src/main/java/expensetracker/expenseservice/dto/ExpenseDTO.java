package expensetracker.expenseservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ExpenseDTO {

    private Long id;

    private LocalDate date;

    private BigDecimal amount;

    private Long categoryId;

    private String description;

    protected ExpenseDTO() {}

    public ExpenseDTO(LocalDate date, BigDecimal amount, Long categoryId, String description) {
        this.date = date;
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Expense[id=%d, date='%s', amount='%s', category='%s', description='%s']",
                id, date.toString(), amount.toString(), categoryId.toString(), description);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }
}
