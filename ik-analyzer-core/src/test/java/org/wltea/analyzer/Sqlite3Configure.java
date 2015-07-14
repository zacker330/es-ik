package org.wltea.analyzer;


import org.wltea.analyzer.cfg.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sqlite3Configure implements Configuration {
    private static Logger logger = Logger.getLogger("Sqlite3Configure");

    private final List<char[]> mainDictionary;
    private final List<char[]> quantifierDictionary;
    private final List<char[]> stopWordDictionary;


    private boolean smartMode = true;


    private Sqlite3Configure(String dbPath) {
        if (dbPath == null || "".equals(dbPath.trim())) {
            logger.log(Level.CONFIG, "dbPath is required!");
            throw new IllegalArgumentException();
        }


        mainDictionary = new ArrayList<char[]>();
        quantifierDictionary = new ArrayList<char[]>();
        stopWordDictionary = new ArrayList<char[]>();
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet mainResult = statement.executeQuery("select * from main_dictionary");
            while (mainResult.next()) {
                String term = mainResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                mainDictionary.add(term.toCharArray());
            }

            ResultSet stopWordResult = statement.executeQuery("select * from stopword_dictionary");
            while (stopWordResult.next()) {
                String term = stopWordResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                stopWordDictionary.add(term.toCharArray());
            }

            ResultSet quantifierResult = statement.executeQuery("select * from quantifier_dictionary");
            while (quantifierResult.next()) {
                String term = quantifierResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                quantifierDictionary.add(term.toCharArray());
            }

        } catch (SQLException e) {
            logger.log(Level.CONFIG, "there's sql error", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.CONFIG, "not found sqlite3 jdbc", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                logger.log(Level.CONFIG, "can't close jdbc connection");
                throw new RuntimeException(e);
            }
        }
    }

    public static Sqlite3Configure smartModeSqlite3Configure(String dbPath) {
        Sqlite3Configure sqlite3Configure = new Sqlite3Configure(dbPath);
        sqlite3Configure.setSmartMode(true);
        return sqlite3Configure;
    }


    /**
     * 返回useSmart标志位
     * isSmartMode =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @return isSmartMode
     */
    public boolean isSmartMode() {
        return smartMode;
    }

    /**
     * 设置useSmart标志位
     * isSmartMode =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @param smartMode
     */
    public void setSmartMode(boolean smartMode) {
        this.smartMode = smartMode;
    }

    @Override
    public List<char[]> getMainDictionary() {
        return mainDictionary;
    }

    @Override
    public List<char[]> getStopWordDictionary() {
        return stopWordDictionary;
    }

    @Override
    public List<char[]> getQuantifierDictionary() {
        return quantifierDictionary;
    }


}
