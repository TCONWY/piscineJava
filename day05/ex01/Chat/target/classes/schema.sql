DROP SCHEMA IF EXISTS chat CASCADE;

CREATE  SCHEMA IF NOT EXISTS  chat;

CREATE TABLE IF NOT EXISTS  chat.user(
id SERIAL PRIMARY KEY,
login text NOT NULL,
password text NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.room(
id SERIAL PRIMARY KEY,
name text NOT NULL,
owner int REFERENCES chat.user(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.message(
id SERIAL PRIMARY KEY,
author int REFERENCES chat.user(id) NOT NULL,
room int REFERENCES chat.room(id) NOT NULL,
text_message text NOT NULL,
timestamp TIMESTAMP  NOT NULL
);