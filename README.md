# Ik-analyzer for Elasticsearch [![Build Status](https://travis-ci.org/zacker330/ik-analyzer.svg?branch=master)](https://travis-ci.org/zacker330/ik-analyzer)

# Requirements

    - Java 7 update 55 or later

# Structure of ik-analyzer

   - ik-analyzer-core
   - ik-analyzer-elsaticsearch-plugin

# How to use it

   - setup elasticsearch.yml
   -

# Database's schema
CREATE TABLE main_dictionary(term TEXT NOT NULL,unique(term));
CREATE TABLE quantifier_dictionary(term TEXT NOT NULL,unique(term));
CREATE TABLE stopword_dictionary(term TEXT NOT NULL,unique(term));

# How to update dictionary



