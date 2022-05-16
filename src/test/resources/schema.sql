CREATE SCHEMA IF NOT EXISTS online_store;

DROP TABLE IF EXISTS online_store.customer_address CASCADE;
DROP TABLE IF EXISTS online_store.user CASCADE;
DROP TABLE IF EXISTS online_store.role CASCADE;
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

CREATE TABLE online_store.role
(
    role_name character varying(255) NOT NULL,
    PRIMARY KEY (role_name)
);

CREATE TABLE online_store.user
(
    id SERIAL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role_name character varying(255),
    address_id integer,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES online_store.customer_address ON DELETE SET NULL,
    FOREIGN KEY (role_name) REFERENCES online_store.role ON DELETE SET NULL
);

CREATE TABLE online_store.product
(
    id SERIAL,
    product_name character varying(255) NOT NULL,
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

CREATE TABLE online_store.order_item
(
    id SERIAL,
    amount integer NOT NULL,
    product_id integer,
    total_price decimal,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES online_store.product ON DELETE SET NULL
);

CREATE TABLE online_store.cart
(
    id SERIAL,
    user_id integer,
    total_sum decimal,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES online_store.user ON DELETE SET NULL
);

CREATE TABLE online_store.catalog_product
(
    catalog_id BIGINT,
    product_id BIGINT,
    FOREIGN KEY (catalog_id) REFERENCES  online_store.catalog ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES online_store.product ON DELETE CASCADE
);

CREATE TABLE online_store.cart_order_item
(
    cart_id BIGINT,
    order_item_id BIGINT,
    FOREIGN KEY (cart_id) REFERENCES online_store.cart ON DELETE CASCADE,
    FOREIGN KEY (order_item_id) REFERENCES online_store.order_item ON DELETE CASCADE
);

INSERT INTO online_store.role(role_name) VALUES ('ADMIN'), ('USER');