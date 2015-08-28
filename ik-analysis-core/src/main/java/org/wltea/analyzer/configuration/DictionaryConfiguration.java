
package org.wltea.analyzer.configuration;

import java.util.List;

public interface DictionaryConfiguration {
	
	
	
	public boolean isSmartMode();
	
	public void setSmartMode(boolean useSmart);
	
    List<char[]> getMainDictionary();

    List<char[]> getStopWordDictionary();

    List<char[]> getQuantifierDictionary();
}
