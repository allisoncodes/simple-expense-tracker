package expensetracker.aggregateservice.services;


import com.netflix.discovery.EurekaClient;
import expensetracker.aggregateservice.model.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements
        CategoryService,
        TokenAuthenticatedService,
        GatewayDiscoveryService {

    @Value("${expense-service.locator}")
    private String expenseServiceLocator;

    @Value("${gateway.locator}")
    private String gatewayLocator;

    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;

    public CategoryServiceImpl(RestTemplate restTemplate,
                               @Qualifier("eurekaClient") EurekaClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<Category> getCategories(String token) {

        try {
            String gatewayUrl = getGatewayUrl(discoveryClient, gatewayLocator);

            HttpEntity<String> entity = getEntity(getHeaders(token));

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
