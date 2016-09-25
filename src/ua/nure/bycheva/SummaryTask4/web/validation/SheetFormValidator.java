package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Sheet form validator.
 */
public class SheetFormValidator implements Validator{

    @Override
    public String getErrorURLByCommand(String commandName) {
        return Path.ADD_SHEET_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        String parameter = servletRequest.getParameter("uid");

        try {
            if(ValidationUtils.isNulableOrEmptyString(parameter)){
                servletRequest.setAttribute("uid_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
                hasError = true;
            }else if(ValidationUtils.isInteger(parameter)){
                Long uid = Long.valueOf(parameter);
                Sheet sheet = ((SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET)).findByNumber(uid);
                if(sheet != null){
                    servletRequest.setAttribute("uid_error", MessageManager.ERR_SHEET_WITH_THAT_NUMBER_EXIST);
                    hasError = true;
                }
            }else{
                servletRequest.setAttribute("uid_error", MessageManager.ERR_FIELDS_MUST_BE_NUMBER);
                hasError = true;
            }
        } catch (AppException e) {
            servletRequest.setAttribute("error_message",e.getMessage());
            hasError = true;
        }

        return hasError;
    }
}
