package ua.nure.bycheva.SummaryTask4.web.validation;

import java.util.Enumeration;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.FacultyDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.ValidationUtils;

/**
 * Faculty form validator.
 */
public class FacultyFormValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(FacultyFormValidator.class);

    @Override
    public String getErrorURLByCommand(String commandName) {
        return commandName.startsWith("update") ? Path.EDIT_FACULTY_PAGE : Path.PAGE_ADD_FACULTY_PAGE;
    }

    @Override
    public boolean validate(ServletRequest servletRequest){
        boolean hasError = false;

        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            if(paramName.startsWith("subject_") && validateFacultySubjectFields(paramName, paramValue, servletRequest)){
                hasError = true;
            }else if(paramName.endsWith("Places")){
                if (!ValidationUtils.isInteger(paramValue) || ValidationUtils.isNegativeInteger(paramValue)) {
                    servletRequest.setAttribute(paramName.concat("_error"), MessageManager.ERR_FIELDS_MUST_BE_NUMBER);
                    hasError = true;
                }
            }
        }

        String paramBudgetPlaces = servletRequest.getParameter("budgetPlaces");
        String paramTotalPlaces = servletRequest.getParameter("totalPlaces");

        if(ValidationUtils.isInteger(paramBudgetPlaces) && ValidationUtils.isInteger(paramTotalPlaces)){
            if(Integer.valueOf(paramBudgetPlaces).compareTo(Integer.valueOf(paramTotalPlaces)) > 0){
                servletRequest.setAttribute("budgetPlaces_error", MessageManager.ERR_BUDGET_PLACES_CANT_BE_MORE_THAN_TOTAL);
                servletRequest.setAttribute("totalPlaces_error", MessageManager.ERR_BUDGET_PLACES_CANT_BE_MORE_THAN_TOTAL);
                hasError = true;
            }
        }

        try {
            FacultyDAO facultyDAO = (FacultyDAO) DAOManager.getInstance().getDAO(Table.FACULTY);
            Faculty faculty;
            if(ValidationUtils.isNulableOrEmptyString(servletRequest.getParameter("name"))){
                servletRequest.setAttribute("name_error", MessageManager.ERR_FIELDS_CANNOT_BE_EMPTY);
                hasError = true;
            }else if((faculty= facultyDAO.findByName(servletRequest.getParameter("name"))) != null){
                String paramId = servletRequest.getParameter("id");
                if(paramId == null || faculty.getId() != Long.valueOf(paramId)){
                    servletRequest.setAttribute("name_error", MessageManager.ERR_FACULTY_WITH_THAT_NAME_EXISTS);
                    hasError = true;
                }
            }
        } catch (AppException e) {
            servletRequest.setAttribute("error_message",e.getMessage());
            hasError = true;
        }

        return hasError;
    }

    private boolean validateFacultySubjectFields(String paramName, String paramValue, ServletRequest servletRequest) {
        boolean hasError = false;
        String subjectParam = paramName.split("_")[2];
        LOG.trace("validateFacultySubjectFields -> paramName: " + paramName + " paramValue: " + paramValue);
        if(!ValidationUtils.isDecimal(paramValue)){
            LOG.trace("PARAM IS NOT DECIMAL");
            servletRequest.setAttribute(subjectParam.concat("_error"), MessageManager.ERR_MUST_BE_DECIMAL_NO_CHARACTER);
            hasError = true;
        }else{
            LOG.trace("PARAM IS DECIMAL");
            double value = Double.valueOf(paramValue);
            if(subjectParam.equals("ratio")){
                if(value < 0 || value > 1){
                    LOG.trace("PARAM is not beetween 0 and 1: hasError -->" + hasError);
                    servletRequest.setAttribute(subjectParam.concat("_error"), MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_1);
                    hasError = true;
                }
            }
            if(subjectParam.equals("passedValue")){
                if(value < 0 || value > 200){
                    LOG.trace("PARAM is not beetween 0 and 200: hasError -->" + hasError);
                    servletRequest.setAttribute(subjectParam.concat("_error"), MessageManager.ERR_FIELDS_VALUE_MUST_BE_BEETWEEN_0_AND_200);
                    hasError = true;
                }
            }
        }
        return  hasError;
    }

}
