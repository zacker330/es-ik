package org.elasticsearch.plugin.ikanalyzer;

import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Collection;

public class IKAnalyzerPlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "ik-analyzer";
    }

    @Override
    public String description() {
        return "An Chinese analyzer plugin for Elasticsearch";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        return ImmutableList.<Class<? extends Module>>of(IKAnalyzerModule.class);
    }
}
