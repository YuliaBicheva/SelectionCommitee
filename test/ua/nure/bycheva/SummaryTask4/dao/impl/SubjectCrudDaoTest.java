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
import ua.nure.bycheva.SummaryTask4.db.dao.SubjectDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 01.09.16.
 */
public class SubjectCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    SubjectDAO subjectDAO;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        subjectDAO = (SubjectDAO) daoManager.getDAO(Table.SUBJECT);
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        subjectDAO.clear();
    }


    @Test(expected = DataBaseAccessException.class)
    public void errorSaveTest() throws AppException {
        Subject subject = new Subject("subjectTest");
        subjectDAO.save(subject);
        subjectDAO.save(subject);
    }

    @Test
    public void saveTest() throws Exception {
        Subject subjectTest = new Subject("subjectTest");
        subjectTest = subjectDAO.save(subjectTest);
        Subject subject = subjectDAO.findById(subjectTest.getId());
        assertEquals(subjectTest,subject);
    }

    @Test
    public void updateTest() throws Exception {
        Subject subjectTest = new Subject("subjectTest");
        subjectTest = subjectDAO.save(subjectTest);
        Subject subject = subjectDAO.findById(subjectTest.getId());

        subject.setName("newSubjectTest");
        subjectDAO.update(subject);

        Subject subjectyId = subjectDAO.findById(subject.getId());
        assertEquals(subject,subjectyId);
    }

    @Test(expected = DataBaseAccessException.class)
    public void errorUpdateTest() throws DataBaseAccessException {
        Subject subjectTest = new Subject("subjectTest");
        subjectTest = subjectDAO.save(subjectTest);
        Subject subjectTest2 = new Subject("subjectTest2");
        subjectTest2 = subjectDAO.save(subjectTest2);

        Subject subject = subjectDAO.findById(subjectTest.getId());
        subject.setName(subjectTest2.getName());
        subjectDAO.update(subject);
    }

    @Test
    public void findAllTest() throws Exception {
        int count = 5;
        Subject[] subjectList = new Subject[count];

        for(int i = 0; i < subjectList.length; i++){
            subjectList[i] =
                    subjectDAO.save(new Subject("subjectTest"+i));
        }

        List<Subject> subjects = subjectDAO.findAll();
        assertArrayEquals(subjectList,subjects.toArray());
    }

}