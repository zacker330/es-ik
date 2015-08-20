package org.wltea.analyzer;

import org.junit.Assert;
import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.Reader;
import java.io.StringReader;

public class IKSegmenterTest {

    @Test
    public void testSegment() throws Exception {
        Reader in = new StringReader("一一分 准确值就是它们听上去的那样。干柴诸如日期或用户ID。当然字符串也可以是准确值，如用户名或邮件地址。准确值Foo与准确值foo是不同的。准确值2014和准确值2014-09-15也是不同的。");
        boolean useSmart = true;
        String dbPath = getClass().getClassLoader().getResource("dictionary.db").getPath();
        IKSegmenter segmenter = new IKSegmenter(in, Sqlite3ConfigureMock.smartModeSqlite3Configure(dbPath));
//        Lexeme lexeme = segmenter.next();
//        do {
//            System.out.println(lexeme.getLexemeText());
//            lexeme = segmenter.next();
//        } while (lexeme != null);

        assertSegmenterCorrect(segmenter.next(), "一一分", 0, 3, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "准确值", 4, 7, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "听", 11, 12, 1, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "上去", 12, 14, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "干柴", 18, 20, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "诸如", 20, 22, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "日期", 22, 24, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "用户", 25, 27, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "id", 27, 29, 2, "ENGLISH");
        assertSegmenterCorrect(segmenter.next(), "当然", 30, 32, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "字符串", 32, 35, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "以是", 37, 39, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "准确值", 39, 42, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "用户名", 44, 47, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "邮件地址", 48, 52, 4, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "准确值", 53, 56, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "foo", 56, 59, 3, "ENGLISH");
        assertSegmenterCorrect(segmenter.next(), "准确值", 60, 63, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "foo", 63, 66, 3, "ENGLISH");
        assertSegmenterCorrect(segmenter.next(), "不同", 67, 69, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "准确值", 71, 74, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "2014", 74, 78, 4, "ARABIC");
        assertSegmenterCorrect(segmenter.next(), "准确值", 79, 82, 3, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "2014-09-15", 82, 92, 10, "LETTER");
        assertSegmenterCorrect(segmenter.next(), "也是", 92, 94, 2, "CN_WORD");
        assertSegmenterCorrect(segmenter.next(), "不同", 94, 96, 2, "CN_WORD");

//        Reader in1 = new StringReader("林夕是个人");
//        IKSegmenter segmenter1 = new IKSegmenter(in1, isSmartMode);
//        Assert.assertEquals("林夕", segmenter1.next().getLexemeText());
//
    }

    private void assertSegmenterCorrect(Lexeme nextLexeme, String lexemeText, int begin, int end, int length, String type) {
        Assert.assertEquals(nextLexeme.getLexemeText(), lexemeText);
        Assert.assertEquals(nextLexeme.getBeginPosition(), begin);
        Assert.assertEquals(nextLexeme.getEndPosition(), end);
        Assert.assertEquals(nextLexeme.getLength(), length);
        Assert.assertEquals(nextLexeme.getLexemeTypeString(), type);


    }
}
