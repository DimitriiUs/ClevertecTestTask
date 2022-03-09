CREATE SCHEMA task9_Lapitski;
SET SCHEMA 'task9_Lapitski';

CREATE TABLE product_categories(
    category_id SERIAL PRIMARY KEY,
    category VARCHAR(50) NOT NULL
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    price MONEY,
    category_id INT REFERENCES product_categories(category_id)
);

CREATE TABLE receipts (
    id SERIAL PRIMARY KEY,
    order_id INT,
    product_id INT REFERENCES products(product_id),
    quantity INT,
    date_receipt TIMESTAMP DEFAULT current_timestamp
);

INSERT INTO product_categories(category) VALUES ('Foods');
INSERT INTO product_categories(category) VALUES ('Drugs');
INSERT INTO product_categories(category) VALUES ('Clothes');

INSERT INTO products(title,price,category_id) VALUES ('Hydroquinone',46.65,2);
INSERT INTO products(title,price,category_id) VALUES ('Castor Oil',31.5,2);
INSERT INTO products(title,price,category_id) VALUES ('Loperamide HCl',31.36,2);
INSERT INTO products(title,price,category_id) VALUES ('Avobenzone',61.58,2);
INSERT INTO products(title,price,category_id) VALUES ('Arsenicum album',56.47,2);
INSERT INTO products(title,price,category_id) VALUES ('Herring',46.18,2);
INSERT INTO products(title,price,category_id) VALUES ('Captopril',21.06,2);
INSERT INTO products(title,price,category_id) VALUES ('Triclosan',38.18,2);
INSERT INTO products(title,price,category_id) VALUES ('Dextromethorphan',31.84,2);
INSERT INTO products(title,price,category_id) VALUES ('Quartz 8',26.25,2);
INSERT INTO products(title,price,category_id) VALUES ('Furosemide',42.54,2);
INSERT INTO products(title,price,category_id) VALUES ('Ibuprofen',10.55,2);
INSERT INTO products(title,price,category_id) VALUES ('Carvedilol',18.91,2);
INSERT INTO products(title,price,category_id) VALUES ('Mupirocin',12.05,2);
INSERT INTO products(title,price,category_id) VALUES ('Lubiprostone',1.42,2);
INSERT INTO products(title,price,category_id) VALUES ('Salicylic Acid',38.24,2);
INSERT INTO products(title,price,category_id) VALUES ('Orange',2.35,1);
INSERT INTO products(title,price,category_id) VALUES ('Egg',1.76,1);
INSERT INTO products(title,price,category_id) VALUES ('Broccoli',8.24,1);
INSERT INTO products(title,price,category_id) VALUES ('Blueberry',2.49,1);

INSERT INTO receipts(order_id,product_id,quantity) VALUES (1,3,5);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (1,4,2);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (1,5,1);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (2,7,8);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (2,9,3);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (2,11,1);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (2,1,8);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (3,19,2);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (3,18,7);
INSERT INTO receipts(order_id,product_id,quantity) VALUES (3,17,10);

SELECT product_id,title,price,category FROM products p, product_categories c WHERE p.category_id=c.category_id AND title LIKE 'A%'
SELECT product_id,title,price,category FROM products NATURAL INNER JOIN product_categories WHERE title LIKE 'A%';

SELECT order_id,title,price,quantity,date_receipt FROM receipts NATURAL INNER JOIN products
WHERE price::NUMERIC IN (SELECT price::NUMERIC FROM products WHERE price::NUMERIC > 20.0);

SELECT order_id,string_agg(title, ', '),sum(price) FROM receipts NATURAL INNER JOIN products
GROUP BY order_id HAVING count(order_id) > 3;

SELECT * FROM receipts INNER JOIN products ON receipts.product_id = products.product_id
INNER JOIN product_categories ON products.category_id = product_categories.category_id;

SELECT * FROM receipts INNER JOIN products ON receipts.product_id = products.product_id
RIGHT JOIN product_categories ON products.category_id = product_categories.category_id;
