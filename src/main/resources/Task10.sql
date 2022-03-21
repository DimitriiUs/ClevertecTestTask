CREATE SCHEMA task10;
SET SCHEMA 'task10';

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    price MONEY,
    discount BOOLEAN
);

CREATE TABLE discount_cards (
    discount_card_id INT PRIMARY KEY,
    discount INT,
    product_quantity_for_discount INT
);

INSERT INTO products(title,price,discount) VALUES ('Hydroquinone',46.65,'yes');
INSERT INTO products(title,price,discount) VALUES ('Castor Oil',31.5,'yes');
INSERT INTO products(title,price,discount) VALUES ('Loperamide HCl',31.36,'no');
INSERT INTO products(title,price,discount) VALUES ('Avobenzone',61.58,'yes');
INSERT INTO products(title,price,discount) VALUES ('Arsenicum album',56.47,'yes');
INSERT INTO products(title,price,discount) VALUES ('Herring',46.18,'no');
INSERT INTO products(title,price,discount) VALUES ('Captopril',21.06,'yes');
INSERT INTO products(title,price,discount) VALUES ('Triclosan',38.18,'no');
INSERT INTO products(title,price,discount) VALUES ('Dextromethorphan',31.84,'yes');
INSERT INTO products(title,price,discount) VALUES ('Quartz 8',26.25,'yes');
INSERT INTO products(title,price,discount) VALUES ('Furosemide',42.54,'no');
INSERT INTO products(title,price,discount) VALUES ('Ibuprofen',10.55,'no');
INSERT INTO products(title,price,discount) VALUES ('Carvedilol',18.91,'no');
INSERT INTO products(title,price,discount) VALUES ('Mupirocin',12.05,'yes');
INSERT INTO products(title,price,discount) VALUES ('Lubiprostone',1.42,'yes');
INSERT INTO products(title,price,discount) VALUES ('Salicylic Acid',38.24,'yes');
INSERT INTO products(title,price,discount) VALUES ('Orange',2.35,'yes');
INSERT INTO products(title,price,discount) VALUES ('Egg',1.76,'no');
INSERT INTO products(title,price,discount) VALUES ('Broccoli',8.24,'no');
INSERT INTO products(title,price,discount) VALUES ('Blueberry',2.49,'no');

INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (976,3,5);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (166,2,5);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (348,1,1);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (368,4,1);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (637,5,2);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (112,6,3);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (667,1,2);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (977,3,5);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (991,3,3);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (576,3,6);
INSERT INTO discount_cards(DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (431,9,10);

SELECT current_database();