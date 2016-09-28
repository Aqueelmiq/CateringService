CREATE TABLE IF NOT EXISTS MENU(

  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  price double,
  min_qty int

);

CREATE TABLE IF NOT EXISTS CATEGORIES(

  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  food_id int,
  FOREIGN KEY (food_id) REFERENCES MENU(id)

);

CREATE TABLE IF NOT EXISTS EXTRAS(

  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  amount double

);


CREATE TABLE IF NOT EXISTS CUSTOMERS(

  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  phone VARCHAR,
  email VARCHAR

);

CREATE TABLE IF NOT EXISTS ORDERS(

  id int PRIMARY KEY auto_increment,
  customer_id int,
  note VARCHAR,
  surcharge double,
  status VARCHAR,
  order_date VARCHAR,
  delivery_date VARCHAR,
  delivery_address VARCHAR,
  amount double

);

CREATE TABLE IF NOT EXISTS ITEMS(

  id int PRIMARY KEY auto_increment,
  order_id int,
  food_id int,
  food VARCHAR,
  qty int,
  amount double,
  FOREIGN KEY (order_id) REFERENCES ORDERS(id)

);

INSERT INTO EXTRAS (name, amount) VALUES ('surcharge', 0);

