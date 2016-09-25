package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.bean.SheetBean;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 30.08.16.
 */
public interface SheetDAO extends CrudDAO<Sheet> {

    SheetBean findSheetBeanById(Long id) throws DataBaseAccessException;

    List<SheetBean> findAllSheetBean() throws DataBaseAccessException;

    void obtainPassedEntrantAndAddToSheet(Long sheetId) throws DataBaseAccessException;

    List<SheetEntrantBean> findAllEntrantsSheets(Long sheetId) throws DataBaseAccessException;

    Sheet findByNumber(Long uid) throws DataBaseAccessException;
}