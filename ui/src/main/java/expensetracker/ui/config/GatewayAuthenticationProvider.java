package expensetracker.ui.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import expensetracker.ui.config.login.LoginRequest;
import expensetracker.ui.config.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
//import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GatewayAuthenticationProvider implements AuthenticationProvider {

    @Value("${gateway.locator}")
    private String gatewayLocator;

    //@Autowired
    //@Qualifier("eurekaClient")
    private final EurekaClient discoveryClient;

    private  String gatewayUrl;

    //@Autowired
    private final RestTemplate restTemplate;

    public GatewayAuthenticationProvider(RestTemplate restTemplate,
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        LoginRequest loginRequest = new LoginRequest(username, password);

        Map<String, String> uriVars= new HashMap<String, String>();
        uriVars.put("client_id", "gateway");
        uriVars.put("client_secret", "secret");
        uriVars.put("grant_type", "password");
        uriVars.put("username", authentication.getName());
        uriVars.put("password", authentication.getCredentials().toString());


        InstanceInfo instance = discoveryClient.getNextServerFromEureka("gateway", false);
        this.gatewayUrl = instance.getHomePageUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                    "http://localhost:8090/uaa/oauth/token?client_id={client_id}&client_secret={client_secret}&grant_type={grant_type}&username={username}&password={password}",//login/oauth2/code/gateway",
                    //this.gatewayUrl,// + "/login",
                    //loginRequest,
                    //this.gatewayUrl + "authenticate",//"uaa/oauth/token",
                    entity, //null,
                    LoginResponse.class,
                    uriVars);

            if (response.getStatusCode() == HttpStatus.OK) {
                LoginResponse loginResponse = response.getBody();

                Authentication auth = new BearerTokenAuthenticationToken(loginResponse.getAccess_token());

                auth.setAuthenticated(true);

                return auth;

                //Collection<? extends GrantedAuthority> authorities = Arrays.stream(loginResponse.getScope().split("\\s+"))
                //        .map(item -> new SimpleGrantedAuthority(item)).collect(Collectors.toList());
                //return new UsernamePasswordAuthenticationToken(username, password, authorities);
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
