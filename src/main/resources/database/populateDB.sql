DELETE
FROM VOTES;
DELETE
FROM menuItems;
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

-- ID 100_002 - 100_004
INSERT INTO RESTAURANTS (NAME)
VALUES ('Lucky Pizza'), -- 100_002
       ('Steak House'), -- 100_003
       ('Green Garden'); -- 100_004

-- ID 100_005 - 100_016
INSERT INTO DISHES (NAME, PRICE, RESTAURANT_ID)
VALUES ('Meat Soup', 50, 100002), -- 100_005
       ('Caesar', 50, 100002),
       ('BeefSteak', 150, 100002),
       ('Coffee', 15, 100002),

       ('Chicken Soup', 40, 100003),
       ('Salad with meat', 50, 100003),
       ('Fried Chicken', 110, 100003),
       ('Tee', 10, 100003),

       ('Chicken Soup', 40, 100004),  -- 100_013
       ('Vegetable salad', 40, 100004),
       ('IceCream', 30, 100004),
       ('Juice', 20, 100004);


-- ID 100_017 - 100_024
INSERT INTO menuItems (DATE, RESTAURANT_ID, DISH_ID)
VALUES ('2020-04-01', 100002, 100005),
       ('2020-04-01', 100002, 100006),
       ('2020-04-01', 100002, 100007),
       ('2020-04-01', 100002, 100008),

       ('2020-04-01', 100003, 100009),
       ('2020-04-01', 100003, 100010),
       ('2020-04-01', 100003, 100011),
       ('2020-04-01', 100003, 100012),

       ('2020-04-01', 100004, 100013),
       ('2020-04-01', 100004, 100014),
       ('2020-04-01', 100004, 100015),
       ('2020-04-01', 100004, 100016);

-- ID 100_025 - 100_026
INSERT INTO VOTES (DATE, RESTAURANT_ID, USER_ID)
VALUES ('2020-04-01', '100002', '100000'),
       ('2020-04-01', '100002', '100001')



