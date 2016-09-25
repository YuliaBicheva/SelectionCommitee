<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<c:set value="subject" var="title"/>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<div class="site-container ${not empty user ? 'loged' : ''}">

    <%@ include file="/WEB-INF/jspf/navigation.jspf" %>

    <div class="section">
        <div class="wrap">

            <a class="icon return"
               title="<fmt:message key="return.back" bundle="${intface}"/>"
               href="controller?command=listEntrants">
                <fmt:message key="return.back" bundle="${intface}"/>
            </a>
            
            <fmt:message key="text.entrant.details" bundle="${intface}" var="formTitle"/>
            <p class="form-title">${formTitle}</p>

            <div class="sheet-details">
                <div>
                    <span><fmt:message key="entrant.fullname.lable" bundle="${intface}"/></span> ${entrant.fullName}
                </div>

                <div>
                    <span><fmt:message key="entrant.email.lable" bundle="${intface}"/></span> ${entrant.email}
                </div>
                <div>
                    <span><fmt:message key="entrant.city.lable" bundle="${intface}"/></span> ${entrant.city}
                </div>

                <div>
                    <span><fmt:message key="entrant.region.lable" bundle="${intface}"/></span> ${entrant.region}
                </div>
                <div>
                    <span><fmt:message key="entrant.school.lable" bundle="${intface}"/> </span>${entrant.school}
                </div>
                <div>
                    <span><fmt:message key="entrant.status.lable" bundle="${intface}"/> </span>${entrant.statusName}
                </div>
            </div>

            

            <c:choose>
                <c:when test="${empty applications}">
                    <small class="info full-msg center mark-info"><fmt:message key="applications.empty" bundle="${intface}" /></small>
                </c:when>

                <c:otherwise>
                    <template:table tablecaption="Applications list" rowcaptions="Faculty name;Create date">
                        <c:forEach items="${applications}" var="app" varStatus="loop">
                            <jsp:useBean id="app" class="ua.nure.bycheva.SummaryTask4.db.bean.EntrantApplicationBean"/>
                            <tr>
                                <td>${loop.count}</td>
                                <td>${app.facultyName}</td>
                                <td><fmt:formatDate value="${app.createDate}" pattern="dd-MM-YYYY"/></td>
                            </tr>
                        </c:forEach>
                    </template:table>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty certmarks}">
                    <small class="info full-msg center mark-info"><fmt:message key="marks.cert.empty" bundle="${intface}" /></small>
                </c:when>

                <c:otherwise>
                    <custom:message  type="ERROR" name="markValue_cert"/>
                    <jsp:useBean id="mark" class="ua.nure.bycheva.SummaryTask4.db.bean.EntrantMarkBean" scope="page"/>
                    <fmt:message var="tableCaption" key="mark.cert.table.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="admin.mark.table.row.captions" bundle="${intface}"/>
                    <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">
                        <c:forEach items="${certmarks}" var="certmark" varStatus="loop">
                            <tr>
                                <td>-</td>
                                <td>${certmark.subjectName}</td>
                                <td>${certmark.markValue}</td>
                            </tr>
                        </c:forEach>
                    </template:table>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${empty testmarks}">
                    <small class="info full-msg center"><fmt:message key="marks.test.empty" bundle="${intface}"/></small>
                </c:when>

                <c:otherwise>
                    <custom:message  type="ERROR" name="markValue_test"/>
                    <fmt:message var="tableCaption" key="mark.test.table.caption" bundle="${intface}"/>
                    <fmt:message var="rowCaptions" key="admin.mark.table.row.captions" bundle="${intface}"/>
                    <template:table table_class="entity-table" tablecaption="${tableCaption}" rowcaptions="${rowCaptions}">

                        <c:forEach items="${testmarks}" var="testmark" varStatus="loop">
                            <tr>
                                <td>-</td>
                                <td>${testmark.subjectName}</td>
                                <td>${testmark.markValue}</td>
                            </tr>
                        </c:forEach>
                    </template:table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
</body>
</html>
