package org.elasticsearch.index.analysis.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.lucene.Sqlite3Configure;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.Reader;

public class IKTokenizerFactory extends AbstractTokenizerFactory {

    private Configuration configuration;


    @Inject
    public IKTokenizerFactory(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        configuration = Sqlite3Configure.smartModeSqlite3Configure(env.settings().get("ik_analyzer_db_path"));
    }

    @Override
    public Tokenizer create(Reader reader) {
        return new IKTokenizer(reader, configuration);
    }
}
