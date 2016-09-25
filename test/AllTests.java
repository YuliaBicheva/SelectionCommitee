import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import ua.nure.bycheva.SummaryTask4.DataSourceUtil;
import ua.nure.bycheva.SummaryTask4.controller.ControllerLoginTest;
import ua.nure.bycheva.SummaryTask4.dao.impl.*;

import javax.naming.NamingException;

@RunWith(Suite.class)
@SuiteClasses(
        {
                CertificateCrudDaoTest.class,
                UserCrudDaoTest.class, UserDAOImplTest.class,
                FacultyCrudDaoTest.class,
                SubjectCrudDaoTest.class,
                ApplicationCrudDaoTest.class,
                ControllerLoginTest.class,
                FacultySubjectCrudDaoTest.class
        })
public class AllTests {

    @BeforeClass
    public static void logConfig() throws NamingException {
        org.apache.log4j.BasicConfigurator.configure();
        DataSourceUtil.setUpNamingEnv();

    }
}
