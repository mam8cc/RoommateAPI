package com.roommateAPI.config;

import com.roommateAPI.dao.AuthenticationDao;
import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.roommateAPI.service")
public class SpringApplication {

    @Bean
    public AuthenticationDao authenticationDao() {
        return new AuthenticationDao();
    }

    @Bean
    public AuthorizationTokenDao authorizationTokenDao() { return new AuthorizationTokenDao(); }

    @Bean
    public UserDao userDao() { return new UserDao(); }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .addScript("classpath:testData.sql")
                .build();
    }
}
