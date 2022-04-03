package com.epam.esm.controller.configuration;

import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.interfaces.CertificateDao;
import com.epam.esm.dao.interfaces.TagDao;
import com.epam.esm.logic.impl.CertificateLogicImpl;
import com.epam.esm.logic.impl.TagLogicImpl;
import com.epam.esm.logic.interfaces.CertificateLogic;
import com.epam.esm.logic.interfaces.TagLogic;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class AppConfig {
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/gifts");
        dataSource.setUsername("root");
        dataSource.setPassword("rebel15386183");
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(30);
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

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }
}
