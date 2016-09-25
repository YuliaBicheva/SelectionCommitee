<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="error" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">
            <fmt:setBundle basename="message" var="msg" scope="session"/>
            <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
            <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

            <c:if test="${not empty code}">
                <h3>Error code: ${code}</h3>
            </c:if>

            <c:if test="${not empty message}">
                <h3>${message}</h3>
            </c:if>

            <c:if test="${not empty exception}">
                <% exception.printStackTrace(new PrintWriter(out)); %>
            </c:if>

            <c:if test="${not empty requestScope.errorMessage}">
                <h3>${requestScope.errorMessage}</h3>
            </c:if>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
