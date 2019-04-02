DROP DATABASE IF EXISTS gui_exam;
CREATE DATABASE IF NOT EXISTS gui_exam;

CREATE TABLE users (
  customer_id INT(9) PRIMARY KEY,
  customer_name VARCHAR(100),
  state VARCHAR(2),
  is_retail SMALLINT(1)
);