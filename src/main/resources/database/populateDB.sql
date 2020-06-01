DELETE
FROM VOTES;
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
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('SecondUser', 'second_user@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

-- ID 100_003 - 100_005
INSERT INTO RESTAURANTS (NAME)
VALUES ('Lucky Pizza'), -- 100_003
       ('Steak House'), -- 100_004
       ('Green Garden'); -- 100_005

-- ID 100_006 - 100_017
INSERT INTO DISHES (NAME, PRICE, DATE, RESTAURANT_ID)
VALUES ('Meat Soup', 50, '2020-04-01', 100003), -- 100_006
       ('Caesar', 50, '2020-04-01', 100003),
       ('BeefSteak', 150, '2020-04-01', 100003),
       ('Coffee', 15, '2020-04-01', 100003),

       ('Chicken Soup', 40, '2020-04-01', 100004),
       ('Salad with meat', 50, '2020-04-01', 100004),
       ('Fried Chicken', 110, '2020-04-01', 100004),
       ('Tee', 10, '2020-04-01', 100004),

       ('Gazpacho', 30, '2020-04-01', 100005),  -- 100_014
       ('Vegetable salad', 40, '2020-04-01', 100005),
       ('IceCream', 30, '2020-04-01', 100005),
       ('Juice', 20, '2020-04-01', 100005); -- 100_017

-- ID 100_18 - 100_019
INSERT INTO VOTES (DATE, RESTAURANT_ID, USER_ID)
VALUES ('2020-04-01', '100003', '100000'),
       ('2020-04-01', '100003', '100001')



