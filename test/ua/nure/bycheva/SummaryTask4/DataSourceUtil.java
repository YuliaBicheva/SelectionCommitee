package ua.nure.bycheva.SummaryTask4;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Created by yulia on 01.09.16.
 */
public class DataSourceUtil {

    private static final String JNDI = "java:/comp/env/jdbc/SUMTASK4";

    public static void setUpNamingEnv() throws NamingException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:derby:/Users/yulia/BychevaProject/SummaryTask4;create=true");

        final Context context = new InitialContext();
        try {
            context.createSubcontext("java:");
            context.createSubcontext("java:/comp");
            context.createSubcontext("java:/comp/env");
            context.createSubcontext("java:/comp/env/jdbc");
            context.bind(JNDI, basicDataSource);
        } finally {
            context.close();
        }
    }
}
