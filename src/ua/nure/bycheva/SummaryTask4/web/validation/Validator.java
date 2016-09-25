package ua.nure.bycheva.SummaryTask4.web.validation;

import javax.servlet.ServletRequest;

/**
 * Main interface for the Validator pattern implementation.
 *
 *
 */
public interface Validator {

    /**
     * Method obtain address to go if the form has invalid fields by the given command name.
     * @param commandName command name which was caled before validation.
     * @return Address to go once the form was invalid.
     */
    String getErrorURLByCommand(String commandName);

    /**
     * Validation command for validator
     * @param servletRequest
     * @return Form has invalid fields.
     */
    boolean validate(ServletRequest servletRequest);

}
