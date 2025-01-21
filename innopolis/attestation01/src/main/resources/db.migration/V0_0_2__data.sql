INSERT INTO users (first_name, last_name) VALUES
('Алексей', 'Прохоров'),
('Андрей', 'Копов'),
('Алина', 'Сырова'),
('Леонид', 'Быстров'),
('Павел', 'Кириллов'),
('Аркадий', 'Чумаков'),
('Олег', 'Старакожев'),
('Антон', 'Шастун'),
('Наташа', 'Бякова'),
('Денис', 'Пальцев'),
('Николай', 'Чкалов'),
('Юлия', 'Стулова');


INSERT INTO products (product_name, description, price, stock) VALUES ('iphone 15', 'Latest model smartphone with advanced features.', 699.99, 50),
('Laptop acer', 'High-performance laptop for gaming and work.', 1299.99, 30),
('Tablet Lenovo', 'Portable tablet with a sleek design.', 399.99, 20),
('aplle watch 8', 'Stylish smartwatch with fitness tracking.', 199.99, 100),
('Headphones JBL', 'Noise-cancelling over-ear headphones.', 149.99, 75),
('Camera dexp', 'DSLR camera for professional photography.', 899.99, 15),
('Speaker HyperX', 'Bluetooth speaker with high sound quality.', 99.99, 60),
('Monitor LG', '27-inch monitor with 4K resolution.', 399.99, 25),
('Keyboard logitech', 'Mechanical keyboard with customizable keys.', 89.99, 40),
('Mouse logitech', 'Wireless mouse with ergonomic design.', 49.99, 80),
('Router TP-link archer', 'High-speed router for seamless internet connectivity.', 129.99, 35),
('Charger dexp', 'Fast charger for smartphones and tablets.', 29.99, 150);

INSERT INTO orders (user_id, total_amount) VALUES
(1, 899.98),
(1, 199.99),
(2, 1299.99),
(3, 399.99),
(3, 149.99),
(4, 699.99),
(5, 499.98),
(6, 1299.99),
(7, 199.99),
(8, 149.99),
(9, 399.99),
(10, 89.99);

INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 699.99),   -- Алексей Прохоров buys iphone 15
(1, 5, 2, 149.99),   -- Алексей Прохоров buys Headphones JBL
(2, 2, 1, 1299.99),  -- 'Андрей', 'Копов' buys Laptop
(3, 3, 1, 399.99),   -- Алина', 'Сырова' buys Tablet
(3, 5, 1, 149.99),   -- Алина', 'Сырова' buys Headphones
(4, 1, 1, 699.99),   -- Леонид', 'Быстров' buys Smartphone
(5, 4, 1, 399.99),   -- 'Павел', 'Кириллов' buys Tablet
(6, 2, 1, 1299.99),  -- 'Аркадий', 'Чумаков' buys Laptop
(7, 4, 1, 199.99),   -- 'Олег', 'Старакожев' buys Smartwatch
(8, 5, 1, 149.99),   -- 'Антон', 'Шастун' buys Headphones
(9, 6, 1, 899.99),   -- 'Наташа', 'Бякова' buys Camera
(10, 10, 1, 49.99);   -- 'Денис', 'Пальцев' buys Mouse