<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="entrants" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <c:choose>
                <c:when test="${empty entrants}">
                    <small class="info full-msg center mark-info"><fmt:message key="entrants.empty" bundle="${intface}" /></small>
                </c:when>

                <c:otherwise>
                    <fmt:message var="tableCaption" key="entrants.table.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="entrants.row.captions" bundle="${intface}"/>
                    <template:table table_class="entrant-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                        <c:forEach items="${requestScope.entrants}" var="entrant">
                            <tr>
                                <td><input type="checkbox" id="entrantId" name="entrantId" value="${entrant.entrantId}"></td>
                                <td>${entrant.fullName}</td>
                                <td>${entrant.email}</td>
                                <td>${entrant.city}</td>
                                <td>${entrant.region}</td>
                                <td>${entrant.school}</td>
                                <td>${entrant.statusName}</td>
                                <td>
                                    <a class="icon ${entrant.statusName eq 'blocked' ? 'unblock': 'block'}"
                                       title="<fmt:message key="${entrant.statusName eq 'blocked' ? 'link.entrant.unblock': 'link.entrant.block'}" bundle="${intface}"/>"
                                       href="controller?command=changeEntrantStatus&entrantId=${entrant.entrantId}">
                                    </a>
                                    <a class="icon show" title="<fmt:message key="link.show" bundle="${intface}"/>" href="controller?command=entrantDetails&entrantId=${entrant.entrantId}"></a>

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
