package expensetracker.ui.config;


import expensetracker.ui.config.login.LoginRequest;
import expensetracker.ui.config.login.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.ws.rs.core.UriBuilder;
import java.util.*;

@Component
public class UIAuthenticationProvider implements AuthenticationProvider {

    @Value("${gateway.locator}")
    private String gatewayLocator;
    @Value("${oauth.url}")
    private String oauthUrl;

    @Value("${grant.type}")
    private String grant_type;
    @Value("${spring.application.name}")
    private String client_id;
    @Value("${client.secret}")
    private String client_secret;

    private final RestTemplate restTemplate;

    public UIAuthenticationProvider(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        LoginRequest loginRequest = new LoginRequest(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String uri = UriBuilder
                .fromUri(oauthUrl)
                .queryParam("client_id", this.client_id)
                .queryParam("client_secret", this.client_secret)
                .queryParam("grant_type", this.grant_type)
                .queryParam("username", loginRequest.getUsername())
                .queryParam("password", loginRequest.getPassword())
                .build()
                .toString();

        try {
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                    uri,
                    entity,
                    LoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LoginResponse loginResponse = response.getBody();



                List<String> scopes =
                        new ArrayList<>(Arrays.asList(
                                loginResponse
                                        .getScope()
                                        .split("\\s+")));

                List<GrantedAuthority> authorities = new ArrayList<>();

                if (scopes.contains("resource.read") && scopes.contains("resource.write")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                Authentication auth = new BearerTokenAuthenticationTokenWithAuthorities(
                        loginResponse.getAccess_token(),
                        authorities);

                auth.setAuthenticated(true);

                return auth;

            } else {
                throw new BadCredentialsException("Authentication Failed!!!");
            }
        } catch (RestClientException e) {
            throw new BadCredentialsException("Authentication Failed!!!", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


}
