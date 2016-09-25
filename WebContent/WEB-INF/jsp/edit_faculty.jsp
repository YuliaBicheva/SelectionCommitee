<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set var="title" value="faculty"/>

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

            <a class="icon return"
               title="<fmt:message key="return.back" bundle="${intface}"/>"
               href="controller?command=listFaculties">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>

            <fmt:message key="text.edit.faculty" bundle="${intface}" var="formTitle"/>
            <p class="form-title">${formTitle}</p>

            <form action="controller" method="post" class="full-form">
            <div class="half">
                <jsp:useBean id="faculty" class="ua.nure.bycheva.SummaryTask4.db.entity.Faculty" scope="request"/>
                <input type="hidden" name="command" value="updateFaculty"/>
                <input type="hidden" name="htmlFormName" value="facultyForm">
                <input type="hidden" name="id" value="${faculty.id}"/>

                <custom:message type="ERROR"/>
                <fieldset>
                    <label for="name"><fmt:message key="faculty.lable.name" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="name"/>
                    <input type="text" name="name" value="${faculty.name}">
                </fieldset>

                <fieldset>
                    <label for="budgetPlaces"><fmt:message key="faculty.lable.budgetplaces" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="budgetPlaces"/>
                    <input type="text" name="budgetPlaces" value="${faculty.budgetPlaces}"/>
                </fieldset>
                <fieldset>
                    <label for="totalPlaces"><fmt:message key="faculty.lable.totalplaces" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="totalPlaces"/>
                    <input type="text" name="totalPlaces" value="${faculty.totalPlaces}"/>
                </fieldset>

                    </div>
                <div class="right half">

                <c:choose>
                    <c:when test="${empty facultySubjects}">
                        <small class="info full-msg center"><fmt:message key="faculty.subjects.empty" bundle="${intface}"/></small>
                        <a class="icon add" title="<fmt:message key="link.add.subject" bundle="${intface}"/>" href="<c:url value="controller?command=addFacultySubject&id=${faculty.id}"/>">
                        </a>
                    </c:when>

                    <c:otherwise>
                        <custom:message type="ERROR" name="ratio"/>
                        <fmt:message var="tableCaption" key="faculty.subjects.table.caption" bundle="${intface}"/>
                        <fmt:message var="rowCaptions" key="faculty.subjects.table.row.captions" bundle="${intface}"/>
                        <fmt:message var="useDotInfo" key="text.use.dot" bundle="${intface}"/>
                        <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">

                            <c:forEach items="${facultySubjects}" var="facultySubject" varStatus="loop">
                                <jsp:useBean id="facultySubject" class="ua.nure.bycheva.SummaryTask4.db.bean.FacultySubjectBean" scope="page"/>
                                <tr>
                                    <td>${loop.count}</td>
                                    <td>${facultySubject.subjectName}</td>
                                    <td>
                                        <label for="subjectRatio" about="RRR" title="${useDotInfo}">
                                        <input id="subjectRatio" type="text" name="subject_${facultySubject.subjectId}_ratio"
                                               value="${facultySubject.ratio}" class="subject_ratio" />
                                        </label>
                                    </td>
                                    <td>
                                        <a class="icon delete"
                                           href="controller?command=deleteFacultySubject&subjectId=${facultySubject.subjectId}&facultyId=${facultySubject.facultyId}"
                                           title="<fmt:message key="link.delete" bundle="${intface}"/>"></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${facultySubjects.size() lt 3}">
                                <tr class="button-line">
                                    <td>
                                        <a class="icon add" title="<fmt:message key="link.add.subject" bundle="${intface}"/>" href="<c:url value="controller?command=addFacultySubject&id=${faculty.id}"/>">
                                        </a>
                                    </td>
                                </tr>
                            </c:if>
                        </template:table>

                    </c:otherwise>

                </c:choose>

                    </div>
                <blockquote><i><fmt:message key="certificate.ratio" bundle="${intface}"/>: <span id="ratio"></span></i></blockquote>
                <span id="warning" ></span>
                <input id="updateButton" type="submit" value="<fmt:message key="form.button.update" bundle="${intface}"/>">
            </form>
        </div>

    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>