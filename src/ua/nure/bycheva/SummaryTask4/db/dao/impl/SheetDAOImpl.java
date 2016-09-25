package ua.nure.bycheva.SummaryTask4.db.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetBean;
import ua.nure.bycheva.SummaryTask4.db.dao.AbstractDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.Fields;
import ua.nure.bycheva.SummaryTask4.db.PassedStatus;
import ua.nure.bycheva.SummaryTask4.db.SQL;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yulia on 30.08.16.
 */
public class SheetDAOImpl extends AbstractDAO implements SheetDAO {

    private static final Logger LOG = Logger.getLogger(SheetDAOImpl.class);

    private static final String TABLENAME = "sheets";

    public SheetDAOImpl(DataSource src) {
        super(src, TABLENAME);
    }

    public SheetDAOImpl() {
        super(TABLENAME);
    }

    @Override
    public Sheet save(Sheet sheet) throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_SAVE_SHEET, PreparedStatement.RETURN_GENERATED_KEYS);

            prepareToSave(st,sheet);

            if(st.executeUpdate() > 0){
                rs = st.getGeneratedKeys();
                if(rs.next()){
                    sheet.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_SAVE_ENTITY) + sheet;
            LOG.error(msg,e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return sheet;
    }

    private void prepareToSave(PreparedStatement st, Sheet sheet) throws SQLException {
        int k = 0;
        st.setLong(++k,sheet.getUid());
        st.setLong(++k,sheet.getFacultyId());
        st.setDate(++k, new Date(sheet.getCreateDate().getTime()));
        st.setInt(++k, sheet.getStatusId());
    }

    @Override
    public boolean update(Sheet sheet)
            throws DataBaseAccessException {
        Connection con = null;
        PreparedStatement st = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_UPDATE_SHEET);

            prepareToUpdate(st,sheet);

            return st.executeUpdate() > 0;
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_UPDATE_ENTITY) + sheet;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            closeStatement(st);
        }
    }

    private void prepareToUpdate(PreparedStatement st, Sheet sheet) throws SQLException {
        int k = 0;
        st.setLong(++k,sheet.getUid());
        st.setLong(++k,sheet.getFacultyId());
        st.setDate(++k, new Date(sheet.getCreateDate().getTime()));
        st.setInt(++k,sheet.getStatusId());
        st.setLong(++k,sheet.getId());
    }

    @Override
    public Sheet findById(Long id) throws DataBaseAccessException {
        PreparedStatement st = null;
        Connection con = null;
        ResultSet rs = null;
        Sheet sheet = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SHEET_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                sheet = extractSheet(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTITY_BY_ID) + TABLENAME + ", id=" + id;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return sheet;
    }

    @Override
    public SheetBean findSheetBeanById(Long id) throws DataBaseAccessException {
        PreparedStatement st = null;
        Connection con = null;
        ResultSet rs = null;
        SheetBean sheet = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SHEET_BEAN_BY_ID);
            st.setLong(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                sheet = extractSheetBean(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_SHEET_INFO_BY_ID);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return sheet;
    }

    @Override
    public List<Sheet> findAll() throws DataBaseAccessException {
        Connection con = null;List<Sheet> sheets = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_SHEETS);
            while(rs.next()){
                sheets.add(extractSheet(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_ENTITIES) + TABLENAME;
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return sheets;
    }

    @Override
    public List<SheetBean> findAllSheetBean() throws DataBaseAccessException {
        Connection con = null;List<SheetBean> sheets = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL.SQL_FIND_ALL_SHEET_BEANS);
            while(rs.next()){
                sheets.add(extractSheetBean(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ALL_SHEETS_INFO);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return sheets;
    }

    @Override
    public void obtainPassedEntrantAndAddToSheet(Long sheetId) throws DataBaseAccessException {

        Map<Long,PassedStatus> passedEntrants = new TreeMap<>();

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_ALL_APPLICATIONS_BY_FACULTY);
            st.setLong(1,sheetId);
            rs = st.executeQuery();

            while(rs.next()) {
                int k = 0;
                int budgetPlaces = rs.getInt(++k);
                int totalPlaces = rs.getInt(++k);
                Long entrantId = rs.getLong(++k);

                if(passedEntrants.size() < budgetPlaces){
                    passedEntrants.put(entrantId,PassedStatus.BUDGET);
                }else if(passedEntrants.size() < totalPlaces){
                    passedEntrants.put(entrantId,PassedStatus.CONTRACT);
                }else{
                    break;
                }
            }

            con.setAutoCommit(false);
            st = con.prepareStatement(SQL.SQL_ADD_ENTRANTS_TO_THE_SHEET);

            for(Map.Entry<Long, PassedStatus> passedEntrant: passedEntrants.entrySet()){
                Long entrantId = passedEntrant.getKey();
                int passedStatusId = passedEntrant.getValue().ordinal();
                int k = 0;
                st.setLong(++k,sheetId);
                st.setLong(++k,entrantId);
                st.setInt(++k,passedStatusId);
                st.addBatch();
            }

            st.executeBatch();

            con.commit();

        }catch (SQLException e) {
            rollback(con);
            e.printStackTrace();
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_ADD_TO_SHEET_PASSED_ENTRANTS);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
    }

    @Override
    public List<SheetEntrantBean> findAllEntrantsSheets(Long sheetId) throws DataBaseAccessException {
        List<SheetEntrantBean> entrants = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_ALL_ENTRANTS_BY_SHEET_ID);
            st.setLong(1,sheetId);
            rs = st.executeQuery();
            while(rs.next()){
                entrants.add(extractSheetEntrant(rs));
            }
        }catch (SQLException e) {
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_ENTRANT_SHEET_INFO);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }

        return entrants;
    }

    @Override
    public Sheet findByNumber(Long uid) throws DataBaseAccessException {
        PreparedStatement st = null;
        Connection con = null;
        ResultSet rs = null;
        Sheet sheet = null;
        try{
            con = getConnection();
            st = con.prepareStatement(SQL.SQL_FIND_SHEET_BY_UID);
            st.setLong(1, uid);
            rs = st.executeQuery();
            if(rs.next()){
                sheet = extractSheet(rs);
            }
        }catch (SQLException e){
            rollback(con);
            String msg = MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_OBTAIN_SHEET_BY_NUMBER);
            LOG.error(msg, e);
            throw new DataBaseAccessException(msg, e);
        }finally {
            close(rs,st,con);
        }
        return sheet;
    }

    private Sheet extractSheet(ResultSet rs) throws SQLException {
        Sheet sheet = new Sheet();
        sheet.setId(rs.getLong(Fields.ENTITY_ID));
        sheet.setUid(rs.getLong(Fields.SHEET_UID));
        sheet.setFacultyId(rs.getLong(Fields.FACULTY_ID));
        sheet.setCreateDate(new java.util.Date(rs.getDate(Fields.SHEET_CREATE_DATE).getTime()));
        sheet.setStatusId(rs.getInt(Fields.SHEET_STATUS_ID));
        return  sheet;
    }

    private SheetBean extractSheetBean(ResultSet rs) throws SQLException {
        SheetBean sheetBean = new SheetBean();
        sheetBean.setId(rs.getLong(Fields.ENTITY_ID));
        sheetBean.setUid(rs.getLong(Fields.SHEET_UID));
        sheetBean.setFacultyName(rs.getString(Fields.FACULTY_NAME));
        sheetBean.setCreateDate(new java.util.Date(rs.getDate(Fields.SHEET_CREATE_DATE).getTime()));
        sheetBean.setStatusName(rs.getString(Fields.SHEET_STATUS_NAME));
        sheetBean.setPassedCount(rs.getInt(6));
        return  sheetBean;
    }

    private SheetEntrantBean extractSheetEntrant(ResultSet rs) throws SQLException {
        SheetEntrantBean sheetEntrantBean = new SheetEntrantBean();
        sheetEntrantBean.setEntrantId(rs.getLong(Fields.ENTRANT_ID));
        sheetEntrantBean.setFullName(String.format("%s %s %s",
                rs.getString(Fields.USER_LAST_NAME), rs.getString(Fields.USER_FIRST_NAME), rs.getString(Fields.USER_MIDDLE_NAME)));
        sheetEntrantBean.setAvgPoint(rs.getDouble(Fields.APPLICATION_AVG_POINT));
        sheetEntrantBean.setPassedStatus(PassedStatus.values()[rs.getInt(Fields.SHEET_PASSED_STATUS_ID)].name());
        return  sheetEntrantBean;
    }
}
