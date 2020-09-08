package expensetracker.ui.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AggregateServiceImpl implements AggregateService {

    @Value("${aggregate-service.locator}")
    private String aggregateServiceLocator;

    @Value("${gateway.locator}")
    private String gatewayLocator;

    private final RestTemplate restTemplate;
    private final EurekaClient discoveryClient;


    public AggregateServiceImpl(RestTemplate restTemplate,
                                @Qualifier("eurekaClient") EurekaClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public Map<String, BigDecimal> getAggregates(String token) {

        try {
            InstanceInfo instance = discoveryClient.getNextServerFromEureka(gatewayLocator, false);
            String gatewayUrl = instance.getHomePageUrl();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            String uri = UriBuilder
                    .fromUri(gatewayUrl)
                    .path("{aggregate-service}/aggregates")
                    .build(aggregateServiceLocator)
                    .toString();

            ResponseEntity<Map<String, BigDecimal>> record = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, BigDecimal>>() {}
            );
            if (record != null && record.hasBody()) {
                return record.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<String, BigDecimal>();
    }

}
