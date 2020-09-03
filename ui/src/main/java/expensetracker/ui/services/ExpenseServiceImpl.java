package expensetracker.ui.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import expensetracker.ui.commands.ExpenseCommand;
import expensetracker.ui.converters.ExpenseCommandToExpense;
import expensetracker.ui.dto.ExpenseDTO;
import expensetracker.ui.model.Expense;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Value("${expense-service.locator}")
    private String expenseServiceLocator;

    @Value("${gateway.locator}")
    private String gatewayLocator;

    private String gatewayUrl;

    private final ExpenseCommandToExpense expenseCommandToExpense;
    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;


    ExpenseServiceImpl(ExpenseCommandToExpense expenseCommandToExpense,
                       RestTemplate restTemplate,
                       @Qualifier("eurekaClient") EurekaClient discoveryClient) {
        this.expenseCommandToExpense = expenseCommandToExpense;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    private void init() {

        InstanceInfo instance = discoveryClient.getNextServerFromEureka(gatewayLocator, false);
        gatewayUrl = instance.getHomePageUrl();
    }

    @Override
    public List<Expense> getExpenses(String token) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            String uri = UriBuilder
                    .fromUri(gatewayUrl)
                    .path("{expense-service}/expenses")
                    .build(expenseServiceLocator)
                    .toString();

            ResponseEntity<List<Expense>> record = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Expense>>() {}
            );

            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Expense>();

    }

    @Override
    public Expense saveExpenseCommand(ExpenseCommand command, String token) {

        ExpenseDTO detachedExpense = expenseCommandToExpense.convert(command);

        if (detachedExpense == null) {
            return null;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            HttpEntity<ExpenseDTO> entity = new HttpEntity<>(detachedExpense, headers);

            String uri = UriBuilder
                    .fromUri(gatewayUrl)
                    .path("{expense-service}/expense")
                    .build(expenseServiceLocator)
                    .toString();

            ResponseEntity<Expense> record = restTemplate.postForEntity(
                    uri,
                    entity,
                    Expense.class
            );

            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
