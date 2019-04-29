package com.le.sso.handler;

import com.le.core.rest.R;
import com.le.core.rest.RCode;
import com.le.core.util.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 严秋旺
 * @since 2019-04-27 23:29
 **/
@Component
public class AuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        R r =new R(RCode.tokenNone);
        String json = JsonUtils.toJson(r);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
