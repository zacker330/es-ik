
package org.wltea.analyzer;

import io.github.zacker330.es.ik.analyzer.Sqlite3Configuration;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Assert;
import org.junit.Test;
import org.wltea.analyzer.configuration.DictionaryConfiguration;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * 使用IKAnalyzer进行分词的演示
 * 2012-10-22
 */
public class IKAnalzyerTest {

    private DictionaryConfiguration configuration;

    @Test
    public void testAnalyzer() {
        //构建IK分词器，使用smart分词模式

        configuration = Sqlite3Configuration.smartModeSqlite3Configure(IKAnalzyerTest.class.getClassLoader().getResource("dictionary.db").getPath());
        Analyzer analyzer = new IKAnalyzer(configuration);

        //获取Lucene的TokenStream对象
        TokenStream tokenStream = null;
        try {
            tokenStream = analyzer.tokenStream("myfield", new StringReader("WORLD ,.. html DATA</html>HELLO"));
//			ts = analyzer.tokenStream("myfield", new StringReader("这是一个中文分词的例子，你可以直接运行它！IKAnalyer can analysis english text too"));
            //获取词元位置属性
            OffsetAttribute offset = tokenStream.addAttribute(OffsetAttribute.class);
            //获取词元文本属性
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            //获取词元文本属性
            TypeAttribute type = tokenStream.addAttribute(TypeAttribute.class);


            //重置TokenStream（重置StringReader）
            tokenStream.reset();

            tokenStream.incrementToken();
            Assert.assertEquals(0, offset.startOffset());
            Assert.assertEquals(5, offset.endOffset());
            Assert.assertEquals("ENGLISH", type.type());
            Assert.assertEquals("world", term.toString());


            tokenStream.incrementToken();
            Assert.assertEquals(10, offset.startOffset());
            Assert.assertEquals(14, offset.endOffset());
            Assert.assertEquals("ENGLISH", type.type());
            Assert.assertEquals("html", term.toString());


            tokenStream.incrementToken();
            Assert.assertEquals(15, offset.startOffset());
            Assert.assertEquals(19, offset.endOffset());
            Assert.assertEquals("ENGLISH", type.type());
            Assert.assertEquals("data", term.toString());

            tokenStream.incrementToken();
            Assert.assertEquals(21, offset.startOffset());
            Assert.assertEquals(25, offset.endOffset());
            Assert.assertEquals("ENGLISH", type.type());
            Assert.assertEquals("html", term.toString());

            tokenStream.incrementToken();
            Assert.assertEquals(26, offset.startOffset());
            Assert.assertEquals(31, offset.endOffset());
            Assert.assertEquals("ENGLISH", type.type());
            Assert.assertEquals("hello", term.toString());


            //关闭TokenStream（关闭StringReader）
            tokenStream.end();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放TokenStream的所有资源
            if (tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
