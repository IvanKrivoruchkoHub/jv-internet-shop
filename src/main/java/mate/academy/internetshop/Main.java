package mate.academy.internetshop;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class Main {
    @Inject
    public static ItemService itemService;

    @Inject
    public static BucketService bucketService;

    @Inject
    public static OrderService orderService;

    @Inject
    public static UserService userService;

    static final Logger logger = Logger.getLogger(Main.class);

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
        Item item1 = new Item();
        item1.setName("Keyboard");
        item1.setPrice(234.00);
        item1 = itemService.create(item1);

        Item item2 = new Item();
        item2.setName("Monitor");
        item2.setPrice(10000.00);
        item2 = itemService.create(item2);

        item2.setPrice(12000.);
        itemService.update(item2);
        logger.info(Storage.items);
        itemService.deleteById(item2.getId());
        itemService.delete(item1);
        logger.info(Storage.items);

        User user1 = new User();
        user1.setName("Ivan Krivoruchko");
        User user2 = new User();
        user2.setName("John Jones");
        user1 = userService.create(user1);
        user2 = userService.create(user2);
        logger.info(Storage.users);

        userService.delete(user1);
        userService.deleteById(user2.getId());
        logger.info(Storage.users);

        logger.info("bucket test");
        Bucket bucket1 = new Bucket();
        Bucket bucket2 = new Bucket();
        bucket1.setUserId(user1.getId());
        bucket2.setUserId(user2.getId());
        bucketService.create(bucket1);
        bucketService.create(bucket2);
        logger.info(Storage.buckets);
        List<Item> items = new ArrayList<>();
        items.add(item1);
        bucket2.setItems(items);
        bucketService.update(bucket2);
        bucketService.addItem(bucket2, item2);
        bucketService.deleteItem(bucket2, item2);
        logger.info(bucketService.getAllItemsFromBucket(bucket2));
        bucketService.clear(bucket2);
        logger.info(bucketService.getAllItemsFromBucket(bucket2));

        Order order1 = new Order();
        order1.setItems(items);
        order1.setUserId(user1.getId());
        order1 = orderService.create(order1);
        logger.info(Storage.orders);
        items.add(item2);
        order1.setItems(items);
        orderService.update(order1);
        logger.info(orderService.get(order1.getOrderId()));
        orderService.delete(order1);
        orderService.completeOrder(items, user1);
        logger.info(orderService.getAll());
        logger.info(orderService.getUserOrders(user1));
    }
}
