package org.wltea.analyzer.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.wltea.analyzer.configuration.DictionaryConfiguration;

import java.io.Reader;

public final class IKAnalyzer extends Analyzer {

    private DictionaryConfiguration configuration;

    public IKAnalyzer(DictionaryConfiguration configuration) {
        super();
        this.configuration = configuration;
    }
    @Override
    protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
        Tokenizer _IKTokenizer = new IKTokenizer(in, configuration);
        return new TokenStreamComponents(_IKTokenizer);
    }

}
