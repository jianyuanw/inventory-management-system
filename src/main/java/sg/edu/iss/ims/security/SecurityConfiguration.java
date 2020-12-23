package sg.edu.iss.ims.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl) {
        userDetailsService = userDetailsServiceImpl;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ADMIN_URLS).hasAuthority("ADMIN_CLERK")
                .antMatchers(ADMIN_MECHANIC_URLS).hasAnyAuthority("ADMIN_CLERK", "MECHANIC")
                .antMatchers(LOGIN_LOGOUT_URLS).permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/login/success")
                        .failureUrl("/login/error")
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout/success")
                .and()
                    .sessionManagement()
                        .maximumSessions(-1)
                        .expiredUrl("/login/expired")
                        .sessionRegistry(sessionRegistry());
        				
    }

    private final String[] ADMIN_URLS = {
            "/brand/**",
            "/category/**",
            "/subcategory/**",
            "/supplier/**",
            "/report/reorder/**",
            "/form/stockentry/**",
            "/form/stockreturn/**",
            "/product/add/**",
            "/product/save/**",
            "/product/list/**",
            "/product/edit/**",
            "/product/delete/**",
            "/product/reorder/**",
            "/user/**",
            "/transaction/**",
            "/list/**"
    };

    private final String[] ADMIN_MECHANIC_URLS = {
            "/form/stockusage/**",
            "/report/usage/**",
            "/product/details/**",
            "/"
    };

    private final String[] LOGIN_LOGOUT_URLS = {
            "/login/**",
            "/logout/**"
    };
}
