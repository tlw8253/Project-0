-- *************************************************************************************
-- Project 0 script
--
/*
 * Project 0 script
 */

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


