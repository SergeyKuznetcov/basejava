package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
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
            insertContacts(connection, r, uuid);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            doModifyRequest(connection, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", false, r.getUuid(), r.getFullName());
            insertContacts(connection, r, r.getUuid());
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSql("SELECT * FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid=c.resume_uuid " +
                " WHERE r.uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid + " does not exist");
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                resume.getContacts().put(resultSet.getString("type"), resultSet.getString("value"));
            } while (resultSet.next());
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
        return sqlHelper.executeSql("SELECT uuid,full_name, type, value FROM resume r " +
                "  LEFT JOIN contact c " +
                "    ON r.uuid=c.resume_uuid " +
                " ORDER BY full_name, uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            if (resultSet.next()) {
                Resume resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                do {
                    if (resume.getUuid().equals(resultSet.getString("uuid"))) {
                        resume.getContacts().put(resultSet.getString("type"), resultSet.getString("value"));
                    } else {
                        resumes.add(resume);
                        resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                        resume.getContacts().put(resultSet.getString("type"), resultSet.getString("value"));
                    }
                } while (resultSet.next());
                resumes.add(resume);
            }
            return resumes;
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
        for (Map.Entry<String, String> entry :
                resume.getContacts().entrySet()) {
            if (entry.getValue()==null){
                doModifyRequest(connection, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", false,
                        uuid, entry.getKey(), "null");
            } else {
                doModifyRequest(connection, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", false,
                        uuid, entry.getKey(), entry.getValue());
            }
        }
    }

    private void setParameters(PreparedStatement preparedStatement, String... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setString(i + 1, parameters[i]);
        }
    }
}
