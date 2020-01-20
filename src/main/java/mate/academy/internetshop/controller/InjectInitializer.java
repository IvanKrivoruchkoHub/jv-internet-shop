package mate.academy.internetshop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    private static final Logger LOGGER = Logger.getLogger(InjectInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            LOGGER.info("Start injectionDependency");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            LOGGER.error("Can't initialized all dependencies ", e);
            throw new RuntimeException(e);
        }

        User admin = new User();
        admin.setName("Super");
        admin.setSurname("Mario");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("Admin1");
        admin.setPassword("1");
        userService.create(admin);

        User user = new User();
        user.setName("John");
        user.setSurname("Jones");
        user.addRole(Role.of("USER"));
        user.setLogin("John1");
        user.setPassword("1");
        userService.create(user);

        Bucket bucket = new Bucket();
        bucket.setUserId(user.getId());
        bucketService.create(bucket);

        Item item = new Item();
        item.setName("Keyboard");
        item.setPrice(500.00);
        itemService.create(item);

        Item item2 = new Item();
        item2.setName("Monitor");
        item2.setPrice(5000.00);
        itemService.create(item2);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
