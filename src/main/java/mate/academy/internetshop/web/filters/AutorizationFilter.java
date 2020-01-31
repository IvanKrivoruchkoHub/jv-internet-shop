package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AutorizationFilter implements Filter {
    @Inject
    private static UserService userService;

    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    private HashMap<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/allUsers", Role.RoleName.ADMIN);
        protectedUrls.put("/servlet/addItem", Role.RoleName.ADMIN);
        protectedUrls.put("/servlet/deleteItem", Role.RoleName.ADMIN);

        protectedUrls.put("/servlet/bucket", Role.RoleName.USER);
        protectedUrls.put("/servlet/addItemToBucket", Role.RoleName.USER);
        protectedUrls.put("/servlet/deleteItemFromBucket", Role.RoleName.USER);
        protectedUrls.put("/servlet/allUserOrders", Role.RoleName.USER);
        protectedUrls.put("/servlet/completeOrder", Role.RoleName.USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();

        if (protectedUrls.get(servletPath) == null) {
            chain.doFilter(request, response);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            User user = userService.get(userId);
            if (verifyRole(user, protectedUrls.get(servletPath))) {
                chain.doFilter(request, response);
            } else {
                processDenied(req, resp);
            }
        } catch (DataProcessingExeption dataProcessingExeption) {
            logger.error(dataProcessingExeption);
            req.setAttribute("errorMsg", dataProcessingExeption.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/denied.jsp").forward(req, resp);
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    @Override
    public void destroy() {

    }
}
