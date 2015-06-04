package org.elasticsearch.plugin.ikanalyzer;

import org.elasticsearch.common.inject.AbstractModule;

public class IKAnalyzerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IKAnalyzerComponent.class).asEagerSingleton();
    }
}
