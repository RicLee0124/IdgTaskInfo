package com.gildata.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by LiChao on 2018/6/12
 */

@Configuration
public class JdbcConfig {

    @Bean(name = "taskConfigDataSource")
    @Qualifier("taskConfigDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.taskconfig")
    public DataSource taskConfigDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "rabbitmsgDataSource")
    @Qualifier("rabbitmsgDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.rabbitmsg")
    public DataSource rabbitmsgDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "taskinfoDataSource")
    @Qualifier("taskinfoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.taskinfo")
    public DataSource taskinfoDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "taskcCnfigJdbcTemplate")
    public JdbcTemplate taskcCnfigJdbcTemplate(@Qualifier("taskConfigDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "rabbitmsgJdbcTemplate")
    public JdbcTemplate rabbitmsgJdbcTemplate(@Qualifier("rabbitmsgDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "taskinfoJdbcTemplate")
    public JdbcTemplate taskinfoJdbcTemplate(@Qualifier("taskinfoDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
