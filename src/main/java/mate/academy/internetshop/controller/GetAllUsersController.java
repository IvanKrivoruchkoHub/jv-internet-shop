package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllUsersController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user1 = new User();
        user1.setName("John Jones");
        User user2 = new User();
        user2.setName("Ivan Krivoruchko");
        userService.create(user1);
        userService.create(user2);
        List<User> userList = userService.getAll();
        req.setAttribute("users", userList);
        req.getRequestDispatcher("WEB-INF/views/allUsers.jsp").forward(req, resp);
    }
}
