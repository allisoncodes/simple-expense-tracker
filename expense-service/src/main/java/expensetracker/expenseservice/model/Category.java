package expensetracker.expenseservice.model;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
