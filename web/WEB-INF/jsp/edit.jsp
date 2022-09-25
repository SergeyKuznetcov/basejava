<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
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
                            <%StringBuilder stringBuilder = new StringBuilder();
                                if (resume.getSections().containsKey(sectionType)){
                                    ListSection listSection =(ListSection) resume.getSections().get(sectionType);
                                    for (String str :
                                            listSection.getDescriptions()) {
                                        stringBuilder.append(str);
                                        stringBuilder.append("\n");
                                    }
                                }%>
                            <textarea name="${sectionType.name()}"><%=stringBuilder.toString()%></textarea>
                        </dd>
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
