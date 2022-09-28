package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    private Resume resume;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        String organizationId = request.getParameter("organizationId");
        String sectionType = request.getParameter("type");
        String periodId = request.getParameter("periodId");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete" -> {
                if (periodId!=null){
                    OrganizationSection section = (OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType));
                    section.getOrganizations().get(Integer.parseInt(organizationId)).getPeriods().remove(Integer.parseInt(periodId));
                    response.sendRedirect("resume?uuid=" + uuid + "&action=edit&type="+sectionType+"&organizationId="+organizationId);
                }
                else if (organizationId == null) {
                    storage.delete(uuid);
                    resume=null;
                    response.sendRedirect("resume");
                } else {
                    ((OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType))).getOrganizations().remove(Integer.parseInt(organizationId));
                    response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                }
                return;
            }
            case "view", "edit" -> {
                if (resume==null || !resume.getUuid().equals(uuid)){
                    resume = storage.get(uuid);
                }
                if (periodId == null) {
                    if (organizationId == null) {
                        request.setAttribute("status", "editResume");
                        request.setAttribute("resume", resume);
                        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);
                    } else {
                        request.setAttribute("status", "editOrg");
                        request.setAttribute("uuid", uuid);
                        request.setAttribute("organization", ((OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType))).getOrganizations().get(Integer.parseInt(organizationId)));
                        request.setAttribute("sectionType", sectionType);
                        request.setAttribute("organizationId", organizationId);
                        request.getRequestDispatcher("/WEB-INF/jsp/organizationEdit.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("status", "editPeriod");
                    request.setAttribute("uuid", uuid);
                    request.setAttribute("sectionType", sectionType);
                    request.setAttribute("organizationId", organizationId);
                    request.setAttribute("period", ((OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType))).getOrganizations().get(Integer.parseInt(organizationId)).getPeriods().get(Integer.parseInt(periodId)));
                    request.getRequestDispatcher("/WEB-INF/jsp/periodEdit.jsp").forward(request, response);
                }
                return;
            }
            case "add" -> {
                if (sectionType == null) {
                    resume = new Resume("");
                } else if (organizationId == null) {
                    request.setAttribute("status", "editOrg");
                    request.setAttribute("organizationId", (-1) + "");
                    request.setAttribute("organization", new Organization());
                    request.setAttribute("sectionType", sectionType);
                    request.setAttribute("uuid", uuid);
                    request.getRequestDispatcher("/WEB-INF/jsp/organizationEdit.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("status", "editPeriod");
                    request.setAttribute("uuid", uuid);
                    request.setAttribute("periodId", (-1) + "");
                    request.setAttribute("period", new Period());
                    request.setAttribute("sectionType", sectionType);
                    request.setAttribute("organizationId", organizationId);
                    request.getRequestDispatcher("/WEB-INF/jsp/periodEdit.jsp").forward(request, response);
                    return;
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("status", "editResume");
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String status = request.getParameter("status");
        boolean isExist = true;
        try {
            storage.get(uuid);
        } catch (NotExistStorageException e) {
            isExist = false;
        }
        switch (status) {
            case "editOrg" -> {
                String name = request.getParameter("name");
                String link = request.getParameter("link");
                String sectionType = request.getParameter("type");
                Integer organizationId = Integer.parseInt(request.getParameter("organizationId"));
                if (!resume.getSections().containsKey(SectionType.valueOf(sectionType))){
                    resume.getSections().put(SectionType.valueOf(sectionType), new OrganizationSection());
                }
                OrganizationSection section = (OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType));
                if (organizationId == -1) {
                    section.getOrganizations().add(new Organization(name, link));
                } else {
                    section.getOrganizations().get(organizationId).setName(name);
                    section.getOrganizations().get(organizationId).setLink(link);
                }
                response.sendRedirect("resume?uuid="+ uuid+"&action=edit");
            }
            case "editPeriod" -> {
                String description = request.getParameter("description");
                String title = request.getParameter("title");
                String dateFrom = request.getParameter("dateFrom");
                String dateTo = request.getParameter("dateTo");
                String sectionType = request.getParameter("type");
                Integer organizationId = Integer.parseInt(request.getParameter("organizationId"));
                Integer periodId = Integer.parseInt(request.getParameter("periodId"));
                if (!resume.getSections().containsKey(SectionType.valueOf(sectionType))){
                    resume.getSections().put(SectionType.valueOf(sectionType), new OrganizationSection());
                }
                OrganizationSection section = (OrganizationSection) resume.getSections().get(SectionType.valueOf(sectionType));
                if (organizationId == -1){
                    section.getOrganizations().add(new Organization());
                    organizationId = section.getOrganizations().size()-1;
                }
                Organization organization = section.getOrganizations().get(organizationId);
                if (periodId == -1) {
                    organization.getPeriods().add(new Period(parseDate(dateFrom), parseDate(dateTo), title, description));
                } else {
                    Period period = organization.getPeriods().get(periodId);
                    period.setTitle(title);
                    period.setDescription(description);
                    period.setDateFrom(parseDate(dateFrom));
                    period.setDateTo(parseDate(dateTo));
                }
                response.sendRedirect("resume?uuid="+ uuid+"&action=edit&type="+sectionType+"&organizationId="+organizationId);
            }
            case "editResume" -> {
                String fullName = request.getParameter("fullName");
                resume.setFullName(fullName);
                for (ContactType type : ContactType.values()) {
                    String value = request.getParameter(type.name());
                    if (value == null || value.trim().length() == 0) {
                        resume.getContacts().remove(type);
                    } else {
                        resume.getContacts().put(type, value);
                    }
                }
                for (SectionType sectionType :
                        SectionType.values()) {
                    switch (sectionType) {
                        case PERSONAL, OBJECTIVE -> {
                            String value = request.getParameter(sectionType.name());
                            if (value == null || value.trim().length() == 0) {
                                resume.getSections().remove(sectionType);
                            } else {
                                resume.getSections().put(sectionType, new TextSection(value));
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATION -> {
                            String value = request.getParameter(sectionType.name());
                            if (value == null || value.trim().length() == 0) {
                                resume.getSections().remove(sectionType);
                            } else {
                                ListSection section = new ListSection();
                                String[] values = value.trim().split("\n");
                                for (String s :
                                        values) {
                                    if (s.trim().length() != 0) {
                                        section.getDescriptions().add(s);
                                    }
                                }
                                if (section.getDescriptions().size() != 0) {
                                    resume.getSections().put(sectionType, section);
                                }
                            }
                        }
                    }
                }
                if (isExist) {
                    storage.update(resume.getUuid(), resume);
                } else {
                    storage.save(resume);
                }
                resume = null;
                response.sendRedirect("resume");
            }
        }
    }

    private LocalDate parseDate(String date){
        String[] strings = date.split("/");
        return LocalDate.of(Integer.parseInt(strings[1]), Integer.parseInt(strings[0]), 1);
    }
}
