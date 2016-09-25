<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="add faculty" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">
            <a class="icon return" title="<fmt:message key="return.back" bundle="${intface}"/>" href="controller?command=listFaculties">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>
            <fmt:message key="text.add.faculty" bundle="${intface}" var="formTitle"/>

            <p class="form-title">${formTitle}</p>
            <custom:message type="ERROR"/>

            <form name="addFaculty" action="controller" method="post" class="add-entity-form">
                <input type="hidden" name="command" value="saveFaculty"/>
                <input type="hidden" name="htmlFormName" value="facultyForm">
                <fieldset>
                    <label for="name"><fmt:message key="faculty.lable.name" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="name"/>
                    <input type="text" name="name" value="${name}"/>
                </fieldset>
                <fieldset>
                    <label for="budgetPlaces"><fmt:message key="faculty.lable.budgetplaces" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="budgetPlaces"/>
                    <input type="text" name="budgetPlaces" value="${budgetPlaces}"/>
                </fieldset>
                <fieldset>
                    <label for="totalPlaces"><fmt:message key="faculty.lable.totalplaces" bundle="${intface}"/></label>
                    <custom:message type="ERROR" name="totalPlaces"/>
                    <input type="text" name="totalPlaces" value="${totalPlaces}"/>
                </fieldset>


                <input type="submit" value="<fmt:message key="form.button.save" bundle="${intface}"/>">
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
