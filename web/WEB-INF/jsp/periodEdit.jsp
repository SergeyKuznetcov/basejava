<%--
  Created by IntelliJ IDEA.
  User: 79060
  Date: 28.09.2022
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Редактирование периода</title>
    <jsp:useBean id="sectionType" type="java.lang.String" scope="request"/>
    <jsp:useBean id="organizationId" type="java.lang.String" scope="request"/>
    <jsp:useBean id="period" type="com.urise.webapp.model.Period" scope="request"/>
    <jsp:useBean id="periodId" type="java.lang.String" scope="request"/>
    <jsp:useBean id="status" type="java.lang.String" scope="request"/>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${uuid}">
        <input type="hidden" name="type" value="${sectionType}">
        <input type="hidden" name="organizationId" value="${organizationId}">
        <input type="hidden" name="periodId" value="${periodId}">
        <input type="hidden" name="status" value="${status}">
        <p>
            <input placeholder="Дата начала ММ/гггг" type="text" name="dateFrom" required
                   value="${period.dateFrom.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
            <input placeholder="Дата окончания ММ/гггг" type="text" name="dateTo" required
                   value="${period.dateFrom.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
        <p>
            <input placeholder="Title" type="text" name="title" required value="${period.title}">
            <input placeholder="Описание" type="text" name="description" required value="${period.description}">
        </p>
        </p>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
