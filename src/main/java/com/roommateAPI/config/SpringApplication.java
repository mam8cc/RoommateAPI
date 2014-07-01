package com.roommateAPI.config;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.roommateAPI.service")
@MapperScan("com.roommateAPI.dao")
public class SpringApplication {

    @Bean
    public AuthorizationTokenDao authorizationTokenDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(AuthorizationTokenDao.class);
    }
    @Bean
    public UserDao userDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(UserDao.class);
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
