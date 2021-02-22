package com.example.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.security.Security.ApplicationUserPermission.*;
import static com.example.security.Security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               // .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE,"management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
        .defaultSuccessUrl("/courses",true)
                .passwordParameter("password")
                .usernameParameter("username")
        .and()
        .rememberMe()
                .rememberMeParameter("remember-me")
        .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
        .key("The MD5 hashed key value")
        .and()
        .logout()
        .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                .clearAuthentication(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID","rememeber-me")
        .logoutSuccessUrl("/login")
        ;
                //.httpBasic();
    }
//  retrive user details from the database
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetails nirmal = User.builder()
                .username("Nirmal")
                .password(passwordEncoder.encode("password"))
               // .roles(STUDENT.name())   //ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthority())
                .build();
        //creating another user named admin and role for it
        UserDetails ritu = User.builder()
                .username("Ritu")
                .password(passwordEncoder.encode("password123"))
              //  .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        UserDetails swagat = User.builder()
                .username("swagat")
                .password(passwordEncoder.encode("password121"))
               // .roles(ADMINTRAINE.name())
                .authorities(ADMINTRAINE.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(nirmal,
                ritu,
                swagat
        );
    }
}
