<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="main" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section light-blue">
        <div class="wrap">
            <div class="main-page-content">
                <h1>
                    <fmt:message key="welcome.message" bundle="${intface}"/>
                </h1>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
