package com.example.brandservice.config;

import com.example.brandservice.annotation.resolver.LoginArgumentResolver;
import com.example.brandservice.filter.RequestResponseWrapperFilter;
import com.example.brandservice.interceptor.BrandLogInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(brandLogInterceptor())
            .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
        resolverList.add(new LoginArgumentResolver());
    }

    @Bean
    public BrandLogInterceptor brandLogInterceptor() {
        return new BrandLogInterceptor();
    }

    @Bean
    public FilterRegistrationBean<RequestResponseWrapperFilter> Filters() {
        FilterRegistrationBean<RequestResponseWrapperFilter> registrationBean = new FilterRegistrationBean<RequestResponseWrapperFilter>();
        registrationBean.setFilter(new RequestResponseWrapperFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestResponseWrapperFilter");
        return registrationBean;
    }

}
