package ua.nure.bycheva.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Application;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 15.08.16.
 */
public interface ApplicationDAO extends CrudDAO<Application>{

    List<EntrantApplicationBean> findAllByUserId(Long userId) throws DataBaseAccessException;

    List<EntrantApplicationBean> findAllByEntrantId(Long entrantId) throws DataBaseAccessException;

    void calculateAvgPoints() throws DataBaseAccessException;
}
