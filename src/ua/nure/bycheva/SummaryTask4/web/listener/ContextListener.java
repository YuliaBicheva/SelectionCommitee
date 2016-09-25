package ua.nure.bycheva.SummaryTask4.web.listener;

import java.io.File;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ua.nure.bycheva.SummaryTask4.Path;

/**
 * Context listener.
 * 
 * @author yulia
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
        deregisterDriver();
        deleteTmpFileDirectory(event.getServletContext());
		log("Servlet context destruction finished");

	}

    private void deregisterDriver() {

        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOG.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                LOG.warn(String.format("Error deregistering driver %s", driver), e);
            }

        }
    }

    public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
        initValidatorContainer();
		initComparatorManager();
        createTmpFileDirectory(servletContext);
	
		log("Servlet context initialization finished");
	}

    /**
     * Delete directory for tmp files.
     *
     */
    private void deleteTmpFileDirectory(ServletContext servletContext) {
        File tmpPath = new File(servletContext.getRealPath(File.separator) + Path.TMP_DIRECTORY_NAME);
        if(tmpPath.exists()){
            tmpPath.delete();
            LOG.debug("Directory for temporary files was deleted");
        }
    }

    /**
     * Create directory for tmp files.
     *
     */
    private void createTmpFileDirectory(ServletContext servletContext) {
        File tmpPath = new File(servletContext.getRealPath(File.separator) + Path.TMP_DIRECTORY_NAME);
        if(!tmpPath.exists()){
            tmpPath.mkdir();
            LOG.debug("Directory for temporary files was created");
        }
    }

    /**
     * Initializes ValidatorContainer.
     *
     */
    private void initValidatorContainer() {

        // initialize validator container
        // just load class to JVM
        try {
            Class.forName("ua.nure.bycheva.SummaryTask4.web.validation.ValidatorContainer");
			LOG.debug("ValidatorContainer has been initialized");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot initialize Validator Container");
        }
    }

    /**
	 * Initializes log4j framework.
	 *
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(
				servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}		
		log("Log4J initialization finished");
	}
	
	/**
	 * Initializes CommandContainer.
	 *
	 */
	private void initCommandContainer() {
		
		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.bycheva.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}
	
	/**
	 * Initializes ComparatorManager.
	 *
	 */
	private void initComparatorManager() {
		
            // initialize comparator manager
            // just load class to JVM
            try {
                Class.forName("ua.nure.bycheva.SummaryTask4.web.sorter.ComparatorManager");

            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Cannot initialize Comparator Manager");
            }
	}

    /**
     * Initializes i18n subsystem.
     *
     *  @param servletContext - gets init parameters from descriptor.
     */
    private void initI18N(ServletContext servletContext) {
        LOG.debug("I18N subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            LOG.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            LOG.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        LOG.debug("I18N subsystem initialization finished");
    }

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}