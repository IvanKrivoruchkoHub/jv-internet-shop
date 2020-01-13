package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static UserService userService;

    private static Long USER_ID = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Bucket bucket = bucketService.getByUserId(USER_ID);
        List<Item> items = bucket.getItems();
        orderService.completeOrder(items, userService.get(bucket.getUserId()));
        bucketService.clear(bucket);
        resp.sendRedirect(req.getContextPath() + "/servlet/allUserOrders");
    }
}
