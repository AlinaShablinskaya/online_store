CREATE SCHEMA IF NOT EXISTS online_store;

DROP TABLE IF EXISTS online_store.customer_address CASCADE;
DROP TABLE IF EXISTS online_store.users CASCADE;
DROP TABLE IF EXISTS online_store.product CASCADE;
DROP TABLE IF EXISTS online_store.catalog CASCADE;
DROP TABLE IF EXISTS online_store.order_item CASCADE;
DROP TABLE IF EXISTS online_store.cart CASCADE;
DROP TABLE IF EXISTS online_store.catalog_product CASCADE;
DROP TABLE IF EXISTS online_store.cart_order_item CASCADE;

CREATE TABLE online_store.customer_address
(
    id SERIAL,
    zipcode character varying(255) NOT NULL,
    country character varying(255) NOT NULL,
    street character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE online_store.users
(
    id SERIAL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(32),
    address_id integer,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES online_store.customer_address ON DELETE SET NULL
);

CREATE TABLE online_store.product
(
    id SERIAL,
    product_name character varying(255),
    brand character varying(255) NOT NULL,
    photo character varying(255) NOT NULL,
    price decimal NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE online_store.catalog
(
    id SERIAL,
    group_name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE online_store.cart
(
    id SERIAL,
    user_id integer,
    total_sum decimal,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES online_store.users ON DELETE SET NULL
);

CREATE TABLE online_store.order_item
(
    id SERIAL,
    amount integer NOT NULL,
    product_id integer,
    cart_id integer,
    total_price decimal,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES online_store.product ON DELETE SET NULL,
    FOREIGN KEY (cart_id) REFERENCES online_store.cart ON DELETE SET NULL
);

CREATE TABLE online_store.catalog_product
(
    catalog_id integer,
    product_id integer,
    FOREIGN KEY (catalog_id) REFERENCES  online_store.catalog ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES online_store.product ON DELETE CASCADE
);

INSERT INTO online_store.customer_address(zipcode, country, street)
VALUES ('231103', 'England', 'Oxford Street'),
       ('237703', 'Germany', 'Oxford Street'),
       ('236541', 'new zealand', 'Baldwin Street');

INSERT INTO online_store.users(first_name, last_name, email, password, role, address_id)
VALUES ('Lando', 'Calrissian', 'lando@gmail.com', 'Aa123456', 'ADMIN', 1),
       ('Han', 'Solo', 'han@gmail.com', 'Aa123456', 'USER', 2),
       ('Hector', 'Barbossa', 'barbossa@gmail.com', 'Aa123456', 'USER', 3);

INSERT INTO online_store.product(product_name, brand, photo, price)
VALUES ('Whiskey Jack Daniels', 'Jack Daniels', '/images/goods/jack.jpg', '30'),
       ('Beer Heineken Lager Beer', 'Heineken', '/images/goods/heineken.jpg', '5'),
       ('Rum Captain Morgan', 'Captain Morgan', '/images/goods/captain.jpg', '20');

INSERT INTO online_store.catalog(group_name)
VALUES ('Whiskey'),
       ('Rum'),
       ('Beer');

INSERT INTO online_store.cart(user_id, total_sum)
VALUES (2, 90),
       (2, 50),
       (3, 100);

INSERT INTO online_store.order_item(amount, product_id, cart_id, total_price)
VALUES (3, 1, null, 90),
       (10, 2, 2, 50),
       (5, 3, 3, 100);

INSERT INTO online_store.catalog_product(catalog_id, product_id)
VALUES (1, 1),
       (2, 3);
