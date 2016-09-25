<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="applications" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <c:choose>
                <c:when test="${empty requestScope.applications}">
                    <small class="info full-msg center"><fmt:message key="applications.empty" bundle="${intface}"/></small>
                </c:when>

                <c:otherwise>
                    <fmt:message var="tableCaption" key="applications.table.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="applications.row.captions" bundle="${intface}"/>
                    <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                        <c:forEach items="${applications}" var="application" varStatus="loop">
                            <tr>
                                <td>${loop.count}</td>
                                <td>${application.facultyName}</td>
                                <td>
                                    <fmt:formatDate value="${application.createDate}" pattern="dd-MM-YYYY"/>
                                </td>
                                <td>
                                    <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteApplication&id=${application.id}"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </template:table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
