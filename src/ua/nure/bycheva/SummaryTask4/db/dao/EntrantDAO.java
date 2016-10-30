package ua.nure.bycheva.SummaryTask4.db.dao;

import java.util.List;

import ua.nure.bycheva.SummaryTask4.db.bean.UserEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

/**
 * Created by yulia on 15.08.16.
 */
public interface EntrantDAO extends CrudDAO<Entrant> {

    Entrant findByUserId(Long userId) throws DataBaseAccessException;

    List<UserEntrantBean> findAllUserEntrantBeans() throws DataBaseAccessException;

    UserEntrantBean findUserEntrantBean(Long eId) throws DataBaseAccessException;
}
