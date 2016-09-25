package ua.nure.bycheva.SummaryTask4.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.nure.bycheva.SummaryTask4.db.bean.FacultySubjectBean;
import ua.nure.bycheva.SummaryTask4.db.dao.*;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.FacultySubject;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by yulia on 01.09.16.
 */
public class FacultySubjectCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    FacultySubjectDAO facultySubjectDAO;

    Faculty faculty;

    Subject subject;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        facultySubjectDAO = (FacultySubjectDAO) daoManager.getDAO(Table.FACULTY_SUBJECTS);
        faculty = ((FacultyDAO)daoManager.getDAO(Table.FACULTY)).save(new Faculty("facultyTest", 10, 20));

        subject = ((SubjectDAO)daoManager.getDAO(Table.SUBJECT)).save(new Subject("subjectTest"));
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        facultySubjectDAO.clear();
        daoManager.getDAO(Table.FACULTY).clear();
        daoManager.getDAO(Table.SUBJECT).clear();
    }


    @Ignore
    @Test(expected = DataBaseAccessException.class)
    public void errorSaveTest() throws AppException {
        FacultySubject facultySubject = new FacultySubject(faculty.getId(), subject.getId()+1,.25);
        facultySubjectDAO.save(facultySubject);
    }

    @Test
    public void saveTest() throws Exception {
        FacultySubject facultySubject = new FacultySubject(faculty.getId(), subject.getId(),.25);
        facultySubjectDAO.save(facultySubject);

        List<FacultySubjectBean> facultySubjectTest = facultySubjectDAO.findAllFacultySubjectsBeans(faculty.getId());
        assertEquals(facultySubjectTest.size() , 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void findAllTest() throws Exception {
        facultySubjectDAO.findAll();
    }

}