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


