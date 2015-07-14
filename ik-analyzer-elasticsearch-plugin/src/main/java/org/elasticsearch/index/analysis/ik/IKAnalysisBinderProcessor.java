package org.elasticsearch.index.analysis.ik;

import org.elasticsearch.index.analysis.AnalysisModule;

public class IKAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {
    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("ik_analyzer", IKAnalyzerProvider.class);
    }

    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
        tokenizersBindings.processTokenizer("ik_tokenizer", IKTokenizerFactory.class);
    }
}
