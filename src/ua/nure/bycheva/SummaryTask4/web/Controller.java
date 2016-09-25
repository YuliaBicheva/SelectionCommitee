package ua.nure.bycheva.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.web.command.Command;
import ua.nure.bycheva.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet controller.
 *
 */
@MultipartConfig
public class Controller extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Controller.class);

    private static final String COMMAND = "command";

    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("Controller starts");

        // obtain command object by its name
        Command command = CommandContainer.get(request.getParameter(COMMAND));
        LOG.debug("Obtained command --> " + command);

        // execute command and get forward address
        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            LOG.debug("Erro message --> " + ex.getLocalizedMessage());
            request.setAttribute(ERROR_MESSAGE, ex.getMessage());
        }

        // go to forward
        if(forward.endsWith("redirect")){
            forward = request.getContextPath().concat(forward);
            LOG.debug("Controller finished, redirect to forward --> " + forward);
            response.sendRedirect(forward);
        }else {
            LOG.debug("Controller finished, now go to forward address --> " + forward);
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}
