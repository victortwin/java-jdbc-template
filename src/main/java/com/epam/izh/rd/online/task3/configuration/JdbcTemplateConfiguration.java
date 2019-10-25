package com.epam.izh.rd.online.task3.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfiguration {

    public JdbcTemplate jdbcTemplate() {
        return null; //TODO
    }

    public DataSource dataSource() {
        return null; //TODO
    }
}
