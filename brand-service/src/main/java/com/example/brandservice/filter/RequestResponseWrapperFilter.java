package com.example.brandservice.filter;

import com.example.brandservice.interceptor.CachingRequestWrapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

@WebFilter(filterName="requestResponseWrapperFilter", urlPatterns="/*")
public class RequestResponseWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;

        Map<String, String[]> parameterMap = request.getParameterMap();
        if(request instanceof HttpServletRequest) {
            requestWrapper = new CachingRequestWrapper((HttpServletRequest)request);
        }

        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);

        if(requestWrapper==null) {
            chain.doFilter(request, wrappingResponse);
        }else {
            chain.doFilter(requestWrapper, wrappingResponse);
        }
        wrappingResponse.copyBodyToResponse();
    }

    @Override
    public void destroy() {
        // do nothing
    }

}
