package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.getInstance().getStorage();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF8");
        String uuid = request.getParameter("uuid");
        if (uuid==null){
            StringBuilder stringBuilder = new StringBuilder("<table>\n<tr>\n<th>uuid</th>\n<th>full_name</th>\n</tr>\n");
            List<Resume> list = storage.getAllSorted();
            for (Resume r :
                    list) {
                stringBuilder.append("<tr>\n<th>");
                stringBuilder.append(r.getUuid());
                stringBuilder.append("</th>\n<th>");
                stringBuilder.append(r.getFullName());
                stringBuilder.append("</th>\n</tr>\n");
            }
            stringBuilder.append("</table>");
            response.getWriter().write(stringBuilder.toString());
        }else {
            response.getWriter().write(storage.get(uuid).toString());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
