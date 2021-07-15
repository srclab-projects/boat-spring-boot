package sample.java.xyz.srclab.spring.boot.web.servlet;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.srclab.spring.boot.web.exception.ExceptionResponseBody;
import xyz.srclab.spring.boot.web.exception.WebExceptions;
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

public class TestFilter extends OncePerRequestFilter {

    @Resource
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

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
        } catch (Exception e) {
            HttpHeaders header = new HttpHeaders();
            header.set(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseEntity<ExceptionResponseBody> responseEntity = new ResponseEntity<>(
                WebExceptions.toExceptionResponseBody(e),
                header,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
            WebServlets.writeResponseEntity(response, responseEntity, (body, out) -> {
                try {
                    mappingJackson2HttpMessageConverter.getObjectMapper().writeValue(out, body);
                } catch (IOException ioException) {
                    throw new IllegalStateException((ioException));
                }
                return null;
            });
        }
    }
}
