package mate.academy.internetshop.factory;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.dao.impl.BucketDaoImpl;
import mate.academy.internetshop.dao.impl.ItemDaoImpl;
import mate.academy.internetshop.dao.impl.OrderDaoImpl;
import mate.academy.internetshop.dao.impl.UserDaoImpl;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class DaoAndServiceFactory {
    private static BucketDao bucketDaoInstance;
    private static ItemDao itemDaoInstance;
    private static OrderDao orderDaoInstance;
    private static UserDao userDaoInstance;

    private static BucketService bucketServiceInstance;
    private static ItemService itemServiceInstance;
    private static OrderService orderServiceInstance;
    private static UserService userServiceInstance;

    public static BucketDao getBucketDaoInstance() {
        if (bucketDaoInstance == null){
            bucketDaoInstance = new BucketDaoImpl();
        }
        return bucketDaoInstance;
    }

    public static ItemDao getItemDaoInstance() {
        if (itemDaoInstance == null){
            itemDaoInstance = new ItemDaoImpl();
        }
        return itemDaoInstance;
    }

    public static OrderDao getOrderDaoInstance() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoImpl();
        }
        return orderDaoInstance;
    }

    public static UserDao getUserDaoInstance() {
        if (userDaoInstance == null){
            userDaoInstance = new UserDaoImpl();
        }
        return userDaoInstance;
    }

    public static BucketService getBucketServiceInstance() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }

    public static ItemService getItemServiceInstance() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static OrderService getOrderServiceInstance() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }
}
