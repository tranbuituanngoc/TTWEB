package Util;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String uploadFolder = sce.getServletContext().getRealPath("/") + "UploadFileStore";
        String linkFolder = sce.getServletContext().getRealPath("/") + "ROOT/UploadFileStore";
        File link = new File(linkFolder);
        if (!link.exists()) {
            try {
                Runtime.getRuntime().exec("ln -s " + uploadFolder + " " + linkFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // do nothing
    }
}
