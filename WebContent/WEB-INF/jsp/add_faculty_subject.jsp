<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="add faculty subject" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <a class="icon return" title="<fmt:message key="return.back" bundle="${intface}"/>" href="controller?command=editFaculty&id=${facultyId}">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>

            <c:choose>
                <c:when test="${empty subjects}">
                    <small class="info full-msg center">
                        <fmt:message key="mark.add.empty.subject.list" bundle="${intface}"/>
                    </small>
                </c:when>

                <c:otherwise>
                    <fmt:message key="text.add.faculty.subject" bundle="${intface}"/>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="saveFacultySubject"/>
                        <input type="hidden" name="htmlFormName" value="facultySubjectForm">
                        <input type="hidden" name="id" value="${facultyId}"/>
                        <fieldset>
                            <label>
                                <fmt:message key="faculty.lable.name" bundle="${intface}"/>: ${facultyName}
                            </label>
                        </fieldset>
                        <fieldset>
                            <label for="subjectId">
                                <fmt:message key="subject.lable.name" bundle="${intface}"/>:
                            </label>
                            <select name="subjectId">
                                <c:forEach items="${subjects}" var="subject">
                                    <jsp:useBean id="subject" class="ua.nure.bycheva.SummaryTask4.db.entity.Subject"/>
                                    <option value="${subject.id}">${subject.name}</option>
                                </c:forEach>
                            </select>
                            <custom:message type="ERROR" name="subjectId"/>
                        </fieldset>

                        <input type="submit" value="<fmt:message key="form.button.save" bundle="${intface}"/>">
                    </form>

                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
