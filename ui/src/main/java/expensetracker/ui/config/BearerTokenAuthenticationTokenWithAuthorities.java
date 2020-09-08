package expensetracker.ui.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class BearerTokenAuthenticationTokenWithAuthorities extends AbstractAuthenticationToken {

    private String token;

    public BearerTokenAuthenticationTokenWithAuthorities(String token,
            Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public Object getCredentials() {
        return this.getToken();
    }

    public Object getPrincipal() {
        return this.getToken();
    }

}
