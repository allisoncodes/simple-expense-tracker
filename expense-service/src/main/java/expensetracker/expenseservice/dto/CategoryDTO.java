package expensetracker.expenseservice.dto;


public class CategoryDTO {

    private Long id;

    private String description;

    protected CategoryDTO() {}

    public CategoryDTO(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, description='%s']",
                id, description);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
