package com.epam.esm.controller.configuration;

import com.epam.esm.dao.configuration.DaoConfigMySql;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ControllerConfig.class, DaoConfigMySql.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
