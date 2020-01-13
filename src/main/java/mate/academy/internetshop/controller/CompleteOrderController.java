package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Bucket bucket = bucketService.get(Long.valueOf(req.getParameter("bucket_id")));
        List<Item> items = bucket.getItems();
        orderService.completeOrder(items, userService.get(bucket.getUserId()));
        bucketService.clear(bucket);
        resp.sendRedirect(req.getContextPath() + "/servlet/allOrders");
    }
}
