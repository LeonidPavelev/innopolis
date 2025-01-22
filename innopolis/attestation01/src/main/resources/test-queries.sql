--READ
--Выбор всех пользователей:
SELECT * FROM users;
--Выбор всех товаров:
SELECT * FROM products;
--Выбор всех заказов с информацией о пользователе:
SELECT orders.order_id, users.first_name, users.last_name, orders.order_date, orders.total_amount
FROM orders
         JOIN users ON orders.user_id = users.user_id;
--Суммарное количество позиций в заказах для каждого пользователя:
SELECT users.first_name, users.last_name, SUM(order_items.quantity) AS total_quantity
FROM order_items
         JOIN orders ON order_items.order_id = orders.order_id
         JOIN users ON orders.user_id = users.user_id
GROUP BY users.user_id;
--Общая сумма всех заказов:
SELECT SUM(total_amount) AS total_sales FROM orders;
--Получение пользователей, у которых есть заказы:
SELECT DISTINCT users.first_name, users.last_name
FROM users
         JOIN orders ON users.user_id = orders.user_id;

--Создание индекса для быстрого поиска по имени пользователей:
CREATE INDEX idx_user_name ON users (first_name, last_name);
--UPDATE
--Обновление цены продукта:
UPDATE products SET price = 19.99 WHERE product_id = 1;
--Обновление количества на складе после продажи товара:
UPDATE products SET stock = stock - 5 WHERE product_id = 1;
--Обновление информации о пользователе:
UPDATE users SET last_name = 'Павельев' WHERE user_id = 1;
--Обновление описания продукта:
UPDATE products SET description = 'Updated Description' WHERE product_id = 1;
--DELETE
--Удаление пользователя по ID:
DELETE FROM users WHERE user_id = 1;
--Удаление товара по ID:
DELETE FROM products WHERE product_id = 1;
--Удаление всех заказов пользователя:
DELETE FROM orders WHERE user_id = 1;
--Удаление пользователей, не сделавших никаких заказов:
DELETE FROM users WHERE user_id NOT IN (SELECT DISTINCT user_id FROM orders);


