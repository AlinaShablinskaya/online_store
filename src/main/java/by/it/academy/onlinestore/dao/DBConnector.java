package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private final HikariDataSource hikariDataSource;

    public DBConnector(String fileConfig) {
        HikariConfig hikariConfig = new HikariConfig(fileConfig);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
