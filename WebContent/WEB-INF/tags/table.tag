<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="count" required="false" %>
<%@ attribute name="tablecaption" required="true" %>
<%@ attribute name="rowcaptions" required="true" %>
<%@ attribute name="table_id" required="false" %>
<%@ attribute name="table_class" required="false" %>

<table id="${table_id}" class="${table_class}">
    <c:if test="${not empty tablecaption}">
        <%--<caption><fmt:message key='${tablecaption}' bundle='${intface}'/></caption>--%>
        <caption>${tablecaption}</caption>
    </c:if>
    <tr>
            <th>#</th>
        <c:forEach items="${rowcaptions.split(';')}" var="h">
            <%--<td><fmt:message key='${fn:trim(h)}' bundle='${intface}'/></td>--%>
            <th>${h}</th>
        </c:forEach>
    </tr>

    <jsp:doBody/>

</table>