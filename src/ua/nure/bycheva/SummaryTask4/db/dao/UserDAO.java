package ua.nure.bycheva.SummaryTask4.db.dao;

import ua.nure.bycheva.SummaryTask4.db.entity.Certificate;
import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.DataBaseAccessException;

import java.util.List;

/**
 * Created by yulia on 12.08.16.
 */
public interface UserDAO extends CrudDAO<User> {

    User findByLogin(String login) throws DataBaseAccessException;

    boolean register(User user, Entrant entrant, Certificate certificate) throws DataBaseAccessException;

    List<User> findAllByEntrantId(Long[] entrantsId) throws DataBaseAccessException;
}
