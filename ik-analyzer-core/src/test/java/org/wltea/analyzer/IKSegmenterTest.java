package org.wltea.analyzer;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.Reader;
import java.io.StringReader;

public class IKSegmenterTest {

    @Test
    public void testSegment() throws Exception {
        Reader in = new StringReader("准确值就是它们听上去的那样。诸如日期或用户ID。当然字符串也可以是准确值，如用户名或邮件地址。准确值Foo与准确值foo是不同的。准确值2014和准确值2014-09-15也是不同的。");
        boolean useSmart = true;
        IKSegmenter segmenter = new IKSegmenter(in, useSmart);
        Lexeme nextLexeme = null;
        do {
            assertSegmenterCorrect(segmenter.next(), "准确", 0, 2, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 2, 3, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "就是", 3, 5, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "它们", 5, 7, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "听", 7, 8, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "上去", 8, 10, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "的", 10, 11, 1, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "那样", 11, 13, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "诸如", 14, 16, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "日期", 16, 18, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "或", 18, 19, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "用户", 19, 21, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "id", 21, 23, 2, "ENGLISH");
            assertSegmenterCorrect(segmenter.next(), "当然", 24, 26, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "字符串", 26, 29, 3, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "也", 29, 30, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "可", 30, 31, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "以是", 31, 33, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "准确", 33, 35, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 35, 36, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "如用", 37, 39, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "户名", 39, 41, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "或", 41, 42, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "邮件地址", 42, 46, 4, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "准确", 47, 49, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 49, 50, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "foo", 50, 53, 3, "ENGLISH");
            assertSegmenterCorrect(segmenter.next(), "与", 53, 54, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "准确", 54, 56, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 56, 57, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "foo", 57, 60, 3, "ENGLISH");
            assertSegmenterCorrect(segmenter.next(), "是", 60, 61, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "不同", 61, 63, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "的", 63, 64, 1, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "准确", 65, 67, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 67, 68, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "2014", 68, 72, 4, "ARABIC");
            assertSegmenterCorrect(segmenter.next(), "和", 72, 73, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "准确", 73, 75, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "值", 75, 76, 1, "CN_CHAR");
            assertSegmenterCorrect(segmenter.next(), "2014-09-15", 76, 86, 10, "LETTER");
            assertSegmenterCorrect(segmenter.next(), "也是", 86, 88, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "不同", 88, 90, 2, "CN_WORD");
            assertSegmenterCorrect(segmenter.next(), "的", 90, 91, 1, "CN_WORD");
        } while (nextLexeme != null);

    }

    private void assertSegmenterCorrect(Lexeme nextLexeme, String lexemeText, int begin, int end, int length, String type) {
        Assert.assertEquals(nextLexeme.getLexemeText(), lexemeText);
        Assert.assertEquals(nextLexeme.getBeginPosition(), begin);
        Assert.assertEquals(nextLexeme.getEndPosition(), end);
        Assert.assertEquals(nextLexeme.getLength(), length);
        Assert.assertEquals(nextLexeme.getLexemeTypeString(), type);


    }
}
