package service;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServlet;

public class Log extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Log.class);

    public static void main(String[] args) {
            logger.debug("Debug message");
            logger.info("Info message");
            logger.warn("Warning message");
            logger.error("Error message");
            logger.fatal("Fatal message");
    }
}
