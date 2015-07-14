package org.elasticsearch.index.analysis.ik;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;
import org.wltea.analyzer.lucene.Sqlite3Configure;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerProvider extends AbstractIndexAnalyzerProvider<IKAnalyzer> {
    private final IKAnalyzer analyzer;

    @Inject
    public IKAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        analyzer = new IKAnalyzer(Sqlite3Configure.smartModeSqlite3Configure(env.settings().get("db_path")));
    }

    @Override
    public IKAnalyzer get() {
        return this.analyzer;
    }
}
