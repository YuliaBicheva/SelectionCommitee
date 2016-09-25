<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="add mark" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

<div class="section">
        <div class="wrap">
            <a class="icon return" title="<fmt:message key="return.back" bundle="${intface}"/>" href="controller?command=listMarks">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>


            <c:choose>
                <c:when test="${empty subjects}">
                    <small class="info full-msg center">
                        <fmt:message key="mark.add.empty.subject.list" bundle="${intface}"/>
                    </small>
                </c:when>

                <c:otherwise>
                    <fmt:message key="text.add.certificate.point" bundle="${intface}" var="certificate"/>
                    <fmt:message key="text.add.test.point" bundle="${intface}" var="test"/>
                    <p class="form-title">${examType eq 'test' ? test : certificate}</p>
                    <form action="controller" method="post" class="add-entity-form">
                        <input type="hidden" name="command" value="saveMark"/>
                        <input type="hidden" name="htmlFormName" value="markForm"/>
                        <input type="hidden" name="examType" value="${examType}"/>
                        <fieldset>
                            <label for="subjectId">
                                <fmt:message key="mark.subject.label" bundle="${intface}"/>
                            </label>
                            <custom:message type="ERROR" name="subjectId"/>
                            <select name="subjectId">
                                <c:forEach items="${subjects}" var="subject">
                                    <jsp:useBean id="subject" class="ua.nure.bycheva.SummaryTask4.db.entity.Subject"/>
                                    <option value="${subject.id}">${subject.name}</option>
                                </c:forEach>
                            </select>
                        </fieldset>
                        <fieldset>
                            <label for="markValue">
                                <fmt:message key="mark.value.label" bundle="${intface}"/>
                            </label>
                            <br/>
                            <custom:message type="ERROR" name="markValue"/>
                            <input type="text" name="markValue" value="${markValue}"/>
                        </fieldset>

                        <input type="submit" value="<fmt:message key="form.button.save" bundle="${intface}"/> "/>
                    </form>
                </c:otherwise>

            </c:choose>

        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>