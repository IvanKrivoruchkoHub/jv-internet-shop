package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = Long.valueOf(req.getParameter("user_id"));
        userService.deleteById(userId);
        if (!checkIfAdmin(userId)) {
            Bucket bucket = bucketService.getByUserId(userId);
            bucketService.delete(bucket);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/allUsers");
    }

    private boolean checkIfAdmin(Long userId) {
        return userService.get(userId).getRoles()
                .stream()
                .anyMatch(r -> r.getRoleName().equals(Role.RoleName.ADMIN));
    }
}
