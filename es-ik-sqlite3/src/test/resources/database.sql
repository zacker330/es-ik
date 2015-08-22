CREATE TABLE main_dictionary(term TEXT NOT NULL,unique(term));

CREATE TABLE stopword_dictionary(term TEXT NOT NULL,unique(term));

CREATE TABLE quantifier_dictionary(term TEXT NOT NULL,unique(term));
