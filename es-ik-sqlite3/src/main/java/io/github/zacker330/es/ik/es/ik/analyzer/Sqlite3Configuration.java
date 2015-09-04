package io.github.zacker330.es.ik.es.ik.analyzer;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.ik.spi.Configuration;
import org.elasticsearch.index.settings.IndexSettings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sqlite3Configuration implements Configuration {

    private final ESLogger logger = ESLoggerFactory.getLogger(Sqlite3Configuration.class.getName());

    private List<char[]> mainDictionary;
    private List<char[]> quantifierDictionary;
    private List<char[]> stopWordDictionary;


    private boolean smartMode = true;

    public Sqlite3Configuration() {
    }

    private Sqlite3Configuration(String dbPath) {
        if (dbPath == null || "".equals(dbPath.trim())) {
            logger.error("dbPath is required!");
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
            logger.error("there's sql error", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.error("not found sqlite3 jdbc", e);
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
                logger.error("can't close jdbc connection", e);
                throw new RuntimeException(e);
            }
        }
    }

    public static Sqlite3Configuration smartModeSqlite3Configure(String dbPath) {
        Sqlite3Configuration sqlite3Configure = new Sqlite3Configuration(dbPath);
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


    @Override
    public Configuration init(Index index, @IndexSettings Settings indexSettings, Environment env, String name, Settings settings) {
        return Sqlite3Configuration.smartModeSqlite3Configure(env.settings().get("ik_analysis_db_path"));
    }
}


