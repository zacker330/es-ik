package org.elasticsearch.index.analysis.ik;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.ik.spi.Configuration;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.Iterator;
import java.util.ServiceLoader;

public class IKAnalyzerProvider extends AbstractIndexAnalyzerProvider<IKAnalyzer> {
    private final IKAnalyzer analyzer;
    private ServiceLoader<Configuration> loader;

    @Inject
    public IKAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);

        loader = ServiceLoader.load(Configuration.class);
        Iterator<Configuration> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new NotFoundIKAnalyzerConfigurationImplementation();
        }
        analyzer = new IKAnalyzer(iterator.next().init(index, indexSettings, env, name, settings));
    }

    @Override
    public IKAnalyzer get() {
        return this.analyzer;
    }
}
