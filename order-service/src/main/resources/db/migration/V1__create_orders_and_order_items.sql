-- V1__create_orders_and_order_items.sql

-- Tạo bảng orders
CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  customer_name VARCHAR(255) NOT NULL,
  order_date TIMESTAMP NOT NULL DEFAULT now(),
  total_amount NUMERIC(12,2),
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
  email VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT now(),
  updated_at TIMESTAMP
);

-- Tạo bảng order_items
CREATE TABLE order_items (
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  product_name VARCHAR(255) NOT NULL,
  quantity INTEGER NOT NULL,
  price NUMERIC(10,2) NOT NULL,
  total_price NUMERIC(12,2) NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);
