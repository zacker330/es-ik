
package org.wltea.analyzer.cfg;

import java.util.List;

/**
 * 
 * 配置管理类接口
 * 
 */
public interface Configuration {
	
	
	
	/**
	 * 返回useSmart标志位
	 * isSmartMode =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * @return isSmartMode
	 */
	public boolean isSmartMode();
	
	/**
	 * 设置useSmart标志位
	 * isSmartMode =true ，分词器使用智能切分策略， =false则使用细粒度切分
	 * @param useSmart
	 */
	public void setSmartMode(boolean useSmart);
	
    List<char[]> getMainDictionary();

    List<char[]> getStopWordDictionary();

    List<char[]> getQuantifierDictionary();
}
