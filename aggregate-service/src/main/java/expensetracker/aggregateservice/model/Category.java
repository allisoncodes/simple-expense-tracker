package expensetracker.aggregateservice.model;


public class Category {

    private Long id;

    private String description;

    protected Category() {}

    public Category(String description) {
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
