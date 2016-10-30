package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.ApplicationDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Application;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 15.08.16.
 */
public class ApplicationDAOImpl extends AbstractDAO<Application> implements ApplicationDAO {

    private static final Logger LOG = Logger.getLogger(ApplicationDAOImpl.class);

    private static final String TABLENAME = "applications";

    public ApplicationDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public ApplicationDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Application save(Application application) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_APPLICATION,PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,application);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    application.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY)+ application;

            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return application;
    }

    @Override
    public boolean update(Application application) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_APPLICATION);

            prepareToUpdate(st, application);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY ) + application;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        } finally {
            close(st,con);
        }
    }

    private void prepareToUpdate(PreparedStatement st, Application application) throws SQLException {
        int k = 0;
        st.setLong(++k, application.getEntrantId());
        st.setLong(++k, application.getFacultyId());
        st.setDouble(++k, application.getAvgPoint());
        st.setLong(++k, application.getId());
    }

    @Override
    public Application findById(Long id) throws DataBaseAccessException {
        Application application = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_APPLICATION_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                application = extractApplication(rs);
            }
        }catch (SQLException e){
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return application;
    }

    @Override
    public List<Application> findAll() throws DataBaseAccessException {
        List<Application> applications = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_APPLICATIONS);
            while(rs.next()){
                applications.add(extractApplication(rs));
            }
        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return applications;
    }

    private Application extractApplication(ResultSet rs) throws SQLException {
        Application application = new Application();
        application.setId(rs.getLong(Fields.ENTITY_ID));
        application.setEntrantId(rs.getLong(Fields.ENTRANT_ID));
        application.setFacultyId(rs.getLong(Fields.FACULTY_ID));
        application.setCreateDate(new java.util.Date(rs.getDate(Fields.APPLICATION_CREATE_DATE).getTime()));
        application.setAvgPoint(rs.getDouble(Fields.APPLICATION_AVG_POINT));
        return  application;
    }

    private void prepareToSave(PreparedStatement st, Application application) throws SQLException {
        int k = 0;
        st.setLong(++k, application.getEntrantId());
        st.setLong(++k, application.getFacultyId());
        st.setDate(++k, new Date(application.getCreateDate().getTime()));
        st.setDouble(++k, application.getAvgPoint());
    }

    @Override
    public List<EntrantApplicationBean> findAllByUserId(Long userId) throws DataBaseAccessException {
        List<EntrantApplicationBean> entranAppBeans = new ArrayList<>();

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_APPLICATION_BEANS);

            st.setLong(1,userId);

            rs = st.executeQuery();

            while(rs.next()){
                entranAppBeans.add(extractEntrantApplication(rs));
            }
            con.commit();
            con.setAutoCommit(true);

        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTRANT_APPLICATIONS);
            LOG.error(msg,e);
            e.printStackTrace();
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entranAppBeans;
    }


    @Override
    public List<EntrantApplicationBean> findAllByEntrantId(Long entrantId) throws DataBaseAccessException {
        List<EntrantApplicationBean> entranAppBeans = new ArrayList<>();

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_APPLICATION_BEANS_BY_ENTRANT_ID);

            st.setLong(1,entrantId);

            rs = st.executeQuery();

            while(rs.next()){
                entranAppBeans.add(extractEntrantApplication(rs));
            }
            con.commit();
            con.setAutoCommit(true);

        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTRANT_APPLICATIONS);
            LOG.error(msg,e);
            e.printStackTrace();
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }


        return entranAppBeans;
    }

    private EntrantApplicationBean extractEntrantApplication(ResultSet rs) throws SQLException {
        EntrantApplicationBean entrantApplicationBean = new EntrantApplicationBean();
        entrantApplicationBean.setId(rs.getLong(Fields.ENTITY_ID));
        entrantApplicationBean.setEntrantId(rs.getLong(Fields.ENTRANT_ID));
        entrantApplicationBean.setFacultyId(rs.getLong(Fields.FACULTY_ID));
        entrantApplicationBean.setFacultyName(rs.getString(Fields.FACULTY_NAME));
        entrantApplicationBean.setCreateDate(new java.util.Date(rs.getDate(Fields.APPLICATION_CREATE_DATE).getTime()));


        return entrantApplicationBean;
    }

    @Override
    public void calculateAvgPoints() throws DataBaseAccessException{
        Statement st = null;
        Connection con = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            con.setAutoCommit(false);
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_APPLICATIONS_IDS);
            con = getConnection();
            prst = con.prepareStatement(SQL.SQL_UPDATE_APPLICATION_POINT_BY_FACULTY_AND_ENTRANT);

            while(rs.next()){
                executeCalculationPoint(rs,prst);
            }

            if(prst != null){
                prst.executeBatch();
            }

            con.commit();
            con.setAutoCommit(true);
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_CALCULATE_AVG_POINTS);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs, con, st, prst);
        }
    }

    private void executeCalculationPoint(ResultSet rs,PreparedStatement prst) throws SQLException, DataBaseAccessException {
        Long facultyId = rs.getLong(Fields.FACULTY_ID);
        Long entrantId = rs.getLong(Fields.ENTRANT_ID);
        double point = calcCompetitivePoint(entrantId,facultyId);
        prst.setDouble(1,point);
        prst.setLong(2,facultyId);
        prst.setLong(3,entrantId);
        prst.addBatch();
    }

    private double calcCompetitivePoint(Long entrantId, Long facultyId) throws SQLException, DataBaseAccessException {
        double competitivePoint = 0;
        double sumRatio;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_CALCULATE_ENTRANT_AVG_CERTIFICATE_VALUE);

            st.setLong(1,entrantId);

            rs = st.executeQuery();

            if(rs.next()){
                competitivePoint = rs.getDouble(1);
            }

            st = con.prepareStatement(SQL.SQL_CALCULATE_ENTRANT_AVG_TEST_VALUE);
            st.setLong(1,entrantId);
            st.setLong(2,facultyId);

            rs = st.executeQuery();

            if(rs.next()){
                sumRatio = rs.getDouble(2);
                competitivePoint *= (1-sumRatio);
                competitivePoint += rs.getDouble(1);
            }

        }finally {
            close(rs,st,con);
        }
        return competitivePoint;
    }
}
