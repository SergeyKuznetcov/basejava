package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            try {
                Class.forName("java.sql.Driver");
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            } catch (ClassNotFoundException e) {
                throw new StorageException(e.getMessage());
            }
        });
    }

    @Override
    public void clear() {
        sqlHelper.executeSql("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(String uuid, Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            doModifyRequest(connection, "UPDATE resume SET full_name=? WHERE uuid=?", true, r.getFullName(), uuid);
            doModifyRequest(connection, "DELETE FROM contact WHERE resume_uuid=?", false, uuid);
            doModifyRequest(connection, "DELETE FROM section WHERE resume_uuid=?", false, uuid);
            insertContacts(connection, r, uuid);
            insertSections(connection, r, uuid);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            doModifyRequest(connection, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", false, r.getUuid(), r.getFullName());
            insertContacts(connection, r, r.getUuid());
            insertSections(connection, r, r.getUuid());
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
                setParameters(preparedStatement, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid + " does not exist");
                }
                resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
                setParameters(preparedStatement, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resume.getContacts().put(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {
                setParameters(preparedStatement, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resume.getSections().put(SectionType.valueOf(resultSet.getString("type")),
                            JsonParser.read(resultSet.getString("description"), Section.class));
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(connection -> {
            doModifyRequest(connection, "DELETE FROM resume WHERE uuid=?", true, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> resumeList = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resumeList.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                int i = 0;
                while (resultSet.next()) {
                    while (!resumeList.get(i).getUuid().equals(resultSet.getString("resume_uuid")) && i < resumeList.size()) {
                        i++;
                    }
                    resumeList.get(i).getContacts().put(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                int i = 0;
                while (resultSet.next()) {
                    while (!resumeList.get(i).getUuid().equals(resultSet.getString("resume_uuid")) && i < resumeList.size()) {
                        i++;
                    }
                    resumeList.get(i).getSections().put(SectionType.valueOf(resultSet.getString("type")),
                            JsonParser.read(resultSet.getString("description"), Section.class));
                }
            }
            resumeList.sort((o1, o2) -> o1.getFullName().equals(o2.getFullName()) ? o1.getUuid().compareTo(o2.getUuid()) : o1.getFullName().compareTo(o2.getFullName()));
            return resumeList;
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeSql("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        });
    }

    private void addSection(ResultSet resultSet, Resume resume) throws SQLException {
        SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));

        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> resume.getSections().put(sectionType, new TextSection(resultSet.getString("description")));
            case QUALIFICATION, ACHIEVEMENT -> {
                String[] descriptions = resultSet.getString("description").split("\n");
                resume.getSections().put(sectionType, new ListSection(descriptions));
            }
        }
    }

    private void doModifyRequest(Connection connection, String sql, boolean checkNotExistException, String... parameters) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParameters(preparedStatement, parameters);
            if (checkNotExistException) {
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException("uuid");
                }
            } else {
                preparedStatement.execute();
            }
        }
    }

    private void insertContacts(Connection connection, Resume resume, String uuid) throws SQLException {
        for (Map.Entry<ContactType, String> entry :
                resume.getContacts().entrySet()) {
            doModifyRequest(connection, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", false,
                    uuid, entry.getKey().toString(), entry.getValue());
        }
    }

    private void insertSections(Connection connection, Resume resume, String uuid) throws SQLException {
        for (Map.Entry<SectionType, Section> entry :
                resume.getSections().entrySet()) {
            doModifyRequest(connection, "INSERT INTO section (resume_uuid, type, description) VALUES (?,?,?)",
                    false, uuid, entry.getKey().toString(), JsonParser.write(entry.getValue(), Section.class));
        }
    }

    private void setParameters(PreparedStatement preparedStatement, String... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setString(i + 1, parameters[i]);
        }
    }
}
