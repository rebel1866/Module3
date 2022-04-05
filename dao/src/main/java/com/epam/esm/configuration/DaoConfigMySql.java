package com.epam.esm.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class DaoConfigMySql {
    private final static String propertiesPath = "C:\\Users\\Stas\\IdeaProjects\\Gifts2\\dao\\src\\main\\" +
            "resources\\databaseConfig.properties";  /* This absolute path is used only for deployment with intellij idea.
                     When deploying on actual server following relative path will be used: databaseConfig.properties */
    @Bean
    public BasicDataSource dataSource() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesPath));
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(DaoConfigMySql.class);
            logger.error("Database configuration failure");
        }
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("db.driver"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty("db.initial_size")));
        dataSource.setMaxActive(Integer.parseInt(properties.getProperty("db.max_active")));
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
