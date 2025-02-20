CREATE TABLE IF NOT EXISTS orders (
id bigInt PRIMARY KEY,
product_id VARCHAR(100),
quantity int NOT NULL,
amount DECIMAL(10, 2) NOT NULL,
order_date TIMESTAMP);

COMMENT ON TABLE orders IS 'Таблица заказов.';
COMMENT ON COLUMN orders.id IS 'Уникальный идентификатор заказа.';
COMMENT ON COLUMN orders.product_id IS 'Артикул товара.';
COMMENT ON COLUMN orders.quantity IS 'Количество товара.';
COMMENT ON COLUMN orders.amount IS 'Сумма заказа.';
COMMENT ON COLUMN orders.order_date IS 'Дата и время заказа.';

