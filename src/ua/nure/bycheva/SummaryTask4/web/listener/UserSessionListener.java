package ua.nure.bycheva.SummaryTask4.web.listener;

import java.io.File;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;

/**
 * User session listener. Obtain uploaded files from database to tmp path and remove its.
 */
public class UserSessionListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(UserSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        String pathToWeb = httpSessionEvent.getSession().getServletContext().getRealPath(Path.TMP_DIRECTORY_NAME);
        String[] fileNames = new File(pathToWeb).list();
        for(String name: fileNames){
            if(name.startsWith(httpSessionEvent.getSession().getId())) {
                new File(pathToWeb + "/" + name).delete();
            }
        }
    }
}
