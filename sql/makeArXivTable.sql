CREATE TABLE abstracts (
       id    	       VARCHAR(30)	PRIMARY KEY,
       keynames	       VARCHAR(30)[],
       forenames       VARCHAR(30)[],
       title	       TEXT		NOT NULL,
       categories      VARCHAR(30)[],
       comments	       TEXT		NOT NULL,
       journalref      VARCHAR(100),
       doi	       VARCHAR(100),
       license	       TEXT,
       abstract	       TEXT		NOT NULL
)      
