<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="subject" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">

            <a class="icon return"
               title="<fmt:message key="return.back" bundle="${intface}"/>"
               href="controller?command=listSubjects">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>

            <fmt:message key="text.edit.subject" bundle="${intface}" var="formTitle"/>
            <p class="form-title">${formTitle}</p>

            <custom:message type="ERROR"/>
            <jsp:useBean id="subject" class="ua.nure.bycheva.SummaryTask4.db.entity.Subject" scope="request"/>

            <form class="add-entity-form" name="subjectForm" action="<c:url value='controller'/>" method="post">
                <input type="hidden" name="command" value="updateSubject"/>
                <input type="hidden" name="htmlFormName" value="subjectForm"/>

                <input type="hidden" name="id" value="${subject.id}"/>
                <fieldset>
                    <fmt:message key="subject.lable.name" bundle="${intface}"/>
                    <custom:message name="name" type="ERROR"/>
                    <input type="text" name="name" value="${subject.name}"/>
                </fieldset>

                <input type="submit" value="<fmt:message key="form.button.update" bundle="${intface}"/> ">
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
