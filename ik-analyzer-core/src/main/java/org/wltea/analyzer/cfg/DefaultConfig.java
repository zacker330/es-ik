/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 *
 *
 */
package org.wltea.analyzer.cfg;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultConfig implements Configuration {

    private static final Configuration instance = new DefaultConfig();
    private final List<char[]> mainDictionary;
    private final List<char[]> quantifierDictionary;
    private final List<char[]> stopWordDictionary;

    /*
     * 是否使用smart方式分词
     */
    private boolean useSmart;

    /**
     * 返回单例
     *
     * @return Configuration单例
     */
    public static Configuration getInstance() {
        return instance;
    }

    private DefaultConfig() {
        mainDictionary = new ArrayList<char[]>();
        quantifierDictionary = new ArrayList<char[]>();
        stopWordDictionary = new ArrayList<char[]>();
        Connection connection = null;
        Statement statement = null;
        String dbPath = getClass().getClassLoader().getResource("./dictionary.db").getPath();
        System.out.println("================");
        System.out.println(dbPath);
        System.out.println("================");

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet mainResult = statement.executeQuery("select * from main_dictionary");
            while (mainResult.next()) {
                String term = mainResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                mainDictionary.add(term.toCharArray());
            }

            ResultSet stopWordResult = statement.executeQuery("select * from stopword_dictionary");
            while (stopWordResult.next()) {
                String term = stopWordResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                stopWordDictionary.add(term.toCharArray());
            }

            ResultSet quantifierResult = statement.executeQuery("select * from quantifier_dictionary");
            while (quantifierResult.next()) {
                String term = quantifierResult.getString("term");
                if (term == null || "".equals(term.trim())) {
                    continue;
                }
                quantifierDictionary.add(term.toCharArray());
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 返回useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @return useSmart
     */
    public boolean useSmart() {
        return useSmart;
    }

    /**
     * 设置useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @param useSmart
     */
    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    public List<char[]> getMainDictionary() {
        return mainDictionary;
    }

    @Override
    public List<char[]> getStopWordDictionary() {
        return stopWordDictionary;
    }

    @Override
    public List<char[]> getQuantifierDictionary() {
        return quantifierDictionary;
    }


}
