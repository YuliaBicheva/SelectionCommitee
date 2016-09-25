<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="subjects" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">

            <p class="sort-menu">
                <span><fmt:message key="link.subject.sort" bundle="${intface}"/></span>
                <a href="controller?command=listSubjects&comparator=SNameAZ"
                   class="${sessionScope.subjectSort eq 'SNameAZ' ? 'active' : ''}">
                    <fmt:message key="link.sort.byname.az" bundle="${intface}"/>
                </a>
                <a href="controller?command=listSubjects&comparator=SNameZA"
                   class="${sessionScope.subjectSort eq 'SNameZA' ? 'active' : ''}">
                    <fmt:message key="link.sort.byname.za" bundle="${intface}"/>
                </a>
            </p>

            <c:choose>
                <c:when test="${empty subjects}">
                    <small class="info full-msg center mark-info"><fmt:message key="subjects.empty" bundle="${intface}" /></small>
                    <a class="icon add" title="<fmt:message key="link.add.subject" bundle="${intface}"/>"
                       href="<c:url value="addSubject.html"/>">
                    </a>
                </c:when>

                <c:otherwise>
                    <fmt:message var="tableCaption" key="subjects.table.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="subjects.table.row.captions" bundle="${intface}"/>
                    <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                        <c:forEach items="${requestScope.subjects}" var="subject" varStatus="loop">
                            <tr>
                                <td>${loop.count}</td>
                                <td>${subject.name}</td>
                                <td>
                                    <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteSubject&id=${subject.id}"></a>
                                    <a class="icon edit" title="<fmt:message key="link.edit" bundle="${intface}"/>" href="controller?command=editSubject&id=${subject.id}"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </template:table>
                    <tr class="button-line">
                        <td>
                            <a class="icon add" title="<fmt:message key="link.add.subject" bundle="${intface}"/>"
                               href="<c:url value="addSubject.html"/>">
                            </a>

                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
