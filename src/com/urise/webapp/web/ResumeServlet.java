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

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> resume = storage.get(uuid);
            case "add" -> resume = new Resume("");
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        boolean isExist = true;
        try {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }catch (NotExistStorageException e){
            resume = new Resume(uuid, fullName);
            isExist = false;
        }
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
            switch (sectionType){
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
                            section.getDescriptions().add(s);
                        }
                        resume.getSections().put(sectionType, section);
                    }
                }
            }
        }
        if (isExist){
            storage.update(resume.getUuid(), resume);
        }else {
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }
}
