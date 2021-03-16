CREATE TABLE IF NOT EXISTS customer (
	id VARCHAR(100) PRIMARY KEY,
	firstname VARCHAR(100),
	lastname VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS address (
	street VARCHAR(100),
	city VARCHAR(100),
	zip_code VARCHAR(100),
	customer VARCHAR(100),
	customer_key VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS my_order (
	id VARCHAR(100),
	customer VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS line_item (
	my_order_key VARCHAR(100),
	description VARCHAR(100),
	foo VARCHAR(100)
);
	
// CREATE TABLE IF NOT EXISTS Lego_Set (id INTEGER, name VARCHAR(100), min_Age INTEGER, max_Age INTEGER);
// CREATE TABLE IF NOT EXISTS Handbuch (handbuch_id INTEGER, author VARCHAR(100), text CLOB);
// CREATE TABLE IF NOT EXISTS Model (name VARCHAR(100), description CLOB, lego_set INTEGER);
