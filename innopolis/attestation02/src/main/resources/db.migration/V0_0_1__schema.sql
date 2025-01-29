CREATE TABLE IF NOT EXISTS users (
user_id bigserial PRIMARY KEY,                             -- Уникальный идентификатор пользователя (PRIMARY KEY)
first_name VARCHAR(50) NOT NULL,                           -- Имя пользователя (обязательное поле)
last_name VARCHAR(50) NOT NULL,                            -- Фамилия пользователя (обязательное поле)
created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP           -- Дата и время создания записи (по умолчанию текущая дата и время)
);

COMMENT ON TABLE users IS 'Таблица пользователей (покупатели)';
COMMENT ON COLUMN users.user_id IS 'Уникальный идентификатор пользователя.';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя.';
COMMENT ON COLUMN users.last_name IS 'Фамилия пользователя.';
COMMENT ON COLUMN users.created_at IS 'Дата и время создания записи.';

CREATE TABLE IF NOT EXISTS products (
product_id bigserial PRIMARY KEY,                          -- Уникальный идентификатор продукта (PRIMARY KEY)
product_name VARCHAR(100) NOT NULL,                        -- Название продукта (обязательное поле)
description TEXT,                                          -- Описание продукта (необязательное поле)
price DECIMAL(10, 2) NOT NULL,                             -- Цена продукта (обязательное поле, с двумя десятичными знаками)
stock INT NOT NULL                                         -- Количество на складе (обязательное поле)
);

COMMENT ON TABLE products IS 'Таблица продуктов.';
COMMENT ON COLUMN products.product_id IS 'Уникальный идентификатор продукта.';
COMMENT ON COLUMN products.product_name IS 'Название продукта.';
COMMENT ON COLUMN products.description IS 'Описание продукта.';
COMMENT ON COLUMN products.price IS 'Цена продукта.';
COMMENT ON COLUMN products.stock IS 'Количество продукта на складе.';

CREATE TABLE IF NOT EXISTS orders (
order_id bigserial PRIMARY KEY,                             -- Уникальный идентификатор заказа (PRIMARY KEY)
user_id bigint,                                             -- Идентификатор пользователя, который сделал заказ (внешний ключ)
order_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,           -- Дата и время заказа (по умолчанию текущая дата и время)
total_amount DECIMAL(10, 2) NOT NULL,                       -- Общая сумма заказа (обязательное поле)
FOREIGN KEY (user_id) REFERENCES users(user_id)             -- Ссылка на таблицу пользователей (user_id)
    );

COMMENT ON TABLE orders IS 'Таблица заказов.';
COMMENT ON COLUMN orders.order_id IS 'Уникальный идентификатор заказа.';
COMMENT ON COLUMN orders.user_id IS 'Идентификатор пользователя, который сделал заказ.';
COMMENT ON COLUMN orders.order_date IS 'Дата и время заказа.';
COMMENT ON COLUMN orders.total_amount IS 'Общая сумма заказа.';

CREATE TABLE IF NOT EXISTS order_items (
order_item_id bigserial PRIMARY KEY,                        -- Уникальный идентификатор товара в заказе (PRIMARY KEY)
order_id bigint,                                            -- Идентификатор заказа, к которому относится товар (внешний ключ)
product_id bigint,                                          -- Идентификатор продукта, который был в заказе (внешний ключ)
quantity INT NOT NULL,                                      -- Количество товара в заказе (обязательное поле)
price DECIMAL(10, 2) NOT NULL,                              -- Цена товара в момент заказа (обязательное поле)
FOREIGN KEY (order_id) REFERENCES orders(order_id),         -- Ссылка на таблицу заказов (order_id)
FOREIGN KEY (product_id) REFERENCES products(product_id)    -- Ссылка на таблицу продуктов (product_id)
);

COMMENT ON TABLE order_items IS 'Таблица товаров в заказах.';
COMMENT ON COLUMN order_items.order_item_id IS 'Уникальный идентификатор товара в заказе.';
COMMENT ON COLUMN order_items.order_id IS 'Идентификатор заказа, к которому относится товар.';
COMMENT ON COLUMN order_items.product_id IS 'Идентификатор продукта, который был в заказе.';
COMMENT ON COLUMN order_items.quantity IS 'Количество товара в заказе.';
COMMENT ON COLUMN order_items.price IS 'Цена товара в момент заказа.';