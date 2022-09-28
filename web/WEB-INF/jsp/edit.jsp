<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="status" type="java.lang.String" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="status" value="${status}">
        <dl>
            <dt>Имя:</dt>
            <dd><input required type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size=30 value="${resume.contacts.get(type)}"></dd>
        </dl>
        </c:forEach>
        <hr>
        <h3>Секции</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
            <c:choose>
                <c:when test="${sectionType eq 'PERSONAL' || sectionType eq 'OBJECTIVE'}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><input type="text" name="${sectionType.name()}" size=50
                                   value="${resume.sections.get(sectionType)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATION'}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <%
                                StringBuilder stringBuilder = new StringBuilder();
                                if (resume.getSections().containsKey(sectionType)) {
                                    ListSection listSection = (ListSection) resume.getSections().get(sectionType);
                                    for (String str :
                                            listSection.getDescriptions()) {
                                        stringBuilder.append(str);
                                        stringBuilder.append("\n");
                                    }
                                }
                            %>
                            <textarea name="${sectionType.name()}"><%=stringBuilder.toString()%></textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <dl>
                        <td>${sectionType.title}</td>
                        <dd><a href="resume?uuid=${resume.uuid}&action=add&type=${sectionType.name()}"><img
                                src="img/add.png"></a></dd>
                        <br/>
                        <br/>
                        <c:if test="${resume.sections.containsKey(SectionType.valueOf(sectionType))}">
                            <c:set var="organizations"
                                   value="<%=((OrganizationSection) resume.getSections().get(sectionType)).getOrganizations()%>"></c:set>
                            <c:forEach var="organization" items="${organizations}">
                                <td><a href="${organization.link}">${organization.name}</a></td>
                                <dd>
                                    <a href="resume?uuid=${resume.uuid}&action=delete&type=${sectionType.name()}&organizationId=${organizations.indexOf(organization)}"><img
                                            src="img/delete.png"></a></dd>
                                <dd>
                                    <a href="resume?uuid=${resume.uuid}&action=edit&type=${sectionType.name()}&organizationId=${organizations.indexOf(organization)}"><img
                                            src="img/pencil.png"></a></dd>
                                <br/>
                                <c:forEach var="period" items="${organization.periods}">
                                    <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                                    <p>
                                        <%=period.getDateFrom().format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + period.getDateTo().format(DateTimeFormatter.ofPattern("MM/yyyy"))%>
                                        <br/>
                                            ${period.title}<br/>
                                        <c:if test="${period.description ne ''}">
                                            ${period.description}<br/>
                                        </c:if>
                                    </p>
                                </c:forEach>
                            </c:forEach>
                        </c:if>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
        </p>
    </form>
    <section/>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
