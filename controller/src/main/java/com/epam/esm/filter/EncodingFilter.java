package com.epam.esm.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter (urlPatterns = "/*")
public class EncodingFilter extends HttpFilter {
    private String encoding;
    private static final String ENCODING_KEY = "requestEncoding";

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(ENCODING_KEY);
        if (encoding == null) {
            encoding = "UTF-8";
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        if (null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }

    public void destroy() {
    }
}
