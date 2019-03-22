DROP DATABASE IF EXISTS gui_lab4;
CREATE DATABASE IF NOT EXISTS gui_lab4;

CREATE TABLE make (
	make_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE model (
	model_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE category (
	category_name VARCHAR(100) PRIMARY KEY
);

CREATE TABLE part (
	part_id INT PRIMARY KEY AUTO_INCREMENT,
	make_name VARCHAR(100) NOT NULL,
	model_name VARCHAR(100) NOT NULL,
	category_name VARCHAR(100) NOT NULL,
	unit_price DECIMAL(15,2),
	part_year INT(4),
	part_name VARCHAR(100) UNIQUE,
	FOREIGN KEY (make_name) REFERENCES make(make_name),
	FOREIGN KEY (model_name) REFERENCES model(model_name),
	FOREIGN KEY (category_name) REFERENCES category(category_name)
);

CREATE TABLE order_detail (
	order_detail_id INT PRIMARY KEY AUTO_INCREMENT
);

-- Insert hiihued primary key davhtsan gsn aldaa garsan ued cart_info-iin quantity-iig n update hiine
CREATE TABLE cart_info (
	order_detail_id INT,
	part_id INT,
	quantity INT NOT NULL,
	PRIMARY KEY (order_detail_id, part_id),
	FOREIGN KEY (order_detail_id) REFERENCES order_detail(order_detail_id),
	FOREIGN KEY (part_id) REFERENCES part(part_id)
);

CREATE TABLE customer_order (
	receipt_number INT PRIMARY KEY AUTO_INCREMENT,
	tax_rate DECIMAL(3,3),
	tax_amount DECIMAL(15,2),
	order_detail_id INT,
	FOREIGN KEY (order_detail_id) REFERENCES order_detail(order_detail_id)
);

