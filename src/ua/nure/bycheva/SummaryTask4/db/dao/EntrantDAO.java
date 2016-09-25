package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.bean.UserEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 15.08.16.
 */
public interface EntrantDAO extends CrudDAO<Entrant> {

    Entrant findByUserId(Long userId) throws DataBaseAccessException;

    List<UserEntrantBean> findAllUserEntrantBeans() throws DataBaseAccessException;

    UserEntrantBean findUserEntrantBean(Long eId) throws DataBaseAccessException;
}
