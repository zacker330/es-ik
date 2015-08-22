package io.github.zacker330.es.ik;

import com.google.common.base.Function;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class InitDBUtil {

//    @Test
    public void initDB() throws Exception {
        InitDBUtil d = new InitDBUtil();
        d.loadMainDictionary();
        d.loadQuantifierDictionary();
        d.loadStopWordDictionary();
    }


    public void loadMainDictionary() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mainDic.properties");
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
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("stopwordDic.properties");
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
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("quantifierDic.properties");
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
