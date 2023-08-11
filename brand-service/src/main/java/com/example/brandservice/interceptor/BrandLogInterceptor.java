package com.example.brandservice.interceptor;

import com.example.brandservice.annotation.resolver.LoginArgumentResolver;
import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class BrandLogInterceptor implements HandlerInterceptor {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final List<String> EXCLUDE_URL_LIST = Arrays.asList("/login");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(loggingExclude(request)) {
            return true;
        }

        if (EXCLUDE_URL_LIST.contains(request.getRequestURI())) {
            return true;
        }

        try {
            String method = request.getMethod();
            String url = request.getRequestURI();
            String userId = request.getHeader("user_id");
            String paramsStr = "";

            // Log template
            String Template = "request %s, api:[%s], userID:[%s], parameter: %s";
            if ("POST".equals(method)) {
                try {
                    paramsStr = StreamUtils.copyToString(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
                } catch (Exception e) {
                    log.info("preHandle exception - 1 :: {}", e.toString());
                    return true;
                }
            } else if ("GET".equals(method)) {
                paramsStr = request.getQueryString();
            }

            // userId가 있는 경우에만. 로그아웃상태는 unknown
            if (StringUtils.isNotEmpty(userId)) {
                BrandAccount brandAccount = brandService.findByLoginIdWithBrand(userId);

                if (brandAccount != null) {
                    request.setAttribute(LoginArgumentResolver.LOGINED_MEMBER, brandAccount);
                }
            } else {
                userId = "unknown";
            }

            log.info(String.format(Template, method, url, userId, paramsStr));
            return true;
        } catch (Exception e) {
            log.info("preHandle exception - 2 :: {}", e.toString());
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(loggingExclude(request)) {
            return;
        }

        try {
            // Log template
            String Template = "response, userID:[%s], Status:[%s], Body: %s";
            String userId = request.getHeader("user_id");
            final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
            if (cachingResponse.getContentType() != null && cachingResponse.getContentType().contains("application/json")) {
                if (cachingResponse.getContentAsByteArray() != null && cachingResponse.getContentAsByteArray().length != 0) {
                    log.info(String.format(Template, userId, cachingResponse.getStatus(), objectMapper.readTree(cachingResponse.getContentAsByteArray())));
                }
            }
        } catch (Exception e) {
            log.info("afterCompletion exception :: {}", e.toString());
        }
    }

    private boolean loggingExclude(HttpServletRequest request) {
        return request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper");
    }

}
