package ua.nure.bycheva.SummaryTask4.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.Status;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import javax.naming.NamingException;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by yulia on 01.09.16.
 */
public class UserDAOImplTest {

    @Mock
    static BasicDataSource dataSource;

    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    UserDAO userDAO;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        userDAO = (UserDAO) daoManager.getDAO(Table.USER);
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        userDAO.clear();
    }

    @Test
    public void registerTest() throws Exception {
        User userToSave = new User("registerUserTest","userTest","userTest","userTest","userTest","userTest", Role.ENTRANT.ordinal(),"ru");
        Entrant entrant = new Entrant(null,"testCity","testRegion","testScool", Status.ACTIVE.ordinal());
        Certificate certificate = new Certificate();
        certificate.setFileName("testFile");
        certificate.setFileContent(new byte[1024]);

        boolean result = userDAO.register(userToSave, entrant, certificate);
        assertTrue(result);
    }

    @Test(expected = DataBaseAccessException.class)
    public void errorRegistrationEntrantFailTest() throws DataBaseAccessException, SQLException {
        User userToSave = new User("registerUserTest","userTest","userTest","userTest","userTest","userTest", Role.ENTRANT.ordinal(),"ru");
        Entrant entrant = new Entrant(null,"testCity","testRegion","testScool", Status.values().length + 1);

        userDAO.register(userToSave, entrant, null);
    }
}