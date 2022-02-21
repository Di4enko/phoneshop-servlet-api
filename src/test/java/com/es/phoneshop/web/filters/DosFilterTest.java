package com.es.phoneshop.web.filters;

import com.es.phoneshop.security.DosProtectionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private ServletRequest request;
    @Mock
    private ServletResponse response;
    @Mock
    private FilterChain filterChain;
    private DosFilter dosFilter = new DosFilter();
    private DosProtectionService dosProtectionService;

    @Before
    public void setup() throws ServletException {
        dosFilter.init(filterConfig);
        when(request.getLocalAddr()).thenReturn("ip");
    }

    @Test
    public void doFilterTest() throws ServletException, IOException {
        dosFilter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
}
