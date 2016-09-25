<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set var="title" value="Success"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">
            <p><custom:message type="SUCCESS" name="registration"/> </p>

            <%
                String message = request.getParameter( "message" );
                if(message != null){
            %>
            <p><%= message.toString() %></p>
            <%
                }
            %>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
