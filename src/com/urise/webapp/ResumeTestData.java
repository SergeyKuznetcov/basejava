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

        Organization organization1 = new Organization("Coursera", "https://www.coursera.org/course/progfun");
        Organization organization2 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        Organization organization3 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        Organization organization4 = new Organization("RIT Center");
        Organization organization5 = new Organization("Wrike", "https://www.wrike.com/");

        organization1.getPeriods().add(new Period("03/2013", " 05/2013", "'Functional Programming Principles in Scala' by Martin Odersky"));
        organization2.getPeriods().add(new Period("09/1993", "07/1996", "Аспирантура (программист С, С++)"));
        organization2.getPeriods().add(new Period("09/1987", "07/1993", "Инженер (программист Fortran, C)"));
        organization3.getPeriods().add(new Period("06/2008", "12/2010", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'"));
        organization4.getPeriods().add(new Period("04/2012", "10/2014", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        organization5.getPeriods().add(new Period("10/2014", "01/2016", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));

        education.getOrganizations().add(organization1);
        education.getOrganizations().add(organization2);
        education.getOrganizations().add(organization3);
        experience.getOrganizations().add(organization4);
        experience.getOrganizations().add(organization5);

        achievement.getDescriptions().add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievement.getDescriptions().add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievement.getDescriptions().add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.getDescriptions().add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.getDescriptions().add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.getDescriptions().add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.getDescriptions().add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.getContacts().put("Тел.", "+7(921) 855-0482");
        resume.getContacts().put("Skype", "skype:grigory.kislin");
        resume.getContacts().put("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put("Профиль GitHub", "https://github.com/gkislin");
        resume.getContacts().put("Профиль Stackoverflow", "https://stackoverflow.com/users/548473");
        resume.getContacts().put("Домашняя страница", "http://gkislin.ru/");


        resume.getSections().put(SectionType.OBJECTIVE, objective);
        resume.getSections().put(SectionType.PERSONAL, personal);
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
        resume.getSections().put(SectionType.QUALIFICATION, qualification);
        resume.getSections().put(SectionType.EDUCATION, education);
        resume.getSections().put(SectionType.EXPERIENCE, experience);

        System.out.println(getResume("uuid1","fullName1"));
    }

    public static Resume getResume(String uuid, String fullName){
        Resume resume = new Resume(uuid, fullName);
        TextSection personal = new TextSection("personalText1");
        TextSection objective = new TextSection("objectiveText1");
        ListSection achievement = new ListSection();
        ListSection qualification = new ListSection();
        OrganizationSection education = new OrganizationSection();
        OrganizationSection experience = new OrganizationSection();

        Organization organization1 = new Organization("Organization1", "link1");
        Organization organization2 = new Organization("Organization2", "link2");
        Organization organization3 = new Organization("Organization3", "link3");

        organization1.getPeriods().add(new Period("date1.1", "date1.2", "title1"));
        organization2.getPeriods().add(new Period("date2.1", "date2.2", "title2.1"));
        organization2.getPeriods().add(new Period("date2.3", "date2.4", "title2.2"));
        organization3.getPeriods().add(new Period("date3.1","date3.2","title3"));
        education.getOrganizations().add(organization1);
        education.getOrganizations().add(organization2);
        experience.getOrganizations().add(organization3);

        achievement.getDescriptions().add("achievement1");
        achievement.getDescriptions().add("achievement2");
        achievement.getDescriptions().add("achievement3");

        qualification.getDescriptions().add("qualification1");
        qualification.getDescriptions().add("qualification2");
        qualification.getDescriptions().add("qualification3");

        resume.getContacts().put("Тел.", "+7(921) 855-0482");
        resume.getContacts().put("Skype", "skype:grigory.kislin");

        resume.getSections().put(SectionType.OBJECTIVE, objective);
        resume.getSections().put(SectionType.PERSONAL, personal);
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
        resume.getSections().put(SectionType.QUALIFICATION, qualification);
        resume.getSections().put(SectionType.EDUCATION, education);
        resume.getSections().put(SectionType.EXPERIENCE, experience);
        return resume;
    }
}
