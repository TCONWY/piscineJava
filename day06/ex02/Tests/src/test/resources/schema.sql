DROP TABLE IF EXISTS product CASCADE;

CREATE TABLE IF NOT EXISTS product (
    id INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(100),
    price INTEGER
);