package com.lucci.webadmin.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class WebSecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User consultant1 = new User("consultant1", "password", Arrays.asList(
            new SimpleGrantedAuthority("ROLE_CONSULTANT"),
            new SimpleGrantedAuthority("ROLE_USER")
        ));
        User consultant2 = new User("consultant2", "password", Arrays.asList(
            new SimpleGrantedAuthority("ROLE_CONSULTANT"),
            new SimpleGrantedAuthority("ROLE_USER")
        ));
        return new InMemoryUserDetailsManager(Arrays.asList(
            consultant1, consultant2
        ));
    }
}
