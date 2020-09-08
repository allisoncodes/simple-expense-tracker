package expensetracker.aggregateservice.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public interface TokenAuthenticatedService {

    default HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return headers;
    }

    default HttpEntity getEntity(HttpHeaders headers) {
        return new HttpEntity<>("body", headers);
    }
}
