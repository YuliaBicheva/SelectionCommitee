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
            <form action="controller" method="post" class="login">
                <input type="hidden" name="command" value="login"/>
                <input type="hidden" name="htmlFormName" value="loginForm"/>
                <custom:message type="ERROR" name="fail"/>

                <fieldset>
                    <label for="login"><fmt:message key="login.lable" bundle="${intface}"/></label>
                    <input type="text" name="login" value="${requestScope.login}"/>
                    <custom:message name="login" type="ERROR"/>
                </fieldset>
                <fieldset>
                    <label for="password"><fmt:message key="password.lable" bundle="${intface}"/></label>
                    <input type="password" name="password"/>
                    <custom:message name="password" type="ERROR"/>
                </fieldset>

                <input type="submit" value="<fmt:message key="form.button.login" bundle="${intface}"/>">

            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>