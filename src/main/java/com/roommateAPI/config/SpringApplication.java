package com.roommateAPI.config;

import com.roommateAPI.service.TokenService;
import com.roommateAPI.utility.UniqueIdentifierGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.roommateAPI")
@MapperScan("com.roommateAPI.dao")
public class SpringApplication {

    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public UniqueIdentifierGenerator uniqueIdentifierGenerator() {
        return new UniqueIdentifierGenerator();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .addScript("classpath:testData.sql")
                .build();
    }
}
