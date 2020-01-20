package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.service.UserService;

public class AuthenticationFilter implements Filter {
    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("userId") == null) {
            processUnAuthenticated(req, resp);
            return;
        }
        try {
            userService.get((Long) req.getSession().getAttribute("userId"));
            chain.doFilter(request, response);
        } catch (NoSuchElementException e) {
            processUnAuthenticated(req, resp);
        }
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
