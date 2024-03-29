
package com.rmit.gateway.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.rmit.gateway.utils.SecurityUtils;

@Component
public class RestUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        SecurityUtils.sendError(response, authException, HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

}
