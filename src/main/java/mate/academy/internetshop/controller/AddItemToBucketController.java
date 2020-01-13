package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    private static final Long USER_ID = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item item = itemService.get(Long.valueOf(req.getParameter("item_id")));
        Bucket bucket = bucketService.getByUserId(USER_ID);
        bucketService.addItem(bucket, item);
        req.setAttribute("items", itemService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/allItems.jsp").forward(req, resp);
    }
}
