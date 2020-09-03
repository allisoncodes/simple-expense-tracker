package expensetracker.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public GatewayAuthenticationProvider authenticationProvider() {
//        GatewayAuthenticationProvider authProvider = new GatewayAuthenticationProvider();
//        return authProvider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .authorizeRequests()
//                .antMatchers("/", "index", "index.html").permitAll()//authorize -> authorize
////                        .antMatchers("/").permitAll()
////                        .antMatchers("index").permitAll()
////                        .antMatchers("index.html").permitAll()
//                        .anyRequest().authenticated()
//                //)
//                //.oauth2ResourceServer()//OAuth2ResourceServerConfigurer::jwt)
//                //.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("http://localhost:8090/uaa/oauth/token"))//OAuth2ResourceServerConfigurer::authenticationEntryPoint)
//                //.and()
////                .oauth2Client()
////                .and()
////                .oauth2Login()
//                .loginForm()
//                .defaultSuccessUrl("/expenses", true)
//            .and()
//            .logout()
//                .logoutSuccessUrl("/")
//                .permitAll()
//            ;


        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/", "index", "index.html").permitAll()
                .anyRequest().authenticated()
                .and()
            //.oauth2Login()
//                .oauth2ResourceServer()
//                .and()
            .formLogin()
                //.loginPage("http://localhost:8080/login/oauth2/code/gateway")
                //.loginPage("http://localhost:8080/authenticate")
                .defaultSuccessUrl("/expenses", true)
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/favicon.ico",
                        "/icons/**",
                        "/images/**",
                        "/css/**",
                        "/js/**",
                        "/vendor/**",
                        "/h2-console/**");
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password("password")
//                    .roles("USER")
//                    .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
