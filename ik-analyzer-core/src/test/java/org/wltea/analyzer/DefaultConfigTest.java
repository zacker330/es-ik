package org.wltea.analyzer;

import com.google.common.base.Function;
import org.apache.commons.dbutils.QueryRunner;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class DefaultConfigTest {

    //    @Test
    public void test() throws Exception {
        DefaultConfigTest d = new DefaultConfigTest();
        d.loadMainDictionary();
        d.loadQuantifierDictionary();
        d.loadStopWordDictionary();
    }


    public void loadMainDictionary() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("main.dic");
        readAndProcess(is, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String term) {
                QueryRunner queryRunner = new QueryRunner(new DictionaryDatasource());
                try {
                    int result = queryRunner.update("INSERT OR IGNORE INTO main_dictionary values(?);", term);
                    Assert.assertEquals(1, result);
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
                return true;
            }
        });
    }

    public void loadStopWordDictionary() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("stopword.dic");
        readAndProcess(is, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String term) {
                QueryRunner queryRunner = new QueryRunner(new DictionaryDatasource());
                try {
                    int result = queryRunner.update("INSERT OR IGNORE INTO stopword_dictionary values(?);", term);
                    Assert.assertEquals(1, result);
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
                return true;
            }
        });
    }


    public void loadQuantifierDictionary() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("quantifier.dic");
        readAndProcess(is, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String term) {
                QueryRunner queryRunner = new QueryRunner(new DictionaryDatasource());
                try {
                    int result = queryRunner.update("INSERT OR IGNORE INTO quantifier_dictionary values(?);", term);
                    Assert.assertEquals(1, result);
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
                return true;
            }
        });
    }

    private static void readAndProcess(InputStream inputStream, Function<String, Boolean> function) throws IOException {
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

}
