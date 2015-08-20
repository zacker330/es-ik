package org.elasticsearch.index.analysis.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.analysis.ik.spi.Configuration;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.Reader;
import java.util.Iterator;
import java.util.ServiceLoader;

public class IKTokenizerFactory extends AbstractTokenizerFactory {
    private final ESLogger logger = ESLoggerFactory.getLogger(IKTokenizerFactory.class.getName());

    private Configuration configuration;
    private ServiceLoader<Configuration> loader;


    @Inject
    public IKTokenizerFactory(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        loader = ServiceLoader.load(Configuration.class);
        Iterator<Configuration> iterator = loader.iterator();
        if (!iterator.hasNext()) {
            throw new NotFoundIKAnalyzerConfigurationImplementation();
        }

        configuration = iterator.next();
        configuration.init(index, indexSettings, env, name, settings);

    }

    @Override
    public Tokenizer create(Reader reader) {
        return new IKTokenizer(reader, configuration);
    }
}
