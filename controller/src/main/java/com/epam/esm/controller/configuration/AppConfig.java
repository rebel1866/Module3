package com.epam.esm.controller.configuration;

import com.epam.esm.controller.exceptions.UiControllerException;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.logic.impl.CertificateLogicImpl;
import com.epam.esm.logic.impl.TagLogicImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm.controller")
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
    public CertificateDaoImpl certificateDao() {
        CertificateDaoImpl certificateDao = new CertificateDaoImpl();
        certificateDao.setJdbcTemplate(jdbcTemplate());
        return certificateDao;
    }

    @Bean
    public TagDaoImpl tagDao() {
        TagDaoImpl tagDao = new TagDaoImpl();
        tagDao.setJdbcTemplate(jdbcTemplate());
        return tagDao;
    }

    @Bean
    public CertificateLogicImpl certificateLogic() {
        CertificateLogicImpl certificateLogic = new CertificateLogicImpl();
        certificateLogic.setCertificateDao(certificateDao());
        return certificateLogic;
    }

    @Bean
    public TagLogicImpl tagLogic() {
        TagLogicImpl tagLogic = new TagLogicImpl();
        tagLogic.setTagDao(tagDao());
        return tagLogic;
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
