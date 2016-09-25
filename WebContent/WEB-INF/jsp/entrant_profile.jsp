<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="ENTRANT PROFILE PAGE" var="title"/>

<html>

<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<form action="controller">
    <input type="hidden" name="command" value="updateEntrantEducation"/>
    <fieldset>
        City:
        <input type="text" name="city"/>
    </fieldset>
    <fieldset>
        Region:
        <input type="text" name="region"/>
    </fieldset>
    <fieldset>
        School:
        <input type="text" name="school"/>
    </fieldset>
    <fieldset>
        Certificate scan:
        <input type="file" name="certificate" aria-multiselectable="false" aria-label="Select certificate"/>
    </fieldset>
</form>

</body>
</html>
