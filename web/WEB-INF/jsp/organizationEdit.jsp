<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: 79060
  Date: 27.09.2022
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Редактирование организации</title>
    <jsp:useBean id="uuid" type="java.lang.String" scope="request"/>
    <jsp:useBean id="organization" type="com.urise.webapp.model.Organization" scope="request"/>
    <jsp:useBean id="sectionType" type="java.lang.String" scope="request"/>
    <jsp:useBean id="organizationId" type="java.lang.String" scope="request"/>
    <jsp:useBean id="status" type="java.lang.String" scope="request"/>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${uuid}">
        <input type="hidden" name="type" value="${sectionType}">
        <input type="hidden" name="organizationId" value="${organizationId}">
        <input type="hidden" name="status" value="${status}">
        <p>
            <input required placeholder="Название" type="text" name="name" size="50" value="${organization.name}">
            <a href="resume?uuid=${uuid}&action=add&type=${sectionType}&organizationId=${organizationId}"><img
                    src="img/add.png"/></a><br/>
            <input placeholder="Ссылка" type="text" name="link" size="50" value="${organization.link}"><br/>
            <c:forEach var="period" items="${organization.periods}">
                <jsp:useBean id="period" type="com.urise.webapp.model.Period"></jsp:useBean>
                    <%
                StringBuilder stringBuilder = new StringBuilder(period.getDateFrom().format(DateTimeFormatter.ofPattern("MM/yyyy")));
                stringBuilder.append(" - ");
                if (period.getDateTo() == null) {
                    stringBuilder.append("Сейчас");
                } else {
                    stringBuilder.append(period.getDateTo().format(DateTimeFormatter.ofPattern("MM/yyyy")));
                }
            %>
        <p>
            <%=stringBuilder.toString()%>
            <a href="resume?uuid=${uuid}&action=edit&type=${sectionType}&organizationId=${organizationId}&periodId=${organization.periods.indexOf(period)}"><img
                    src="img/pencil.png"/></a>
            <a href="resume?uuid=${uuid}&action=delete&type=${sectionType}&organizationId=${organizationId}&periodId=${organization.periods.indexOf(period)}"><img
                    src="img/delete.png"/></a>
        <br/>
        ${period.title}
        <br/>
        ${period.description}
        </p>
        </c:forEach>
        </p>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
