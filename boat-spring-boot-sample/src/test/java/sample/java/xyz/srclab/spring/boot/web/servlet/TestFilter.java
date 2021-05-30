package sample.java.xyz.srclab.spring.boot.web.servlet;

import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.srclab.spring.boot.web.exception.EnableWebExceptionService;
import xyz.srclab.spring.boot.web.exception.WebExceptionService;
import xyz.srclab.spring.boot.web.servlet.WebServlets;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableWebExceptionService
public class TestFilter extends OncePerRequestFilter {

    @Resource
    private WebExceptionService webExceptionService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String p1 = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("p1", Collections.singletonList(p1));
        HttpServletRequest newRequest = WebServlets.newPreparedHttpServletRequest(request, parameters);
        try {
            filterChain.doFilter(newRequest, response);
        } catch (Throwable e) {
            WebServlets.writeResponseEntity(response, webExceptionService.toResponseEntity(e));
        }
    }
}
