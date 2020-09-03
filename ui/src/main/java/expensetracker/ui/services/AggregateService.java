package expensetracker.ui.services;

import java.math.BigDecimal;
import java.util.Map;

public interface AggregateService {

    Map<String, BigDecimal> getAggregates(String token);
}
