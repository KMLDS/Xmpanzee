DROP TABLE IF EXISTS abstracts;
CREATE TABLE abstracts (
       id    	       TEXT		PRIMARY KEY,
       keynames	       TEXT[],
       forenames       TEXT[],
       title	       TEXT		NOT NULL,
       categories      TEXT[],
       comments	       TEXT		NOT NULL,
       journalref      TEXT,
       doi	       TEXT,
       license	       TEXT,
       abstract	       TEXT		NOT NULL
);      
