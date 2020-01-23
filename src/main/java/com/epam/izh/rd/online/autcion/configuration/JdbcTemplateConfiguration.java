package com.epam.izh.rd.online.autcion.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return null;
    }

    @Bean
    public DataSource dataSource() {
        return null;
    }
}
