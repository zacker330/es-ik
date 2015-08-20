# Ik-analyzer for Elasticsearch [![Build Status](https://travis-ci.org/zacker330/ik-analyzer.svg?branch=master)](https://travis-ci.org/zacker330/ik-analyzer)

# Requirements

    - Java 7 update 55 or later

# Structure of ik-analyzer

*  ik-analyzer-core

    The algorithm of this module is coming from [ik-analyzer](https://code.google.com/p/ik-analyzer/). In principle, you can use this module to implement a Solor analyzer plugin or a Elasticsearch plugin.

    You just need implement `DictionaryConfiguration` interface to provide dictionary content which is used by analysing content process.

*  ik-analyzer-es-plugin:

    Integrate with ik-analyzer-core module and Elasticsearch. Define a kind of [SPI](https://en.wikipedia.org/wiki/Service_provider_interface) which is `Configuration` extends `DictionaryConfiguration`

*  ik-analyzer-es-plugin-sqlite

    Persist dictionary's content into Sqlite3 database. This module is a kind of `service provider` to SPI Configuration defined in ik-analyzer-es-plugin.


# How to use ik-analyzer

Actually, ik-analyzer-es-plugin expose a interface `DictionaryConfiguration` a kind of SPI. ik-analyzer-es-plugin-sqlite implement it so that ik-analyzer-es-plugin can get dictionary's content from Sqlite. In other words, you can get your implementation like persisting dictionary's content into Redis.

SPI is just a kind of concept. In java, I use [ServiceLoader](https://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html) to implement that. As soon as your implementation conforms with ServiceLoader's usage, don't need to change ik-analyzer-es-plugin module, you'll get a new ik-analyzer-es-plugin's plugin. :P




# How to use ik-analyzer-es-plugin-sqlite


- setup the path of sqlite3 DB's file in elasticsearch.yml, which is a configuration file for Elasticsearch

    add field in your elsaticsearch.yml:



   -

# Create a empty sqlite3 db for ik-analyzer-es-plugin-sqlite

1. create database

        sqlite3 dictionary.db

2. create tables

        CREATE TABLE main_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE quantifier_dictionary(term TEXT NOT NULL,unique(term));
        CREATE TABLE stopword_dictionary(term TEXT NOT NULL,unique(term));

# How to update dictionary



