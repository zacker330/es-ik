package org.elasticsearch.index.analysis.ik.spi;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.configuration.DictionaryConfiguration;

public interface Configuration extends DictionaryConfiguration {
    Configuration init(Index index, @IndexSettings Settings indexSettings, Environment env, String name, Settings settings);
}
