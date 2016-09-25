<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set var="title" value="faculties"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<head>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/css/custom.css"/>
</head>
<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">
            <div class="content">
                <p class="sort-menu">
                    <span><fmt:message key="link.faculty.sort" bundle="${intface}"/></span>
                    <a href="controller?command=listFaculties&comparator=FNameAZ" class="${sessionScope.facultySort eq 'FNameAZ' ? 'active' : ''}">
                        <fmt:message key="link.sort.byname.az" bundle="${intface}"/>
                    </a>
                    <a href="controller?command=listFaculties&comparator=FNameZA" class="${sessionScope.facultySort eq 'FNameZA' ? 'active' : ''}">
                        <fmt:message key="link.sort.byname.za" bundle="${intface}"/>
                    </a>
                    <a href="controller?command=listFaculties&comparator=FBudgPlace" class="${sessionScope.facultySort eq 'FBudgPlace' ? 'active' : ''}">
                        <fmt:message key="link.faculty.sort.bybudget.places" bundle="${intface}"/>
                    </a>
                    <a href="controller?command=listFaculties&comparator=FTotalPlace" class="${sessionScope.facultySort eq 'FTotalPlace' ? 'active' : ''}">
                        <fmt:message key="link.faculty.sort.bytotal.places" bundle="${intface}"/>
                    </a>
                </p>

                <c:choose>
                    <c:when test="${empty faculties}">
                        <small class="info full-msg center mark-info"><fmt:message key="faculties.empty" bundle="${intface}" /></small>
                        <a class="icon add" href="<c:url value="addFaculty.html"/>" title="<fmt:message key="link.add.faculty" bundle="${intface}"/>"></a>
                    </c:when>

                    <c:otherwise>
                        <fmt:message var="tableCaption" key="faculties.table.caption" bundle="${intface}"/>
                        <fmt:message var="rowCaptions" key="faculties.table.row.captions" bundle="${intface}"/>
                        <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                            <c:forEach items="${requestScope.faculties}" var="faculty" varStatus="loop">
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${faculty.name}</td>
                                    <td>${faculty.budgetPlaces}</td>
                                    <td>${faculty.totalPlaces}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${userRole eq 'ADMIN'}">
                                                <a class="icon edit" title="<fmt:message key="link.edit" bundle="${intface}"/>" href="controller?command=editFaculty&id=${faculty.id}"></a>
                                                <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteFaculty&id=${faculty.id}"></a>
                                            </c:when>

                                            <c:when test="${userRole eq 'ENTRANT'}">
                                                <c:choose>
                                                    <c:when test="${appliedFaculties.contains(faculty.id)}">
                                                        <a  class="icon ok" href="#" title="<fmt:message key="text.faculty.already.applied" bundle="${intface}"/>" disabled="true"></a>

                                                    </c:when>
                                                    <c:when test="${!appliedFaculties.contains(faculty.id)}">
                                                        <a  class="icon apply" href="controller?command=createApplication&id=${faculty.id}" title="<fmt:message key="link.apply.faculty" bundle="${intface}"/>"></a>
                                                    </c:when>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${userRole eq 'ADMIN'}">
                                <tr class="button-line">
                                    <td>
                                        <a class="icon add" href="<c:url value="addFaculty.html"/>" title="<fmt:message key="link.add.faculty" bundle="${intface}"/>"></a>
                                    </td>
                                </tr>
                            </c:if>
                        </template:table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>