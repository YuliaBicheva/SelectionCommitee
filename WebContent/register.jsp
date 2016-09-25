<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set var="title" value="Login"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <form action="controller" method="post" enctype="multipart/form-data">

                <input type="hidden" name="command" value="register">
                <input type="hidden" name="htmlFormName" value="registerForm">

                <fieldset>
                    <label for="login"><fmt:message key="login.lable" bundle="${intface}"/></label>
                    <input type="text" name="login" placeholder="Create login" value="${login}"/>
                    <custom:message type="ERROR" name="login" />
                </fieldset>
                <fieldset>
                    <label for="password"><fmt:message key="password.lable" bundle="${intface}"/></label>
                    <input type="password" lang="ru" name="password" placeholder="Create password min 4 symbols"/>
                    <custom:message type="ERROR" name="password"/>
                </fieldset>
                <fieldset>
                    <label for="password"><fmt:message key="retype.password.lable" bundle="${intface}"/></label>
                    <input type="password" lang="ru" name="password" placeholder="Retype password"/>
                    <custom:message type="ERROR" name="password"/>
                </fieldset>
                <fieldset>
                    <label for="email"><fmt:message key="email.lable" bundle="${intface}"/></label>
                    <input type="email" name="email" value="${email}"/>
                    <custom:message type="ERROR" name="email"/>
                </fieldset>
                <fieldset>
                    <label for="firstName"><fmt:message key="first.name.lable" bundle="${intface}"/></label>
                    <input type="text" name="firstName" value="${firstName}"/>
                    <custom:message type="ERROR" name="firstName"/>
                </fieldset>
                <fieldset>
                    <label for="lastName"><fmt:message key="last.name.lable" bundle="${intface}"/></label>
                    <input type="text" name="lastName" value="${lastName}"/>
                    <custom:message type="ERROR" name="lastName"/>
                </fieldset>
                <fieldset>
                    <label for="middleName"><fmt:message key="middle.name.lable" bundle="${intface}"/></label>
                    <input type="text" name="middleName" value="${middleName}"/>
                    <custom:message type="ERROR" name="middleName"/>
                </fieldset>
                <fieldset>
                    <label for="userLocaleToSet"><fmt:message key="user.locale.lable" bundle="${intface}"/></label>
                    <select id="userLocaleToSet" name="userLocaleToSet">
                        <option value="ru" ${userLocale eq 'ru' ? 'selected' : ''} >Russian</option>
                        <option value="en" ${userLocale eq 'en' ? 'selected' : ''} >English</option>
                    </select>
                </fieldset>

                    <fieldset>
                    <label for="city"><fmt:message key="city.lable" bundle="${intface}"/></label>
                    <input type="text" name="city" value="${city}"/>
                    <custom:message type="ERROR" name="city"/>
                </fieldset>
                <fieldset>
                    <label for="region"><fmt:message key="region.lable" bundle="${intface}"/></label>
                    <input type="text" name="region" value="${region}"/>
                    <custom:message type="ERROR" name="region"/>
                </fieldset>
                <fieldset>
                    <label for="school"><fmt:message key="school.lable" bundle="${intface}"/></label>
                    <input type="text" name="school" value="${school}"/>
                    <custom:message type="ERROR" name="school"/>
                </fieldset>
                <fieldset>
                    <label for="firstName"><fmt:message key="certificate.lable" bundle="${intface}"/></label>
                    <input type="file" name="certificate" aria-multiselectable="false" accept="image/*"/>
                    <custom:message type="ERROR" name="certificate"/>
                </fieldset>

                <input type="submit" value="Register"/>
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>