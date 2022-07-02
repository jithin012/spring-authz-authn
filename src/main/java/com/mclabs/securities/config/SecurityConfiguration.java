package com.mclabs.securities.config;

import com.mclabs.securities.filter.CustomAuthenticationFilter;
import com.mclabs.securities.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        /** <----- csrf: enable cookie based csrf protection-----> */
        // withHttpOnlyFalse: The browser will allow javascript originating from the same origin to access the cookie
        // in SPA, CSRF token can not include in HTML. Hence it needs to include in cookie sent to user browser.
        // So the client side, we EXTRACT TOKEN  FROM THIS COOKIE ALSO YOU NEED A PERMISSION TO READ THE COOKIE
//        http.csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//
        http.csrf().disable();

        /** Don't create a session/token by spring. We will handle session by jwt later. */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /** <---- content security policy: should use in prod ---->  */
        //http.headers().contentSecurityPolicy("script-src: http://mydomain...");

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/api/confirmAccount/**", "/api/password/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    /** <---- http firewall - Block NON-ASCII character ----> */
    @Bean
    public HttpFirewall firewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                        .passwordEncoder(bCryptPasswordEncoder);
    }

}
