package mate.academy.internetshop.lib;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.factory.DaoAndServiceFactory;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AnnotatedClassMap {
    private static final Map<Class, Object> classMap = new HashMap<>();
    
    static  {
        classMap.put(BucketDao.class, DaoAndServiceFactory.getBucketDaoInstance());
        classMap.put(ItemDao.class, DaoAndServiceFactory.getItemDaoInstance());
        classMap.put(OrderDao.class, DaoAndServiceFactory.getOrderDaoInstance());
        classMap.put(UserDao.class, DaoAndServiceFactory.getUserDaoInstance());

        classMap.put(BucketService.class, DaoAndServiceFactory.getBucketServiceInstance());
        classMap.put(ItemService.class, DaoAndServiceFactory.getItemServiceInstance());
        classMap.put(OrderService.class, DaoAndServiceFactory.getOrderServiceInstance());
        classMap.put(UserService.class, DaoAndServiceFactory.getUserServiceInstance());
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}