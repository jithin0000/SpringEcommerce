package com.jithin.Ecommerce.security;

import com.google.gson.Gson;
import com.jithin.Ecommerce.dto.InvalidLoginResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        String responseJson = new Gson().toJson(invalidLoginResponse);

        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(responseJson);

    }
}
