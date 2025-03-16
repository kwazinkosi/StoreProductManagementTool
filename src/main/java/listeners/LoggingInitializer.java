package listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import utils.LoggingManager;

@WebListener
public class LoggingInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        LoggingManager.configureLogging();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        LoggingManager.info("Application is shutting down.");
    }
}
