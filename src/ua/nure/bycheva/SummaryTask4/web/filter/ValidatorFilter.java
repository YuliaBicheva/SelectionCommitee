package ua.nure.bycheva.SummaryTask4.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.web.validation.Validator;
import ua.nure.bycheva.SummaryTask4.web.validation.ValidatorContainer;

/**
 * Form validator filter. Validate form by given html form name and forward back,
 * if form has error. Usong ValidatorContainer object to obtain validation neccessity.
 */
public class ValidatorFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ValidatorFilter.class);

    private static final String COMMAND = "command";

    private static final String HTML_FORM_NAME = "htmlFormName";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Filter initialization starts");
        //no op
        LOG.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Filter starts");
        boolean hasError;

        String htmlFormName = servletRequest.getParameter(HTML_FORM_NAME);
        LOG.trace("Obtain form name from request: htmlFormName --> " + htmlFormName);

        if(ValidatorContainer.needToValidate(htmlFormName)) {
            LOG.trace("Start validation");

            Validator validator = ValidatorContainer.get(htmlFormName);

            hasError = validator.validate(servletRequest);

            if (hasError) {
                LOG.trace("Form has error");

                returnBackAllParameters(servletRequest);
                String forward = validator.getErrorURLByCommand(servletRequest.getParameter(COMMAND));

                LOG.trace("Forward to --> " + forward);
                servletRequest.getRequestDispatcher(forward).forward(servletRequest, servletResponse);
            } else {
                LOG.debug("Filter finished");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else{
            LOG.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void returnBackAllParameters(ServletRequest servletRequest) {
        Enumeration params = servletRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String) params.nextElement();
            String paramValue = servletRequest.getParameter(paramName);

            servletRequest.setAttribute(paramName,paramValue);
        }
    }

    @Override
    public void destroy() {
        LOG.debug("Filter destruction starts");
        // no op
        LOG.debug("Filter destruction finished");
    }
}
