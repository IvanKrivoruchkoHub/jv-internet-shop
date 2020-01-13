package mate.academy.internetshop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.internetshop.lib.Injector;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {

    static final Logger logger = Logger.getLogger(InjectInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            logger.info("Start injectionDependency");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
