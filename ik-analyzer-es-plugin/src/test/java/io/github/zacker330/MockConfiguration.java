package io.github.zacker330;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.ik.spi.Configuration;
import org.elasticsearch.index.settings.IndexSettings;

import java.util.Collections;
import java.util.List;

public class MockConfiguration implements Configuration{


    @Override
    public Configuration init(Index index, @IndexSettings Settings indexSettings, Environment env, String name, Settings settings) {

        return this;
    }

    @Override
    public boolean isSmartMode() {
        return false;
    }

    @Override
    public void setSmartMode(boolean useSmart) {

    }

    @Override
    public List<char[]> getMainDictionary() {
        return Collections.emptyList();
    }

    @Override
    public List<char[]> getStopWordDictionary() {
        return Collections.emptyList();
    }

    @Override
    public List<char[]> getQuantifierDictionary() {
        return Collections.emptyList();
    }
}
