package com.es.phoneshop.web.filters;

import com.es.phoneshop.security.DosProtectionService;
import com.es.phoneshop.security.impl.DosProtectionServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosProtectionService = DosProtectionServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(dosProtectionService.isAvailable(servletRequest.getLocalAddr(), new Date())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
