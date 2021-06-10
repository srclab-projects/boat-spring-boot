package test.xyz.srclab.spring.boot.web.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class SecurityController {

    @RequestMapping("context")
    public Object testServlet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext;
    }
}
