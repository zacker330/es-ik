package io.github.zacker330.es.ik;

import com.google.common.base.Function;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.wltea.analyzer.IKAnalzyerTest;

import java.io.*;
import java.sql.SQLException;

public abstract class AbstractIntegrationTest {

    public final static String dbPath = AbstractIntegrationTest.class.getResource(".") + "dictionary.db";

    @BeforeClass
    public static void prepareDatabase() throws IOException {


        if (new File(dbPath).exists()) {
            Assert.assertTrue(new File(dbPath).delete());
        }

        Assert.assertTrue(runSQL(dbPath, "CREATE TABLE IF NOT EXISTS main_dictionary(term TEXT NOT NULL,unique(term));"));
        Assert.assertTrue(runSQL(dbPath, "CREATE TABLE IF NOT EXISTS stopword_dictionary(term TEXT NOT NULL,unique(term));"));
        Assert.assertTrue(runSQL(dbPath, "CREATE TABLE IF NOT EXISTS quantifier_dictionary(term TEXT NOT NULL,unique(term));"));
//
        insertTerm("INSERT OR IGNORE INTO quantifier_dictionary values(?);", new IKAnalzyerTest().getClass().getClassLoader().getResourceAsStream("./quantifierDic.properties"));
        insertTerm("INSERT OR IGNORE INTO stopword_dictionary values(?);", new IKAnalzyerTest().getClass().getClassLoader().getResourceAsStream("./stopwordDic.properties"));
        insertTerm("INSERT OR IGNORE INTO main_dictionary values(?);", new IKAnalzyerTest().getClass().getClassLoader().getResourceAsStream("./mainDic.properties"));
    }

    @AfterClass
    public static void cleanDatabase() {
        if (new File(dbPath).exists()) {
            Assert.assertTrue(new File(dbPath).delete());
        }
    }

    private static void insertTerm(String sql, InputStream dataLineByLineInputStream) throws IOException {
        readAndProcessTextInLine(dataLineByLineInputStream, new AbstractIntegrationTest.SQLRunFunction(dbPath, sql));
    }

    private static void readAndProcessTextInLine(InputStream inputStream, Function<String, Boolean> function) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 512);

        String line = null;
        do {
            line = bufferedReader.readLine();
            if (line != null && !"".equals(line.trim())) {
                if (!function.apply(line.trim().toLowerCase())) {
                    break;
                }
            }
        } while (line != null);
    }


    private static class SQLRunFunction implements Function<String, Boolean> {

        private String dbPath;
        private String sql;

        public SQLRunFunction(String dbPath, String sql) {
            this.dbPath = dbPath;
            this.sql = sql;
        }

        @Override
        public Boolean apply(String arg) {
            return runSQL(dbPath, sql, arg);
        }
    }

    private static boolean runSQL(String dbPath, String sql, Object... args) {
        QueryRunner queryRunner = new QueryRunner(new DictionaryDataSource(dbPath));
        try {
            System.out.println("SQL: " + sql);
            int result = queryRunner.update(sql, args);
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


}
