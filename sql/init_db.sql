CREATE DATABASE IF NOT EXISTS btg;
USE btg;

CREATE TABLE IF NOT EXISTS customer (
  id BIGINT PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT PRIMARY KEY,
  customer_id BIGINT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(15,2),
  FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS order_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT,
  product VARCHAR(255),
  quantity INT,
  price DECIMAL(15,2),
  line_total DECIMAL(15,2),
  FOREIGN KEY (order_id) REFERENCES orders(id)
);