package expensetracker.ui.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


public class Expense implements Serializable {

    private Long id;


    private LocalDate date;

    private BigDecimal amount;

    private Category category;

    private String description;

    protected Expense() {}

    public Expense(LocalDate date, BigDecimal amount, Category category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Expense[id=%d, date='%s', amount='%s', category='%s', description='%s']",
                id, date.toString(), amount.toString(), category.getDescription(), description);
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

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

}
