-- Drop tables if they exist for clean migration
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS party;

CREATE TABLE party (
    party_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    age INTEGER NOT NULL,
    identification VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL
);

CREATE TABLE customer (
    customer_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    password VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    party_id UUID REFERENCES party(party_id) UNIQUE
);
