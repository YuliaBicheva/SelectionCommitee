package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import java.sql.Connection;
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
import ua.nure.bycheva.SummaryTask4.db.bean.UserEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.EntrantDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 15.08.16.
 */
public class EntrantDAOImpl extends AbstractDAO<Entrant> implements EntrantDAO {

    private static final Logger LOG = Logger.getLogger(EntrantDAOImpl.class);

    private static final String TABLENAME = "entrants";

    public EntrantDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public EntrantDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Entrant save(Entrant entrant) throws DataBaseAccessException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_ENTRANT,PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,entrant);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    entrant.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY)+ entrant;
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return entrant;
    }

    @Override
    public boolean update(Entrant entrant) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_ENTRANT);

            prepareToUpdate(st, entrant);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + entrant;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        } finally {
            closeStatement(st);
        }
    }

    private void prepareToUpdate(PreparedStatement st, Entrant entrant) throws SQLException {
        int k = 0;
        st.setLong(++k,entrant.getUserId());
        st.setString(++k,entrant.getCity());
        st.setString(++k,entrant.getRegion());
        st.setString(++k,entrant.getSchool());
        st.setLong(++k,entrant.getStatusId());
        st.setLong(++k,entrant.getId());
    }

    @Override
    public Entrant findById(Long id) throws DataBaseAccessException {
        Entrant entrant = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                entrant = extractEntrant(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return entrant;
    }

    @Override
    public List<Entrant> findAll() throws DataBaseAccessException {
        throw new UnsupportedOperationException("EntrantDAO FindAll() method");
    }

    @Override
    public Entrant findByUserId(Long userId) throws DataBaseAccessException {
        Entrant entrant = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_BY_USER_ID);
            st.setLong(1, userId);
            rs = st.executeQuery();
            if(rs.next()){
                entrant = extractEntrant(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTRANT_BY_USER_ID);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return entrant;
    }

    @Override
    public List<UserEntrantBean> findAllUserEntrantBeans() throws DataBaseAccessException {
        List<UserEntrantBean> entrantBeans = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_ALL_ENTRANT_BEANS);
            while(rs.next()){
                entrantBeans.add(extractEntrantBean(rs));
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTRANTS_INFO);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return entrantBeans;
    }

    @Override
    public UserEntrantBean findUserEntrantBean(Long eId) throws DataBaseAccessException {
        UserEntrantBean entrantBeans = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_ENTRANT_BEANS);
            st.setLong(1, eId);
            rs = st.executeQuery();
            if(rs.next()){
                entrantBeans = extractEntrantBean(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTRANTS_INFO);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return entrantBeans;
    }

    private UserEntrantBean extractEntrantBean(ResultSet rs) throws SQLException {
        UserEntrantBean entrantBean = new UserEntrantBean();
        entrantBean.setEntrantId(rs.getLong(1));
        entrantBean.setEmail(rs.getString(Fields.USER_EMAIL));
        entrantBean.setFullName(String.format("%s %s %s",
                rs.getString(Fields.USER_LAST_NAME), rs.getString(Fields.USER_FIRST_NAME), rs.getString(Fields.USER_MIDDLE_NAME)));
        entrantBean.setCity(rs.getString(Fields.ENTRANT_CITY));
        entrantBean.setRegion(rs.getString(Fields.ENTRANT_REGION));
        entrantBean.setSchool(rs.getString(Fields.ENTRANT_SCHOOL));
        entrantBean.setStatusName(rs.getString(Fields.STATUS_NAME));
        return entrantBean;
    }

    private void prepareToSave(PreparedStatement st, Entrant entrant) throws SQLException {
        int k = 0;
        st.setLong(++k,entrant.getUserId());
        st.setString(++k,entrant.getCity());
        st.setString(++k,entrant.getRegion());
        st.setString(++k,entrant.getSchool());
        st.setInt(++k,entrant.getStatusId());
    }

    private Entrant extractEntrant(ResultSet rs) throws SQLException {
        Entrant entrant = new Entrant();
        entrant.setId(rs.getLong(Fields.ENTITY_ID));
        entrant.setUserId(rs.getLong(Fields.USER_ID));
        entrant.setCity(rs.getString(Fields.ENTRANT_CITY));
        entrant.setRegion(rs.getString(Fields.ENTRANT_REGION));
        entrant.setSchool(rs.getString(Fields.ENTRANT_SCHOOL));
        entrant.setStatusId(rs.getInt(Fields.ENTRANT_STATUS_ID));
        return entrant;
    }
}
