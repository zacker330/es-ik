package org.wltea.analyzer;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DictionaryDatasource implements DataSource {
    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        String dbPath = this.getClass().getClassLoader().getResource("./dictionary.db").getPath();
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    @Deprecated
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Deprecated
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Deprecated

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Deprecated

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 100;
    }

    @Deprecated

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Deprecated

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }


    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
