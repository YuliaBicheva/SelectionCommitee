<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="sheets" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">

            <c:choose>
                <c:when test="${empty sheets}">
                    <small class="info full-msg center mark-info"><fmt:message key="sheets.empty" bundle="${intface}" /></small>
                    <a class="icon add" href="controller?command=addSheet" title="<fmt:message key="link.add.sheet" bundle="${intface}"/>"></a>
                </c:when>

                <c:otherwise>
                    <form method="post">
                        <fmt:message var="tableCaption" key="sheets.table.caption" bundle="${intface}"/>
                        <fmt:message var="rowCaptions" key="sheets.table.row.captions" bundle="${intface}"/>
                        <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                            <c:forEach items="${requestScope.sheets}" var="sheet" varStatus="loop">
                                <jsp:useBean id="sheet" class="ua.nure.bycheva.SummaryTask4.db.bean.SheetBean" scope="page"/>
                                <tr>
                                    <td><input type="checkbox" name="sheetId" value="${sheet.id}"/></td>
                                    <td>${sheet.facultyName}</td>
                                    <td>${sheet.uid}</td>
                                    <td>
                                        <fmt:formatDate value="${sheet.createDate}" pattern="dd-MM-YYYY"/>
                                    </td>
                                    <td>${sheet.passedCount}</td>
                                    <td>${sheet.statusName.replace("_"," ")}</td>
                                    <td>
                                        <c:if test="${sheet.statusName eq 'in_work'}">
                                            <a class="icon delete" title="<fmt:message key="link.delete" bundle="${intface}"/>" href="controller?command=deleteSheet&id=${sheet.id}"></a>
                                        </c:if>
                                        <a class="icon show" title="<fmt:message key="link.show" bundle="${intface}"/>" href="controller?command=showSheet&id=${sheet.id}"></a>
                                        <a class="icon generate-file" title="<fmt:message key="link.generate.pdf.file" bundle="${intface}"/>" href="generatePdf?id=${sheet.id}" target="_blank"></a>

                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="button-line">
                                <td>
                                    <a class="icon add" href="controller?command=addSheet" title="<fmt:message key="link.add.sheet" bundle="${intface}"/>"></a>
                                </td>
                            </tr>
                        </template:table>
                    </form>
                </c:otherwise>

            </c:choose>

        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>