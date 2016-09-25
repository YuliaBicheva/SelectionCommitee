<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="actionLink" required="true" %>
<%@attribute name="commandName" required="false" %>
<%@attribute name="text" required="false" %>
<%@attribute name="key" required="false" %>
<%@attribute name="bundleName" type="javax.servlet.jsp.jstl.fmt.LocalizationContext" required="false" %>



<form style="display: inline-block; padding-bottom: 20px" action="${actionLink}" method="post">
    <input type="hidden" name="command" value="${commandName}"/>
    <c:if test="${empty key}">
        <input type="submit" value="${text}"/>
    </c:if>
    <c:if test="${!empty key}">
        <input type="submit" value="<fmt:message key="${key}" bundle="${bundleName}"/>"/>
    </c:if>
</form>
