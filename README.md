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




# How to use es-ik-sqlite3


- setup the path of sqlite3 DB's file in elasticsearch.yml, which is a configuration file for Elasticsearch

    add field in your elsaticsearch.yml:



   -

# Create a empty sqlite3 db for es-ik-sqlite3

1. create database

        sqlite3 dictionary.db

2. create tables

        CREATE TABLE main_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE quantifier_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE stopword_dictionary(term TEXT NOT NULL,unique(term));


617052 records ~= 30MB db file

# How to update dictionary



