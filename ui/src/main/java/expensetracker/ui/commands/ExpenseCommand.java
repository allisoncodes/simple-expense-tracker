package expensetracker.ui.commands;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ExpenseCommand {

    @NotNull
    @NotEmpty
    @NotBlank
    private String date;

    @Min(0)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private Long categoryId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max=30)
    private String description;

    public ExpenseCommand() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
