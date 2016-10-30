package ua.nure.bycheva.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Created by yulia on 12.08.16.
 */
public abstract class AbstractDAO<T> {


    private static final Logger LOG = Logger.getLogger(AbstractDAO.class);

    private final String tableName;

    public AbstractDAO(String tableName) {
        this.tableName = tableName;
    }

    protected AbstractDAO (DataSource src, String tableName) {
//        this.src = src;
        this.tableName = tableName;
    }

    /**
     * Get a connection from the connection pool. The returned connection
     * must be closed by calling closeConnection()
     * @return : a new connection from the pool if succeed
     * @throws DataBaseAccessException : if cannot get a connection from the pool
     */
    public Connection getConnection() throws DataBaseAccessException {
        Connection con = null;
        try{
            con = DAOManager.getInstance().getConnection();
        } catch (Exception e) {
            LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CONNECTION),e);
            throw new DataBaseAccessException(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_CONNECTION),e);
        }
        return con;
    }

    /**
     * Use this method to return the connection to the connection pool
     * @param connection : the connection that needs to be returned to the pool
     */
    public void closeConnection(Connection connection) {
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_CLOSE_CONNECTION),e);
        }
    }

    /**
     * Use this method to close the Statement
     * @param statement : the statement that needs to be closed
     */
    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_CLOSE_STATEMENT),e);
        }
    }

    /**
     * Use this method to close the ResultSet
     * @param rs : the resultset that needs to be closed
     */
    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_CLOSE_STATEMENT),e);
        }
    }

    public void rollback(Connection con){
        if(con != null){
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_ROLLBACK),e);
            }
        }
    }

    public void close(ResultSet rs, Statement st, Connection con) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(con);
    }

    public void close(ResultSet rs, Connection con, Statement... statements) {
        closeResultSet(rs);
        closeStatement(statements);
        closeConnection(con);
    }

    public void close(Statement st, Connection con) {
        closeStatement(st);
        closeConnection(con);
    }

    /**
     * Use this method to close the Statement
     * @param statements : array of statements that needs to be closed
     */
    protected void closeStatement(Statement... statements) {
        for(Statement statement: statements){
            closeStatement(statement);
        }
    }

    public boolean delete(Long id) throws AppException {
        Connection connection = null;
        String sql = "DELETE FROM " + this.tableName + " WHERE id=?";
        PreparedStatement st = null;
        try{
            connection = getConnection();
            st = connection.prepareStatement(sql);
            st.setLong(1, id);
            return st.executeUpdate() > 0;
        }catch (SQLException e){
            String errorMsg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_DELETE_ENTITY_BY_ID + tableName + ", id=" + id);
            LOG.error(errorMsg, e);
            throw new DataBaseAccessException(errorMsg, e);
        }finally {
            close(st,connection);
        }
    }

    public void clear() throws AppException {
        Connection connection = null;
        String sql = "DELETE FROM " + this.tableName;
        PreparedStatement st = null;
        try{
            connection = getConnection();
            st = connection.prepareStatement(sql);
            st.executeUpdate();
        }catch (SQLException e){
            String errorMsg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_CLEAR_TABLE + tableName);
            LOG.error(errorMsg, e);
            throw new DataBaseAccessException(errorMsg, e);
        }finally {
            close(st,connection);
        }
    }

}
