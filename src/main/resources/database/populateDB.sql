DELETE
FROM VOTES;
DELETE
FROM MENUS;
DELETE
FROM RESTAURANTS;
DELETE
FROM DISHES;
DELETE
FROM USER_ROLES;
DELETE
FROM USERS;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

-- ID 100_002 - 100_012
INSERT INTO DISHES (NAME, PRICE)
VALUES ('Meat Soup', 50), --100_002
       ('Chicken Soup', 40),
       ('Salad with meat', 50),
       ('Vegetable salad', 40),
       ('Caesar', 50),
       ('BeefSteak', 150),
       ('Fried Chicken', 110),
       ('IceCream', 30),
       ('Juice', 20),
       ('Coffee', 15),
       ('Tee', 10);

-- ID 100_013 - 100_015
INSERT INTO RESTAURANTS (NAME)
VALUES ('Lucky Pizza'),
       ('Steak House'),
       ('Green Garden');

-- ID 100_016 - 100_024
INSERT INTO MENUS (DATE, RESTAURANT_ID, DISH_ID)
VALUES ('2020-04-01', '100013', '100002'),
       ('2020-04-01', '100013', '100004'),
       ('2020-04-01', '100013', '100012'),
       ('2020-04-01', '100014', '100003'),
       ('2020-04-01', '100014', '100005'),
       ('2020-04-01', '100014', '100011'),
       ('2020-04-01', '100015', '100006'),
       ('2020-04-01', '100015', '100007'),
       ('2020-04-01', '100015', '100010');

-- ID 100_025 - 100_026
INSERT INTO VOTES (DATE, RESTAURANT_ID, USER_ID)
VALUES ('2020-04-01', '100013', '100000'),
       ('2020-04-01', '100013', '100001')



