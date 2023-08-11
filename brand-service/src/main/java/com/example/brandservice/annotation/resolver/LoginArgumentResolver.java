package com.example.brandservice.annotation.resolver;

import com.example.brandservice.annotation.Login;
import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.exception.CustomException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String LOGINED_MEMBER = "session_brand_account";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLogin = parameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = BrandAccount.class.isAssignableFrom(parameter.getParameterType());

        return hasLogin && assignableFrom;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();

        BrandAccount brandAccount = (BrandAccount) req.getAttribute(LoginArgumentResolver.LOGINED_MEMBER);
        if(brandAccount == null) throw new CustomException(HttpStatus.UNAUTHORIZED, "you need login");

        return brandAccount;
    }
}
