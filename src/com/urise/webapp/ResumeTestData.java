package com.urise.webapp;

import com.urise.webapp.model.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        ListSection achievement = new ListSection();
        ListSection qualification = new ListSection();
        OrganizationSection education = new OrganizationSection();
        OrganizationSection experience = new OrganizationSection();

        achievement.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");


        education.add("Coursera","https://www.coursera.org/course/progfun");
        education.get("Coursera").addPeriod("03/2013", " 05/2013", "'Functional Programming Principles in Scala' by Martin Odersky");
        education.add("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        education.get("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики").addPeriod("09/1993", "07/1996", "Аспирантура (программист С, С++)");
        education.get("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики").addPeriod("09/1987", "07/1993", "Инженер (программист Fortran, C)");
        education.add("Luxoft","http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        education.get("Luxoft").addPeriod("06/2008", "12/2010", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");

        experience.add("RIT Center");
        experience.add("Wrike", "https://www.wrike.com/");
        experience.get("Wrike").addPeriod("10/2014", "01/2016", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experience.get("RIT Center").addPeriod("04/2012", "10/2014", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");

        resume.addContact("Тел.", "+7(921) 855-0482");
        resume.addContact("Skype", "skype:grigory.kislin");
        resume.addContact("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        resume.addContact("Профиль GitHub", "https://github.com/gkislin");
        resume.addContact("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");
        resume.addContact("Домашняя страница", "http://gkislin.ru/");


        resume.addSection(SectionType.OBJECTIVE, objective);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.ACHIEVEMENT, achievement);
        resume.addSection(SectionType.QUALIFICATION, qualification);
        resume.addSection(SectionType.EDUCATION, education);
        resume.addSection(SectionType.EXPERIENCE, experience);

        System.out.println(resume);
    }
}
