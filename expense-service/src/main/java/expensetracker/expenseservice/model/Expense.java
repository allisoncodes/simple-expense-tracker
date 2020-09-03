package expensetracker.expenseservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Expense implements Comparable<Expense>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate date;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
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


    @Override
    public int compareTo(Expense o) {

        if (this.date.compareTo(o.date) != 0) {
            // return in reverse chronological order
            return -1 * this.date.compareTo(o.date);
        } else {
            // return in reverse dollar value order
            return -1 * this.amount.compareTo(o.amount);
        }
    }
}
