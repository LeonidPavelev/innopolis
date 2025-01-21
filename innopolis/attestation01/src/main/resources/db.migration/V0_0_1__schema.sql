--создание таблицы "Пользователи"
CREATE TABLE IF NOT EXISTS users (
    user_id bigserial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
--создание таблицы "Продукты"
CREATE TABLE IF NOT EXISTS products (
    product_id bigserial PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);
--создание таблицы "Заказы"
CREATE TABLE IF NOT EXISTS orders (
    order_id bigserial PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
--создание таблицы "Товары в заказах"
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id bigserial PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

