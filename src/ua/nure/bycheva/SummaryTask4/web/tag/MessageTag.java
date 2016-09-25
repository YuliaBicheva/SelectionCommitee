package ua.nure.bycheva.SummaryTask4.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.bycheva.SummaryTask4.exception.MessageManager;

/**
 * Custom tag. Provide searching attributes and parameters by special name and
 * print it to the jsp page if have one of needed.
 */
public class MessageTag extends TagSupport {

	private static final long serialVersionUID = 7301343257824608871L;

	private String cssClass = "";

    private String name;

    private MessageType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }


    @Override
    public void release() {
        super.release();
        name = null;
    }

    @Override
    public int doEndTag() throws JspException {
    	HttpServletRequest resuest = (HttpServletRequest) pageContext.getRequest();
        String userLocale = (String) resuest.getSession().getAttribute("userLocale");
        String lookUpAttr = (name != null ? name.concat("_").concat(type.getName()) : type.getName().concat("_message"));
        String message = (String) pageContext.findAttribute(lookUpAttr);
        if(message == null){
            message =  pageContext.getRequest().getParameter(lookUpAttr);
        }
        if(message != null){
            StringBuilder output = new StringBuilder();
            output.append("<small class=\"").append(type.getName()).append(" " + cssClass).append("\">")
                    .append(MessageManager.getInstance().getProperty(message,userLocale)).append("</small>");
            JspWriter out = pageContext.getOut();
            try {
                out.println(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return EVAL_PAGE;
    }
}

