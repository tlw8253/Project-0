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

CREATE TABLE account( -- bring back auto increment to use as bases for account number
					  -- if there was time, could have save last account number in a file and 
					  -- increment for next key, could consider using return (int) ((Math.random() * (max - min)) + min); for now
-- acct_id INTEGER PRIMARY KEY AUTO_INCREMENT,
-- acct_number VARCHAR(5) NOT NULL UNIQUE,  -- CHECK(LENGTH(acct_number) = 5),
-- acct_number VARCHAR(5) NOT NULL UNIQUE,
acct_number VARCHAR(5) PRIMARY key NOT NULL, -- for the MVP program will not generate this key
acct_type VARCHAR(50) NOT NULL,
acct_balance DOUBLE NOT NULL,
client_id INTEGER NOT NULL,
CONSTRAINT `fk_accountnumber_client` 
	FOREIGN KEY (client_id) REFERENCES client (client_id)
);

-- initial data load
INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('00001', 'CHECKING', 10000.00, 1),
('00002', 'SAVINGS', 1000000.00, 1),
('00003', 'CHECKING', 500000.00, 2),
('00004', 'CHECKING', 2500.25, 3),
('00005', 'CHECKING', 10000.36, 4);

-- INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
-- VALUES 
-- (12345, 'CHECKING', 10000.00, 1);


SELECT * FROM account;
SELECT * FROM account WHERE client_id = 4 ORDER BY acct_number;
SELECT * FROM account WHERE acct_number = '34567';
SELECT * FROM project0.account WHERE acct_number = 34567;

DELETE FROM project0.account WHERE acct_number = 80000;
DELETE FROM project0.account WHERE acct_number = 80001;
SELECT * FROM project0.account WHERE client_id = 4;

-- requirement data load <= 2000 && >= 400
INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('00006', 'SAVINGS', 399.99, 1),
('00007', 'SAVINGS', 400.00, 1),
('00008', 'SAVINGS', 400.01, 1),
('00009', 'SAVINGS', 1000.00, 1),
('00010', 'SAVINGS', 1999.99, 1),
('00011', 'SAVINGS', 2000.00, 1),
('00012', 'SAVINGS', 2000.01, 1);

-- requirement data load to delete an account for a client
INSERT INTO account (acct_number, acct_type, acct_balance, client_id)
VALUES 
('00013', 'SAVINGS', 20000.00, 2),
('00014', 'SAVINGS', 20000.01, 2);

SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400.00 ORDER BY acct_balance;
SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance <= 2000.00 ORDER BY acct_balance;
SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400.00 AND acct_balance <= 2000.00 ORDER BY acct_balance;

SELECT * FROM project0.account WHERE client_id = 1 AND acct_balance >= 400 AND acct_balance <= 2000 ORDER BY acct_balance

UPDATE client SET client_first_name = 'Robert', client_last_name = 'Patrick', client_nickname = 'T-1000' WHERE client_id = 4;
SELECT * FROM client;
SELECT * FROM account;
SELECT * FROM account WHERE client_id = 4 ORDER BY acct_number;

SELECT * FROM project0.account WHERE client_id = 1  AND acct_balance >= 5  AND acct_balance <= 20  ORDER BY acct_balance

-- future consideration create account_type table to store the types
-- futrue consideration CREATE TABLE account_history table to record account transactions





