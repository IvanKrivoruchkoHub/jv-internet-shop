package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setPassword(req.getParameter("psw"));
        userService.create(user);
        Bucket bucket = new Bucket();
        bucket.setUserId(user.getId());
        bucketService.create(bucket);
        resp.sendRedirect(req.getContextPath() + "/servlet/allUsers");
    }
}
