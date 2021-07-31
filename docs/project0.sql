-- Project 0 script
--
/*
 * Project 0 script
 */

-- CREATE USER 'project0'@localhost IDENTIFIED BY 'project0';

-- drop table if it exists
DROP TABLE IF EXISTS phone_number; -- drop before client due to fk relationship
DROP TABLE IF EXISTS account; -- drop before client due to fk relationship
DROP TABLE IF EXISTS client;

-- create client table
CREATE TABLE client(
client_id INTEGER PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL CHECK(LENGTH(first_name)> 0),
last_name VARCHAR(255) NOT NULL CHECK(LENGTH(last_name)> 0)
);

INSERT INTO client (first_name, last_name)
VALUES 
('Arnold', 'Schwarzeneg'),
('Linda', 'Hamilton'),
-- ('Michael', 'Biehn'),
('Edward', 'Furlong'),
-- ('Earl', 'Boen'),
-- ('Gale', 'Anne'),
-- ('Lance', 'Henriksen'),
-- ('Bill', 'Paxton'),
-- ('Paul', 'Winfield'),
-- ('kristanna', 'Loken'),
-- ('William', 'Wisher Jr.'),
('Robert', 'Patrick');

SELECT * FROM client;

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

CREATE TABLE account(
acct_id INTEGER PRIMARY KEY AUTO_INCREMENT,
acct_number DOUBLE NOT NULL UNIQUE,
acct_balance DOUBLE NOT NULL,
client_id INTEGER NOT NULL,
CONSTRAINT `fk_accountnumber_client` 
	FOREIGN KEY (client_id) REFERENCES client (client_id)
);

INSERT INTO account (acct_number, acct_balance, client_id)
VALUES 
(1234567890, 10000, 1),
(1234567891, 1000000, 1),
(1234567892, 500000, 2),
(1234567893, 2500, 3),
(1234567894, 10000, 4);

SELECT * FROM account;


CALL project0.get_clients;

-- futrue consideration an CREATE TABLE account_history table to record account transactions





