-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket VARCHAR(255) UNIQUE NOT NULL,
    price VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE wallet (
    id SERIAL PRIMARY KEY,
    wallet_name VARCHAR(255) UNIQUE NOT NULL,
    active BOOLEAN NOT NULL,
    profile_email VARCHAR(255) REFERENCES profile(email) ON DELETE CASCADE
);

-- Initial data
INSERT INTO profile(email, name) VALUES('sample@email.com', 'John Doe');
INSERT INTO wallet(wallet_name, active, profile_email) VALUES('JohnWallet1', true, 'sample@email.com');
INSERT INTO wallet(wallet_name, active, profile_email) VALUES('JohnWallet2', false, 'sample@email.com');
