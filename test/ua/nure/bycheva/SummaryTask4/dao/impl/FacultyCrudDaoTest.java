package ua.nure.bycheva.SummaryTask4.dao.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
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

import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 01.09.16.
 */
public class FacultyCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    FacultyDAO facultyDAO;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        facultyDAO = (FacultyDAO) daoManager.getDAO(Table.FACULTY);
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        facultyDAO.clear();
    }


    @Test(expected = DataBaseAccessException.class)
    public void errorSaveTest() throws AppException {
        Faculty faculty = new Faculty("facultyTest", 10, 20);
        facultyDAO.save(faculty);
        facultyDAO.save(faculty);
    }

    @Test
    public void saveTest() throws Exception {
        Faculty facultyTest = new Faculty("facultyTest", 10, 20);
        facultyTest = facultyDAO.save(facultyTest);
        Faculty faculty = facultyDAO.findById(facultyTest.getId());
        assertEquals(facultyTest,faculty);
    }

    @Test
    public void updateTest() throws Exception {
        Faculty facultyTest = new Faculty("facultyTest", 10, 20);
        facultyTest = facultyDAO.save(facultyTest);
        Faculty faculty = facultyDAO.findById(facultyTest.getId());

        faculty.setName("facultyNewName");
        faculty.setBudgetPlaces(5);
        faculty.setTotalPlaces(20);

        facultyDAO.update(faculty);

        Faculty facultyDAOById = facultyDAO.findById(faculty.getId());
        assertEquals(faculty,facultyDAOById);
    }

    @Test(expected = DataBaseAccessException.class)
    public void errorUpdateTest() throws DataBaseAccessException {
        Faculty facultyTest = new Faculty("facultyTest", 10, 20);
        facultyDAO.save(facultyTest);
        Faculty facultyTest2 = new Faculty("facultyTest2", 10, 20);
        facultyDAO.save(facultyTest2);

        Faculty facultyById = facultyDAO.findById(facultyTest.getId());
        facultyById.setName(facultyTest2.getName());
        facultyDAO.update(facultyById);
    }

    @Test
    public void findAllTest() throws Exception {
        int count = 5;
        Faculty[] facultyList = new Faculty[count];

        for(int i = 0; i < facultyList.length; i++){
            facultyList[i] =
                    facultyDAO.save(new Faculty("facultyTest" + i, 10, 20));
        }

        List<Faculty> faculties = facultyDAO.findAll();
        assertArrayEquals(facultyList,faculties.toArray());
    }

}