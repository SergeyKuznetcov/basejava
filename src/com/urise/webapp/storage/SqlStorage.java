package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeSql("DELETE FROM resume", (preparedStatement) -> preparedStatement.execute());
    }

    @Override
    public void update(String uuid, Resume r) {
        sqlHelper.executeSql("UPDATE resume SET full_name=? WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid + " does not exist");
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSql("SELECT full_name FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid + " does not exist");
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSql("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid + " does not exist");
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeSql("SELECT * FROM resume ORDER BY full_name", preparedStatement -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
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
}
