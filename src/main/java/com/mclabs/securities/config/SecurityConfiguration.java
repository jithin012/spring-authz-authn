package com.mclabs.securities.config;

import javax.sql.DataSource;

//import com.mclabs.securities.filter.CustomAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().anyRequest().permitAll();
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.authorizeRequests().anyRequest()
                .authenticated()
                        .and()
                                .httpBasic();
//
//        http.formLogin();
//                .successHandler(new AuthenticationSuccessHandlerImpl())
//                .failureHandler(new AuthenticationFailureHandlerImpl())
//                //.failureUrl("/login")
//                .defaultSuccessUrl("/hello");

//        http.authorizeRequests()
//                //.antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
//                .antMatchers("/login*").permitAll()
//                .antMatchers("/assets/css/**", "assets/js/**", "/images/**").permitAll()
//                .antMatchers("/index*").permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .failureUrl("/login?error=true")
//                .permitAll() // let everyone be able to see it.
//                .defaultSuccessUrl("/", true)
//
//                // .and()
//                // .rememberMe()
//                // .key("supersecretKey")
//                // .tokenRepository(tokenRepository())
//
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login?logout=true")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout", "GET"))
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                ;

    }

    // @Bean
    // public PersistentTokenRepository tokenRepository() {
    //     JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
    //     token.setDataSource(dataSource);
    //     return token;
    // }

    // unblock all javascript, css and html, @Note: only for mvc projects
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/css/**", "/webjars/**", "/js/**");
    }
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                        .passwordEncoder(passwordEncoder());
//        auth
//            .inMemoryAuthentication()
//            .withUser("jithin")
//            .password(passwordEncoder().encode("jithi"))
//            .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
