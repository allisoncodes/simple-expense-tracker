package expensetracker.aggregateservice.services;

import expensetracker.aggregateservice.model.Category;

import java.math.BigDecimal;

public interface AggregateService {

    BigDecimal aggregate(Category category, String token);
}
