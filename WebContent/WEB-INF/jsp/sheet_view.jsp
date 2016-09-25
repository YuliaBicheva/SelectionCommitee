<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="sheet" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">

            <a class="icon return"
               title="<fmt:message key="return.back" bundle="${intface}"/>"
               href="controller?command=listSheets">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>
<p class="form-title">Sheet details </p>
            <div class="sheet-details">
                <div>
                    <span><fmt:message key="sheet.lable.uid" bundle="${intface}"/></span> ${sheet.uid}
                </div>

                <div>
                    <span><fmt:message key="sheet.lable.faculty.name" bundle="${intface}"/></span> ${sheet.facultyName}
                </div>
                <div>
                    <span><fmt:message key="sheet.lable.create.date" bundle="${intface}"/></span> <fmt:formatDate value="${sheet.createDate}" pattern="dd-MM-YYYY"/>
                </div>

                <div>
                    <span><fmt:message key="sheet.passed.count" bundle="${intface}"/></span> ${sheet.passedCount}
                </div>
                <div>
                    <span><fmt:message key="sheet.status.name" bundle="${intface}"/> </span>${sheet.statusName.replaceAll('_',' ')}
                </div>
            </div>


            <c:choose>
                <c:when test="${sheet.passedCount == 0}">
                    <small class="info full-msg center"><fmt:message key="sheet.entrants.empty" bundle="${intface}"/></small>
                </c:when>

                <c:otherwise>
                    <fmt:message var="tableCaption" key="sheet.entrants.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="sheet.entrants.table.row.captions" bundle="${intface}"/>
                    <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                        <c:forEach items="${entrants}" var="entrantBean" varStatus="loop">
                            <tr>
                                <td>${entrantBean.entrantId}</td>
                                <td>${entrantBean.fullName}</td>
                                <td>${entrantBean.avgPoint}</td>
                                <td>${entrantBean.passedStatus}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${sheet.statusName eq 'in_work'}">
                            <tr class="button-line">
                                <td>
                                    <a class="icon completed" href="controller?command=setSheetFinally&id=${sheet.id}" title="<fmt:message key="link.sheet.set.finally" bundle="${intface}" />"></a>
                                </td>
                            </tr>
	                     </c:if>
                  </template:table>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>