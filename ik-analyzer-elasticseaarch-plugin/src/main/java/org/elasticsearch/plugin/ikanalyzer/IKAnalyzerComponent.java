package org.elasticsearch.plugin.ikanalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.Reader;

public class IKAnalyzerComponent extends AbstractComponent {

    @Inject
    public IKAnalyzerComponent(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);


        // Register smartcn analyzer
        indicesAnalysisService.analyzerProviderFactories().put("ik-analyzer", new PreBuiltAnalyzerProviderFactory("ik-analyzer", AnalyzerScope.INDICES, new IKAnalyzer(true)));

        // Register smartcn_tokenizer tokenizer
        indicesAnalysisService.tokenizerFactories().put("ik_tokenizer", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "ik_tokenizer";
            }

            @Override
            public Tokenizer create(Reader reader) {
                return new IKTokenizer(reader, true);
            }

        }));

        // Register smartcn_sentence tokenizer -- for backwards compat an alias to smartcn_tokenizer
        indicesAnalysisService.tokenizerFactories().put("ik_sentence", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "ik_sentence";
            }

            @Override
            public Tokenizer create(Reader reader) {
                return new IKTokenizer(reader, true);
            }
        }));

        // Register smartcn_word token filter -- noop
        indicesAnalysisService.tokenFilterFactories().put("ik_word", new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
            @Override
            public String name() {
                return "ik_word";
            }

            @Override
            public TokenStream create(TokenStream tokenStream) {
                return tokenStream;
            }
        }));
    }
}
