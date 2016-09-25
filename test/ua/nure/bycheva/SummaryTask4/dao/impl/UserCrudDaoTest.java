package ua.nure.bycheva.SummaryTask4.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yulia on 01.09.16.
 */
public class UserCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    UserDAO userDAO;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        userDAO = (UserDAO) daoManager.getDAO(Table.USER);
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        userDAO.clear();
    }


    @Ignore
    @Test(expected = DataBaseAccessException.class)
    public void errorSaveTest() throws AppException {
        User userToSave = new User("userTest", "userpasswordLengthIsMoreThan10", "userTest", "userTest", "userTest", "userTest", Role.ADMIN.ordinal(), "ru");
        userDAO.save(userToSave);
    }

    @Test
    public void saveTest() throws Exception {
        User userToSave = new User("userTest", "userTest", "userTest", "userTest", "userTest", "userTest", Role.ADMIN.ordinal(), "ru");
        try {
            userToSave = userDAO.save(userToSave);
            User user = userDAO.findById(userToSave.getId());
            assertEquals(user,userToSave);
        }catch (Throwable e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void updateTest() throws Exception {
        User userToSave = new User("userTest","userTest","userTest","userTest","userTest","userTest", Role.ADMIN.ordinal(),"ru");
        try {
            userToSave = userDAO.save(userToSave);
            User userToUpdate = userDAO.findById(userToSave.getId());

            userToUpdate.setLogin("newLogin");
            userToUpdate.setPassword("pass");
            userDAO.update(userToUpdate);

            User user = userDAO.findByLogin("newLogin");
            assertEquals(userToUpdate,user);
        }catch (Throwable e){
            fail();
        }
    }

    @Ignore
    @Test(expected = DataBaseAccessException.class)
    public void errorUpdateTest() throws Exception {
        User userToSave = new User("userTest","userTest","userTest","userTest","userTest","userTest", Role.ADMIN.ordinal(),"ru");
        userToSave = userDAO.save(userToSave);
        User userToUpdate = userDAO.findById(userToSave.getId());

        userToUpdate.setPassword("passwordLengthIsMoreThan10");
        userDAO.update(userToUpdate);
    }

    @Test
    public void findAllTest() throws Exception {
        int count = 5;
        User[] userListToSave = new User[count];
        List<User> users;
        for(int i = 0; i < userListToSave.length; i++){
            userListToSave[i] =
                    new User("userTest" + i,"userTest","userTest","userTest","userTest","userTest", Role.ADMIN.ordinal(),"ru");
        }
        try {
            for(int k = 0; k < userListToSave.length; k++) {
                User userToSave = userListToSave[k];
                userToSave = userDAO.save(userToSave);
                userListToSave[k] = userToSave;
            }
            users = userDAO.findAll();
            assertArrayEquals(userListToSave,users.toArray());
        }catch (Throwable e){
            fail();
        }
    }

}