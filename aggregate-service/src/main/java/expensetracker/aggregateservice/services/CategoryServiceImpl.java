package expensetracker.aggregateservice.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import expensetracker.aggregateservice.model.Category;
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

    public CategoryServiceImpl(RestTemplate restTemplate,
                               @Qualifier("eurekaClient") EurekaClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    private void init() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(gatewayLocator, false);
        this.gatewayUrl = instance.getHomePageUrl();
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

            ResponseEntity<List<Category>> record = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Category>>(){});
            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<Category>();
    }
}
