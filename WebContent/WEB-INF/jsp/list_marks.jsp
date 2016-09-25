<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="marks" var="title"/>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">


            <form action="controller" method="post">
                <input type="hidden" name="htmlFormName" value="updateMarksForm">
                <input type="hidden" name="command" value="updateMarks"/>
                <c:if test="${not empty certmarks or not empty testmarks}">
                    <input type="submit" value="<fmt:message key="form.button.update.allmarks" bundle="${intface}"/>"/>
                </c:if>
                    <custom:message  type="ERROR" name="markValue"/>

                <c:choose>
                    <c:when test="${empty certmarks}">
                        <small class="info full-msg center mark-info"><fmt:message key="marks.cert.empty" bundle="${intface}" /></small>
                        <a class="icon add" title="<fmt:message key="link.add.certificate.mark" bundle="${intface}"/>" href="<c:url value="controller?command=addMark&examType=certificate"/>"></a>
                    </c:when>

                    <c:otherwise>
                        <custom:message  type="ERROR" name="markValue_cert"/>
                        <jsp:useBean id="mark" class="ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean" scope="page"/>
                        <fmt:message var="tableCaption" key="mark.cert.table.caption" bundle="${intface}"/>
                        <fmt:message var="rowCaptions" key="mark.table.row.captions" bundle="${intface}"/>
                        <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                            <c:forEach items="${certmarks}" var="certmark" varStatus="loop">
                                <tr>
                                    <td>-</td>
                                    <td>${certmark.subjectName}</td>
                                    <td><input type="number" name="mark_${certmark.id}_certificate" value="${certmark.markValue}"></td>
                                    <td>
                                        <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteMark&id=${certmark.id}"></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="button-line">
                                <td>
                                    <a class="icon add" title="<fmt:message key="link.add.certificate.mark" bundle="${intface}"/>" href="<c:url value="controller?command=addMark&examType=certificate"/>"></a>
                                </td>
                            </tr>
                        </template:table>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${empty testmarks}">
                        <small class="info full-msg center"><fmt:message key="marks.test.empty" bundle="${intface}"/></small>
                        <a class="icon add" title="<fmt:message key="link.add.test.mark" bundle="${intface}"/>" href="<c:url value="controller?command=addMark&examType=test"/>">
                        </a>
                    </c:when>

                    <c:otherwise>
                        <custom:message  type="ERROR" name="markValue_test"/>
                        <fmt:message var="tableCaption" key="mark.test.table.caption" bundle="${intface}"/>
                        <fmt:message var="rowCaptions" key="mark.table.row.captions" bundle="${intface}"/>
                        <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">

                            <c:forEach items="${testmarks}" var="testmark" varStatus="loop">
                                <tr>
                                    <td>-</td>
                                    <td>${testmark.subjectName}</td>
                                    <td><input type="number" name="mark_${testmars.id}_test" value="${testmark.markValue}"></td>
                                    <td>
                                        <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteMark&id=${testmark.id}"></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="button-line">
                                <td>
                                    <a class="icon add" title="<fmt:message key="link.add.test.mark" bundle="${intface}"/>" href="<c:url value="controller?command=addMark&examType=test"/>">
                                        </a>

                                </td>
                            </tr>
                        </template:table>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
