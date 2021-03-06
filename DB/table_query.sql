DROP DATABASE IF EXISTS gui_lab4;
CREATE DATABASE IF NOT EXISTS gui_lab4;

CREATE TABLE make (
	make_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE model (
	model_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE make_model (
	make_name VARCHAR(50),
	model_name VARCHAR(50),
  PRIMARY KEY (make_name, model_name),
	FOREIGN KEY (make_name) REFERENCES make(make_name),
	FOREIGN KEY (model_name) REFERENCES model(model_name)
);

CREATE TABLE category (
	category_name VARCHAR(100) PRIMARY KEY
);

CREATE TABLE part (
	part_id INT PRIMARY KEY AUTO_INCREMENT,
	make_name VARCHAR(100) NOT NULL,
	model_name VARCHAR(100) NOT NULL,
	category_name VARCHAR(100) NOT NULL,
	unit_price decimal(15, 3) NOT NULL,
	part_year INT(4),
	part_name VARCHAR(100),
	FOREIGN KEY (make_name) REFERENCES make(make_name),
	FOREIGN KEY (model_name) REFERENCES model(model_name),
	FOREIGN KEY (category_name) REFERENCES category(category_name)
);

-- Insert hiihued primary key davhtsan gsn aldaa garsan ued cart_info-iin quantity-iig n update hiine
CREATE TABLE carts (
	cart_id INT,
	part_id INT,
	quantity INT NOT NULL,
	PRIMARY KEY (cart_id, part_id),
	FOREIGN KEY (part_id) REFERENCES part(part_id)
);

CREATE TABLE customer_order (
	receipt_number INT PRIMARY KEY AUTO_INCREMENT,
	tax_rate DECIMAL(10,5),
	total_amount DECIMAL(15,2),
	cart_id INT,
	FOREIGN KEY (cart_id) REFERENCES carts(cart_id)
);
