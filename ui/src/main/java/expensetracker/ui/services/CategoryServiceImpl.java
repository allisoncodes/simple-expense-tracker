package expensetracker.ui.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import expensetracker.ui.model.Category;
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
public class CategoryServiceImpl implements CategoryService {

    @Value("${expense-service.locator}")
    private String expenseServiceLocator;

    @Value("${gateway.locator}")
    private String gatewayLocator;

    private String gatewayUrl;

    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;


    CategoryServiceImpl(RestTemplate restTemplate,
                        @Qualifier("eurekaClient") EurekaClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    private void init() {

        InstanceInfo instance = discoveryClient.getNextServerFromEureka(gatewayLocator, false);
        gatewayUrl = instance.getHomePageUrl();

    }

    @Override
    public List<Category> getCategories(String token) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            String uri = UriBuilder
                    .fromUri(gatewayUrl)
                    .path("{expense-service}/categories")
                    .build(expenseServiceLocator)
                    .toString();

            ParameterizedTypeReference<List<Category>>  parameterizedTypeReference =
                    new ParameterizedTypeReference<List<Category>>(){};


            ResponseEntity<List<Category>> record = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    parameterizedTypeReference);
            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Category>();
    }

    @Override
    public Category getCategory(Long id, String token) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            String uri = UriBuilder
                    .fromUri(gatewayUrl)
                    .path("{expense-service}/category/{id}")
                    .build(expenseServiceLocator, id)
                    .toString();

            ResponseEntity<Category> record = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Category>(){});
            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getCategoryDescription(Long id, String token) {

        Category category = getCategory(id, token);

        return category != null ? category.getDescription() : null;
    }
}
