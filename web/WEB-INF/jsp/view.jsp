<%@ page import="com.urise.webapp.model.TextSection" %>
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
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"></jsp:useBean>
            <%=contactEntry.getKey().getTitle() + " : " + contactEntry.getValue()%><br/>
        </c:forEach>
    </p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"></jsp:useBean>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
        <c:if test="${resume.sections.get(sectionEntry.key) != null}">
    <h3>${sectionEntry.key.title}</h3>
    <c:choose>
        <c:when test="${sectionEntry.key eq 'PERSONAL' || sectionEntry.key eq 'OBJECTIVE'}">
            <%=((TextSection) section).getDescription()%>
        </c:when>
        <c:when test="${sectionEntry.key eq 'ACHIEVEMENT' || sectionEntry.key eq 'QUALIFICATION'}">
            <c:forEach var="description" items="<%=((ListSection) section).getDescriptions()%>">
                ${description}<br/>
            </c:forEach>
        </c:when>
        <c:when test="${sectionEntry.key eq 'EXPERIENCE' || sectionEntry.key eq 'EDUCATION'}">
            <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                <h4><a href="${organization.link}">${organization.name}</a></h4>
                <c:forEach var="period" items="${organization.periods}">
                    <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                    ${period.title}<br/>
                    <%=period.getDateFrom().format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + period.getDateTo().format(DateTimeFormatter.ofPattern("MM/yyyy"))%>
                    <br/>
                    <c:if test="${period.description ne ''}">
                        ${period.description}<br/>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </c:when>
    </c:choose>
        </c:if>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
