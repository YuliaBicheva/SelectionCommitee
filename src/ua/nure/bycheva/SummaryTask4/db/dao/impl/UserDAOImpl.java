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
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.UserDAO;
import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 *
 * Created by yulia on 12.08.16.
 */
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    private static final String TABLENAME = "users";

    public UserDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public UserDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public User findByLogin(String login) throws DataBaseAccessException {
        User user = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_USER_BY_LOGIN);
            st.setString(1, login);
            rs = st.executeQuery();
            if(rs.next()){
                user = extractUser(rs);
            }
        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_USER_BY_LOGIN);
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return user;
    }

    @Override
    public User save(User user) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_USER,PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,user);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    user.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + user;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return user;
    }

    @Override
    public boolean update(User user) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_USER);

            prepareToUpdate(st, user);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY )+ user;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        } finally {
            close(st,con);
        }
    }

    @Override
    public User findById(Long id) throws DataBaseAccessException {
        User user = null;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_USER_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                user = extractUser(rs);
            }
        }catch (SQLException e){
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DataBaseAccessException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_USERS);
            while(rs.next()){
                users.add(extractUser(rs));
            }
        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
           close(rs,st,con);
        }

        return users;
    }

    @Override
    public boolean register(User user, Entrant entrant, Certificate certificate) throws DataBaseAccessException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            user = this.save(user);

            entrant.setUserId(user.getId());
            entrant = new EntrantDAOImpl().save(entrant);

            if(certificate != null) {
                certificate.setEntrantId(entrant.getId());
                new CertificateDAOImpl().save(certificate);
            }

            con.commit();


            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            throw new DataBaseAccessException(MessageManager.getInstance().getProperty(MessageManager.ERR_REGISTRATION),e);
        }finally {
            closeConnection(con);
        }
    }

    @Override
    public List<User> findAllByEntrantId(Long[] entrantsId) throws DataBaseAccessException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_ALL_FIND_USERS_BY_ENTRANTS_ID);

            for(Long id: entrantsId){
                st.setLong(1,id);
                st.addBatch();
            }

            rs = st.executeQuery();
            while(rs.next()){
                users.add(extractUser(rs));
            }
        }catch (SQLException e) {
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CURRENT_ENTRANTS_LIST);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return users;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setMiddleName(rs.getString(Fields.USER_MIDDLE_NAME));
        user.setEmail(rs.getString(Fields.USER_EMAIL));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        user.setLocale(rs.getString(Fields.USER_LOCALE));
        return user;
    }

    private void prepareToSave(PreparedStatement st, User user) throws SQLException {
        int k = 0;
        st.setString(++k, user.getLogin());
        st.setString(++k, user.getPassword());
        st.setString(++k, user.getFirstName());
        st.setString(++k, user.getLastName());
        st.setString(++k, user.getMiddleName());
        st.setString(++k, user.getEmail());
        st.setInt(++k,user.getRoleId());
        st.setString(++k, user.getLocale());
    }

    private void prepareToUpdate(PreparedStatement st, User user) throws SQLException {
        int k = 0;
        st.setString(++k, user.getLogin());
        st.setString(++k, user.getPassword());
        st.setString(++k, user.getFirstName());
        st.setString(++k, user.getLastName());
        st.setString(++k, user.getMiddleName());
        st.setString(++k, user.getEmail());
        st.setInt(++k,user.getRoleId());
        st.setString(++k, user.getLocale());
        st.setLong(++k, user.getId());
    }
}
