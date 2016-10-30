package ua.nure.bycheva.SummaryTask4.controller;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.Role;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.util.PasswordUtil;
import ua.nure.bycheva.SummaryTask4.web.Controller;

/**
 * Created by yulia on 01.09.16.
 */

public class ControllerLoginTest extends HttpServlet {


    private static HttpServletRequest request;

    private static HttpServletResponse response;

    private static HttpSession session;

    private static Controller servlet;

    @Mock
    static BasicDataSource dataSource;

    @InjectMocks
    static DAOManager daoManager;

    User admin;
    User entrant;

    @BeforeClass
    public static void initDAO() throws AppException, SQLException, NamingException {
        daoManager = DAOManager.getInstance();
    }

    @Before
    public final void setUp() throws AppException, IOException, NamingException, SQLException, NoSuchAlgorithmException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getParameter("command")).thenReturn("login");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/summarytask4");

        doNothing().when(response).sendRedirect("/summarytask4" + Path.MAIN_PAGE.concat("&redirect"));

        final ServletContext servletContext = mock(ServletContext.class);
        servlet = new Controller() {
            public ServletContext getServletContext() {
                return servletContext; // return the mock
            }
        };

        initMocks();
    }

    public void initMocks() throws NamingException, AppException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserDAO userDAO = (UserDAO) daoManager.getDAO(Table.USER);

        User user = new User("admin", PasswordUtil.hash("admin"),"admin","admin","admin","admin", Role.ADMIN.ordinal(),"en");
        this.admin = userDAO.save(user);

        user = new User("entrant",PasswordUtil.hash("entrant"),"entrant","entrant","entrant","entrant", Role.ENTRANT.ordinal(),"ru");
        this.entrant = userDAO.save(user);
    }

    @After
    public void deleteUser() throws NamingException, AppException, SQLException {
        (daoManager.getDAO(Table.USER)).clear();
    }

    /**
     * Verifies that the doGet method throws an exception when passed null arguments
     * @throws ServletException
     * @throws IOException
     */
    @Test(expected = NullPointerException.class)
    public final void testDoGetNegative() throws ServletException, IOException {
        servlet.doGet(null, null);
    }

    /**
     * Verifies that the doGet method runs without exception
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public final void testLoginEntrantCommand() throws ServletException, IOException {
        boolean throwsException = false;
        try {
            when(request.getParameter("login")).thenReturn("entrant");
            when(request.getParameter("password")).thenReturn("entrant");
            servlet.doGet(request, response);
            verify(session, atLeast(1)).setAttribute("user",entrant);
            verify(session, atLeast(1)).setAttribute("userRole", Role.ENTRANT);
            verify(session, atLeast(1)).setAttribute("userLocale", entrant.getLocale());
            verify(response).sendRedirect("/summarytask4".concat(Path.MAIN_PAGE).concat("&redirect"));
        } catch (Exception e) {
            throwsException = true;
        }
        assertFalse("doGet throws an exception", throwsException);
    }

    /**
     * Verifies that the doPost method throws an exception when passed null arguments
     * @throws ServletException
     * @throws IOException
     */
    @Test(expected = NullPointerException.class)
    public final void testDoPostNegative() throws ServletException, IOException {
        servlet.doPost(null, null);
    }

    /**
     * Verifies that the doGet method runs without exception
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public final void testLoginAdminCommand() throws ServletException, IOException {
        boolean throwsException = false;
        try {
            when(request.getParameter("login")).thenReturn("admin");
            when(request.getParameter("password")).thenReturn("admin");
            servlet.doPost(request, response);
            verify(session, atLeast(1)).setAttribute("user",admin);
            verify(session, atLeast(1)).setAttribute("userRole", Role.ADMIN);
            verify(session, atLeast(1)).setAttribute("userLocale", admin.getLocale());
            verify(response).sendRedirect("/summarytask4".concat(Path.MAIN_PAGE).concat("&redirect"));
        } catch (Exception e) {
            throwsException = true;
        }
        assertFalse("doGet throws an exception", throwsException);
    }
}