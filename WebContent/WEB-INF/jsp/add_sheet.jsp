<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="add sheet" var="title"/>

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

            <fmt:message key="text.add.sheet" bundle="${intface}" var="formTitle"/>
            <p class="form-title">${formTitle}</p>

            <form class="add-entity-form" action="controller" method="post">
                <input type="hidden" name="command" value="saveSheet"/>
                <input type="hidden" name="htmlFormName" value="sheetForm">
                <fieldset>
                    <label for="uid"><fmt:message key="sheet.lable.doc.number" bundle="${intface}"/> </label>
                    <input type="text" name="uid" value="${uid}"/>
                    <custom:message type="ERROR" name="uid"/>
                </fieldset>
                <fieldset>
                    <label form="facultyId"><fmt:message key="faculty.lable.faculty" bundle="${intface}"/> </label>
                    <select name="facultyId">
                        <c:forEach items="${faculties}" var="faculty">
                            <jsp:useBean id="faculty" class="ua.nure.bycheva.SummaryTask4.db.entity.Faculty"/>
                            <option value="${faculty.id}">${faculty.name}</option>
                        </c:forEach>
                    </select>
                    <custom:message  type="ERROR" name="facultyId"/>
                </fieldset>

                <input type="submit" value="<fmt:message key="form.button.save" bundle="${intface}"/> ">
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
