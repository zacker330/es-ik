# Kind of Chinese Analysis for Elasticsearch [![Build Status](https://travis-ci.org/zacker330/es-ik.svg?branch=master)](https://travis-ci.org/zacker330/es-ik)

# Requirements

    - Java 7 update 55 or later

# Structure of es-ik

*  ik-analysis-core

    The algorithm of this module is coming from [ik-analyzer](https://code.google.com/p/ik-analyzer/). In principle, you can use this module to implement a Solor analyzer plugin or a Elasticsearch plugin.

    You just need implement `DictionaryConfiguration` interface to provide dictionary content which is used by analysing content process.

*  ik-analysis-es-plugin:

    Integrate with ik-analyzer-core module and Elasticsearch. Define a kind of [SPI](https://en.wikipedia.org/wiki/Service_provider_interface) which is `Configuration` extends `DictionaryConfiguration`

*  es-ik-sqlite3

    Persist dictionary's content into Sqlite3 database. This module is a kind of `service provider` to SPI Configuration defined in ik-analysis-es-plugin.


# How to use es-ik

Actually, ik-analysis-es-plugin expose a interface `DictionaryConfiguration` a kind of SPI. es-ik-sqlite3 implement it so that ik-analysis-es-plugin can get dictionary's content from Sqlite. In other words, you can get your implementation like persisting dictionary's content into Redis.

SPI is just a kind of concept. In java, I use [ServiceLoader](https://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html) to implement that. As soon as your implementation conforms with ServiceLoader's usage, don't need to change ik-analysis-es-plugin module, you'll get a new ik-analysis-es-plugin's plugin. :P




# How to use es-ik-sqlite3(currently version 1.0.1)


- tell elasticsearch where is you sqlite3 db, add a configuration into your elasticsearch.yml, like:

        ik_analysis_db_path: /opt/ik/dictionary.db

   PS: you can download my dictionary.db from https://github.com/zacker330/es-ik-sqlite3-dictionary


- get in you elasticsearch folder then install plugin:

        ./bin/plugin -i ik-analysis -u https://github.com/zacker330/es-ik-plugin-sqlite3-release/raw/master/es-ik-sqlite3-1.0.1.zip

- test your configuration:

1. create songs index

        curl -X PUT -H "Cache-Control: no-cache" -d '{
            "settings":{
                "index":{
                    "number_of_shards":1,
                    "number_of_replicas": 1
                }
            }
        }' 'http://localhost:9200/songs/'

2. create map for songs/song

        curl -X PUT -H "Cache-Control: no-cache" -d '{
                "song": {
                    "_source": {"enabled": true},
                    "_all": {
                        "indexAnalyzer": "ik_analysis",
                        "searchAnalyzer": "ik_analysis",
                        "term_vector": "no",
                        "store": "true"
                    },
                    "properties":{
                        "title":{
                            "type": "string",
                            "store": "yes",
                            "indexAnalyzer": "ik_analysis",
                            "searchAnalyzer": "ik_analysis",
                            "include_in_all": "true"
                        }
                    }

                }
        }
            ' 'http://localhost:9200/songs/_mapping/song'

3. test it

        curl -X POST  -d '林夕为我们作词' 'http://localhost:9200/songs/_analyze?analyzer=ik_analysis'

        response:
        {"tokens":[{"token":"林夕","start_offset":0,"end_offset":2,"type":"CN_WORD","position":1},{"token":"作词","start_offset":5,"end_offset":7,"type":"CN_WORD","position":2}]}

# Create a empty sqlite3 db for es-ik-sqlite3

1. create database

        sqlite3 dictionary.db

2. create tables

        CREATE TABLE main_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE quantifier_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE stopword_dictionary(term TEXT NOT NULL,unique(term));


617052 records ~= 30MB db file


