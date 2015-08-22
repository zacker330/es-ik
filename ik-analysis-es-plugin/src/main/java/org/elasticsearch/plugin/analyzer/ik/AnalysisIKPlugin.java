package org.elasticsearch.plugin.analyzer.ik;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.ik.IKAnalysisBinderProcessor;
import org.elasticsearch.plugins.AbstractPlugin;

public class AnalysisIKPlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "ik-analyzer";
    }

    @Override
    public String description() {
        return "IK Chinese analysis support";
    }

    @Override public void processModule(Module module) {
        if (module instanceof AnalysisModule) {
            AnalysisModule analysisModule = (AnalysisModule) module;
            analysisModule.addProcessor(new IKAnalysisBinderProcessor());
        }
    }
}
