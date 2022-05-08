package com.mclabs.securities.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest()
                .authenticated()
                        .and()
                .formLogin()
                .and()
                                .httpBasic();

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

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("jithin")
            .password(passwordEncoder().encode("jithi"))
            .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
