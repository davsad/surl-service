CREATE DATABASE IF NOT EXISTS surldb;
USE surldb;

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS surl (
  code varchar(8) NOT NULL,
  url varchar(255) NOT NULL,
  username varchar(255),
  updated bigint,
  PRIMARY KEY (code),
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_code_url
  on surl (code,url);

CREATE TABLE IF NOT EXISTS authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);
  
INSERT IGNORE INTO users (username, password, enabled)
  values ('user',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
    1);

INSERT IGNORE INTO authorities (username, authority)
  values ('user', 'ROLE_USER');