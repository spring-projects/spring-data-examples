CREATE TABLE IF NOT EXISTS lego_set (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS manual (
  id      INTEGER IDENTITY PRIMARY KEY,
  lego_set INTEGER,
  author  CHAR(100),
  text    VARCHAR(1000)
);
CREATE TABLE IF NOT EXISTS Model (
  name        VARCHAR(100),
  description CLOB,
  lego_set     INTEGER
);
