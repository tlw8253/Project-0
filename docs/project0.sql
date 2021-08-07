-- Project 0 script
--
/*
 * Project 0 script
 */

-- CREATE USER 'project0'@localhost IDENTIFIED BY 'project0';

-- drop table if it exists
-- DROP TABLE IF EXISTS phone_number; -- drop before client due to fk relationship
DROP TABLE IF EXISTS account; -- drop before client due to fk relationship
DROP TABLE IF EXISTS client;

-- create client table
CREATE TABLE client(
client_id INTEGER PRIMARY KEY AUTO_INCREMENT,
client_first_name VARCHAR(255) NOT NULL CHECK(LENGTH(client_first_name)> 0),
client_last_name VARCHAR(255) NOT NULL CHECK(LENGTH(client_last_name)> 0),
client_nickname VARCHAR(255)
);

INSERT INTO client (client_first_name, client_last_name, client_nickname)
VALUES 
('Arnold', 'Schwarzeneg', 'Terminator'),
('Linda', 'Hamilton', 'Sarah Connor'),
-- ('Michael', 'Biehn', 'Kyle Reese'),
('Edward', 'Furlong', 'John Connor'),
-- ('Earl', 'Boen', 'Dr. Siberman'),
-- ('Lance', 'Henriksen', 'Detective Hal Vukovich'),
-- ('Bill', 'Paxton', 'Punk Leader'),
-- ('Paul', 'Winfield', 'Lieutenant Ed Traxler'),
-- ('kristanna', 'Loken', 'Terminatrix'),
-- ('William', 'Wisher Jr.', 'Policeman #1'),
('Robert', 'Patrick', 'T-1000');

SELECT * FROM client;
SELECT * FROM project0.client;

CREATE TABLE account(
-- acct_id INTEGER PRIMARY KEY AUTO_INCREMENT,
-- acct_number VARCHAR(5) NOT NULL UNIQUE,  -- CHECK(LENGTH(acct_number) = 5),
acct_number VARCHAR(5) PRIMARY KEY NOT NULL,
acct_type VARCHAR(50) NOT NULL,
acct_balance DOUBLE NOT NULL,
client_id INTEGER NOT NULL,
CONSTRAINT `fk_accountnumber_client` 
	FOREIGN KEY (client_id) REFERENCES client (client_id)
);

-- initial data load
INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('12345', 'CHECKING', 10000.00, 1),
('23456', 'SAVINGS', 1000000.00, 1),
('34567', 'CHECKING', 500000.00, 2),
('45678', 'CHECKING', 2500.25, 3),
('56789', 'CHECKING', 10000.36, 4);

-- INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
-- VALUES 
-- (12345, 'CHECKING', 10000.00, 1);


SELECT * FROM account;
SELECT * FROM account WHERE acct_number = '34567';
SELECT * FROM project0.account WHERE acct_number = 34567;

DELETE FROM project0.account WHERE acct_number = 80000;
DELETE FROM project0.account WHERE acct_number = 80001;
SELECT * FROM project0.account WHERE client_id = 4;

-- requirement data load <= 2000 && >= 400
INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('90123', 'SAVINGS', 399.99, 1),
('01234', 'SAVINGS', 400.00, 1),
('10000', 'SAVINGS', 400.01, 1),
('10001', 'SAVINGS', 1000.00, 1),
('10002', 'SAVINGS', 1999.99, 1),
('10003', 'SAVINGS', 2000.00, 1),
('10004', 'SAVINGS', 2000.01, 1);

INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('00003', 'SAVINGS', 20000.00, 2),
('00004', 'SAVINGS', 20000.01, 2);

SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400.00 ORDER BY acct_balance;
SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance <= 2000.00 ORDER BY acct_balance;
SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400.00 AND acct_balance <= 2000.00 ORDER BY acct_balance;

SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400 AND acct_balance <= 2000 ORDER BY acct_balance


-- future consideration create account_type table to store the types
-- futrue consideration CREATE TABLE account_history table to record account transactions









CREATE TABLE phone_number(
phone_number_id INTEGER PRIMARY KEY AUTO_INCREMENT,
country_code CHAR(10) NOT NULL, -- formatted ex 1-345 Cayman Islands, 44-1534 Jersey
phone_number CHAR(10) NOT NULL, -- mandatory 10 CHAR no formatting
client_id INTEGER NOT NULL,
CONSTRAINT `fk_phonenumber_client` 
	FOREIGN KEY (client_id) REFERENCES client (client_id)
);

INSERT INTO phone_number (country_code, phone_number, client_id)
VALUES 
('1', '5551234567', 1),
('1', '9253213211', 1),
('1', '7194577890', 2),
('44', '5553456789', 3),
('1-345', '5558971234', 4);

SELECT * FROM phone_number;



-- future consideration create account_type table to store the types
-- futrue consideration CREATE TABLE account_history table to record account transactions





