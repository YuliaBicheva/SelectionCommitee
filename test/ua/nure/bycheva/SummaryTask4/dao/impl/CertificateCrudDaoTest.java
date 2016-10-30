package ua.nure.bycheva.SummaryTask4.dao.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
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

import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.Status;
import ua.nure.bycheva.SummaryTask4.db.dao.CertificateDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;

/**
 * Created by yulia on 01.09.16.
 */
public class CertificateCrudDaoTest {

    @Mock
    static BasicDataSource dataSource;
    @InjectMocks
    static DAOManager daoManager;

    @InjectMocks
    CertificateDAO certificateDAO;

    User user;
    Entrant entrant;


    @BeforeClass
    public static void init() throws NamingException, SQLException {
        daoManager = DAOManager.getInstance();
        MockitoAnnotations.initMocks(daoManager);
    }

    @Before
    public void setUp() throws NamingException, AppException, SQLException {
        user = ((UserDAO)daoManager.getDAO(Table.USER)).save(new User("registerUserTest","userTest","userTest","userTest","userTest","userTest", Role.ENTRANT.ordinal(),"ru"));
        entrant = ((EntrantDAO) daoManager.getDAO(Table.ENTRANT)).save(new Entrant(user.getId(),"testCity","testRegion","testScool", Status.ACTIVE.ordinal()));
        certificateDAO = (CertificateDAO) daoManager.getDAO(Table.CERTIFICATE);
    }

    @After
    public void tearDown() throws NamingException, AppException, SQLException {
        certificateDAO.clear();
        daoManager.getDAO(Table.ENTRANT).clear();
        daoManager.getDAO(Table.USER).clear();
    }

    @Test
    public void saveTest() throws Exception {
        String fileName = "FileToSave.md";
        byte[] buff = fileToByteArray(fileName);

        Certificate certificate = new Certificate();
        certificate.setEntrantId(entrant.getId());
        certificate.setFileName(fileName);
        certificate.setFileContent(buff);

        certificateDAO.save(certificate);
        Certificate certificateTest = certificateDAO.findById(certificate.getId());
        assertEquals(certificate,certificateTest);
    }

    @Test
    public void updateTest() throws Exception {
        String fileName = "FileToSave.md";
        byte[] buff = fileToByteArray(fileName);

        Certificate certificate = new Certificate();
        certificate.setEntrantId(entrant.getId());
        certificate.setFileName(fileName);
        certificate.setFileContent(buff);

        certificateDAO.save(certificate);

        Certificate certificateTest = certificateDAO.findById(certificate.getId());
        certificateTest.setFileName("newFileName");
        certificateTest.setFileContent(fileToByteArray("newFileToUpdate.md"));
        certificateDAO.update(certificateTest);

        Certificate certificateById = certificateDAO.findById(certificate.getId());
        assertEquals(certificateTest,certificateById);
    }

    private byte[] fileToByteArray(String fileName){
        byte[] buff = new byte[400000];
        try {
            File file = new File(fileName);
            file.deleteOnExit();
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buff);

            fileInputStream.close();
        } catch (java.io.IOException e) {
        }

        return buff;
    }

    @Test
    public void findAllTest() throws Exception {
        int count = 5;
        Certificate[] certificateList = new Certificate[count];

        for(int i = 0; i < certificateList.length; i++){
            certificateList[i] =
                    certificateDAO.save(new Certificate(entrant.getId(), "fileName"+i, fileToByteArray("TESTFILE" + i + ".txt")));
        }

        List<Certificate> certificates = certificateDAO.findAll();
        assertArrayEquals(certificateList,certificates.toArray());
    }

}