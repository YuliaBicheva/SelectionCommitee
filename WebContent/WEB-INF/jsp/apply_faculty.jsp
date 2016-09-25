<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="apply faculty" var="title"/>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="applyFaculty"/>
                <input type="hidden" name="htmlFormName" value="applicationForm">

                <input type="hidden" name="id" value="${requestScope.id}"/>
                <input type="hidden" name="entrantId" value="${sessionScope.entrant.id}"/>

                <c:if test="${not empty facultySubjects or not empty testmarks}">
                    <p class="form-title"><fmt:message key="application.caption.test.mark" bundle="${intface}"/></p>
                    <custom:message  name="subject_test" type="ERROR"/>
                    <c:forEach items="${requestScope.facultySubjects}" var="testSubject">
                        <jsp:useBean id="testSubject" class="ua.nure.bycheva.SummaryTask4.db.entity.Subject" scope="page"/>
                        <fieldset>
                            <label for="subject">${testSubject.name}</label>
                            <input type="number" name="subject_${testSubject.id}_test"/>
                        </fieldset>
                    </c:forEach>

                    <c:forEach items="${requestScope.testmarks}" var="mark">
                        <jsp:useBean id="mark" class="ua.nure.bycheva.SummaryTask4.db.bean.ApplicationMarkBean" scope="page"/>
                        <fieldset>
                            <label for="mark">${mark.subjectName}</label>
                            <input type="text" value="${mark.markValue}" disabled/>
                        </fieldset>
                    </c:forEach>
                </c:if>
                <p class="form-title"><fmt:message key="application.caption.certificate.mark" bundle="${intface}"/></p>
                <custom:message  name="subject_certificate" type="ERROR"/>
                <c:forEach items="${requestScope.subjects}" var="certSubject">
                    <fieldset>
                        <label for="subject">${certSubject.name}</label>
                        <input id="subject" type="number" name="subject_${certSubject.id}_certificate" />
                    </fieldset>
                </c:forEach>

                <c:forEach items="${requestScope.certmarks}" var="mark">
                    <fieldset>
                        <label for="mark">${mark.subjectName}</label>
                        <input id="mark" type="text" value="${mark.markValue}" disabled=""/>
                    </fieldset>
                </c:forEach>

                <input type="submit" value="<fmt:message key="form.button.apply" bundle="${intface}"/>">
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
