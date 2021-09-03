
package com.surl.service.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/*").permitAll()
                .antMatchers(HttpMethod.POST).hasAnyRole("USER").and().csrf().disable();
        httpSecurity.authorizeRequests().anyRequest().denyAll();
        
    }
}