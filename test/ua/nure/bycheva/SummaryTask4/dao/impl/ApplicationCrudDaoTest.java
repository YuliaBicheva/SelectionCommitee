package ua.nure.bycheva.SummaryTask4.dao.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.Status;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Application;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 01.09.16.
 */
public class ApplicationCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    ApplicationDAO applicationDao;

    User user;
    Entrant entrant;
    Faculty faculty;



    @BeforeClass
    public static void init() throws NamingException, SQLException, AppException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);

    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        applicationDao = (ApplicationDAO) daoManager.getDAO(Table.APPLICATION);
        user = ((UserDAO)daoManager.getDAO(Table.USER)).save(new User("registerUserTest","userTest","userTest","userTest","userTest","userTest", Role.ENTRANT.ordinal(),"ru"));
        entrant = ((EntrantDAO) daoManager.getDAO(Table.ENTRANT)).save(new Entrant(user.getId(),"testCity","testRegion","testScool", Status.ACTIVE.ordinal()));
        faculty = ((FacultyDAO)daoManager.getDAO(Table.FACULTY)).save(new Faculty("facultyTest", 10, 20));
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        applicationDao.clear();
        daoManager.getDAO(Table.FACULTY).clear();
        daoManager.getDAO(Table.ENTRANT).clear();
        daoManager.getDAO(Table.USER).clear();
    }

    @Test
    public void saveAppTest() throws Exception {
        Date create_date = new Date();

        Application appToSave = new Application(entrant.getId(), faculty.getId(), 180.7 , create_date);
        appToSave = applicationDao.save(appToSave);
        Application app = applicationDao.findById(appToSave.getId());
        assertEquals(appToSave,app);
    }

    @Test
    public void updateAppTest() throws Exception {
        Date create_date = new Date();

        Application appToSave = new Application(entrant.getId(), faculty.getId(), 180.7 , create_date);
        appToSave = applicationDao.save(appToSave);

        Application appToUpdate = applicationDao.findById(appToSave.getId());

        appToUpdate.setAvgPoint(120);
        applicationDao.update(appToUpdate);

        Application app = applicationDao.findById(appToUpdate.getId());
        assertEquals(appToUpdate,app);
    }

    @Test(expected = DataBaseAccessException.class)
    public void errorSaveAppTest() throws Exception {
        Date create_date = new Date();
        Application appToSave = new Application(entrant.getId()+1, faculty.getId(), 180.7 , create_date);
        applicationDao.save(appToSave);

    }

    @Test(expected = DataBaseAccessException.class)
    public void errorUpdateAppTest() throws Exception {
        Date create_date = new Date();

        Application appToSave = new Application(entrant.getId(), faculty.getId(), 180.7 , create_date);
        appToSave = applicationDao.save(appToSave);

        Application appToUpdate = applicationDao.findById(appToSave.getId());

        appToUpdate.setEntrantId(appToSave.getEntrantId()+1);
        applicationDao.update(appToUpdate);
    }

    @Test
    public void findAllTest() throws Exception {
        Date create_date = new Date();

        int count = 5;
        Faculty[] facultyList = new Faculty[count];
        Application[] appListToSave = new Application[count];

        for(int i = 0; i < facultyList.length; i++){
            facultyList[i] =
                    ((FacultyDAO)daoManager.getDAO(Table.FACULTY)).save(new Faculty("facultyTest" + i, 10, 20));
        }
        for(int k = 0; k < appListToSave.length; k++) {
            appListToSave[k] =
                    applicationDao.save(new Application(entrant.getId(),facultyList[k].getId(), 180.7 , create_date));
        }

        List<Application> apps = applicationDao.findAll();
        assertArrayEquals(appListToSave,apps.toArray());
    }



}