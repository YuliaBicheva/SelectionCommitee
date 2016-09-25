<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="settings" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">
            <jsp:useBean id="user" class="ua.nure.bycheva.SummaryTask4.db.entity.User" scope="session"/>

            <form id="settings_form" action="controller" method="post" enctype="multipart/form-data" class="full-form">
                <input type="hidden" name="command" value="updateSettings" />
                <input type="hidden" name="htmlFormName" value="settingsForm"/>

                <div class="${userRole.name eq 'admin' ? 'full' : 'half'}">
                    <fieldset>
                        <label for="login"><fmt:message key="login.lable" bundle="${intface}"/>:</label>
                        <input type="text" id="login" name="login" disabled value="${user.login}">
                    </fieldset>

                    <fieldset>
                        <label for="localeToSet"><fmt:message key="user.locale.lable" bundle="${intface}"/></label>
                        <select id="localeToSet" name="localeToSet">
                            <option value="ru" ${userLocale eq 'ru' ? 'selected' : ''} >Russian</option>
                            <option value="en" ${userLocale eq 'en' ? 'selected' : ''} >English</option>
                        </select>
                    </fieldset>

                    <fieldset>
                        <label for="firstName"><fmt:message key="first.name.lable" bundle="${intface}"/>:</label>
                        <input name="firstName" value="${user.firstName}">
                        <custom:message type="ERROR" name="firstName" />
                    </fieldset>

                    <fieldset>
                        <label for="lastName"><fmt:message key="last.name.lable" bundle="${intface}"/></label>
                        <input name="lastName" value="${user.lastName}">
                        <custom:message type="ERROR" name="lastName" />
                    </fieldset>

                    <fieldset>
                        <label for="middleName"><fmt:message key="middle.name.lable" bundle="${intface}"/></label>
                        <input name="middleName" value="${user.middleName}">
                        <custom:message type="ERROR" name="middleName" />
                    </fieldset>

                    <fieldset>
                        <label for="email"><fmt:message key="email.lable" bundle="${intface}"/></label>
                        <input name="email" value="${user.email}">
                        <custom:message type="ERROR" name="email" />
                    </fieldset>
                </div>

                <c:if test="${userRole eq 'ENTRANT'}">
                <div class="right half">

                    <fieldset>
                        <label for="city"><fmt:message key="city.lable" bundle="${intface}"/></label>
                        <input name="city" value="${entrant.city}">
                        <custom:message type="ERROR" name="city" />
                    </fieldset>

                    <fieldset>
                        <label for="region"><fmt:message key="region.lable" bundle="${intface}" /></label>
                        <input name="region" value="${entrant.region}">
                        <custom:message type="ERROR" name="region" />
                    </fieldset>

                    <fieldset>
                        <label for="school"><fmt:message key="school.lable" bundle="${intface}" /></label>
                        <input name="school" value="${entrant.school}">
                        <custom:message type="ERROR" name="school" />
                    </fieldset>

                    <%--<c:out value="${fileName}"/>--%>
                    <%--<c:if test="${fileName eq not 'null'}">--%>
                    <%--</c:if>--%>
                    <c:if test="${userRole.name eq 'entrant'}">
                        <fieldset>
                            <label for="certificate"><fmt:message key="certificate.lable" bundle="${intface}" /></label>
                            <img alt="Certificate" src="<%=request.getContextPath()%><%=request.getAttribute("fileName")%>">
                            <input type="file" name="certificate" aria-multiselectable="false"/>
                            <custom:message type="ERROR" name="certificate"/>
                        </fieldset>
                    </c:if>
                </div>
                </c:if>


                <%--<img src="<c:url value="${fileName}" />" />--%>

                <input type="submit" value="<fmt:message key="form.button.update" bundle="${intface}" />" /><br/>
            </form>

        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</div>
</body>
</html>