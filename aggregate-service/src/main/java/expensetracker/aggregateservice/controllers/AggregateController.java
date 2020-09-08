package expensetracker.aggregateservice.controllers;

import expensetracker.aggregateservice.model.Category;
import expensetracker.aggregateservice.services.AggregateService;
import expensetracker.aggregateservice.services.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AggregateController {

    private final CategoryService categoryService;
    private final AggregateService aggregateService;

    public AggregateController(CategoryService categoryService,
                               AggregateService aggregateService) {
        this.categoryService = categoryService;
        this.aggregateService = aggregateService;
    }

    @GetMapping("/aggregates")
    public Map<String, BigDecimal> getAggregates() {

        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getToken().getTokenValue();//jwtUtils.generateJwtToken(authentication);

        Map<String, BigDecimal> aggregates = new HashMap<String, BigDecimal>();

        List<Category> categories = categoryService.getCategories(token);

        for (Category category : categories) {
            aggregates.put(category.getDescription(), aggregateService.aggregate(category, token));
        }

        return aggregates;
    }
}
