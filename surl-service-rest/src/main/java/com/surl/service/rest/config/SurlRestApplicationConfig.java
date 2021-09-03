
package com.surl.service.rest.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.surl.service")
@EnableJpaRepositories({"com.surl.service.jpa.api", "com.surl.service.jpa.impl"})
@EntityScan("com.surl.service.jpa.entity")
@EnableCaching
public class SurlRestApplicationConfig extends SpringBootServletInitializer {

    @Value("${cache.size.max}")
    private int cacheSizeMax;
    
    @Autowired
    private DataSource dataSource;
    
    public static void main(String[] args) {
        SpringApplication.run(SurlRestApplicationConfig.class, args);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().maximumSize(cacheSizeMax).expireAfterWrite(10, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
