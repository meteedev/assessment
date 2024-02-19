DROP TABLE IF EXISTS lottery;
CREATE TABLE lottery (
    "ticket" varchar(6) PRIMARY KEY UNIQUE NOT NULL,
    "amount" int4 NOT NULL,
    "price" float8 NOT NULL
);


DROP TABLE IF EXISTS user_ticket;
CREATE TABLE user_ticket (
    "no" SERIAL PRIMARY KEY,
    "user_id" varchar NOT NULL,
    "ticket" varchar(6) NOT NULL,
    "price" float8 NOT NULL,
    "amount" int4 NOT NULL,
    "total_bill" float8 NOT NULL
);