package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeSql(String sql, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return sqlExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            throw convertException(e);
        }
    }

    public  <T> T transactionalExecute(SqlTransaction<T> executor){
        try (Connection connection= connectionFactory.getConnection()){
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            }catch (SQLException e){
                connection.rollback();
                throw convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }
    }

    private StorageException convertException(SQLException e){
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException();
        }
        return new  StorageException(e.getMessage());
    }
}
