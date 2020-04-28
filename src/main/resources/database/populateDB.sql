DELETE FROM user_roles;
DELETE FROM users;

DELETE FROM dishes;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD) VALUES
('User', 'user@yandex.ru', '{noop}password'),
('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES
  ('USER', 100000),
  ('ADMIN', 100001),
  ('USER', 100001);

-- 100_002
INSERT INTO DISHES (NAME, PRICE) VALUES
    ('Meat Soup', 50),
    ('Chicken Soup', 40),
    ('Salad with meat', 50),
    ('Vegetable salad', 40),
    ('Caesar', 50),
    ('BeefSteak', 150),
    ('Fried Chicken', 110),
    ('IceCream', 30),
    ('Juice', 20),
    ('Coffee', 15),
    ('Tee', 10)


-- INSERT INTO meals (date_time, description, calories, user_id)
-- VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 100000),
--        ('2020-01-30 13:00:00', 'Обед', 1000, 100000),
--        ('2020-01-30 20:00:00', 'Ужин', 500, 100000),
--        ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 100000),
--        ('2020-01-31 10:00:00', 'Завтрак', 500, 100000),
--        ('2020-01-31 13:00:00', 'Обед', 1000, 100000),
--        ('2020-01-31 20:00:00', 'Ужин', 510, 100000),
--        ('2020-01-31 14:00:00', 'Админ ланч', 510, 100001),
--        ('2020-01-31 21:00:00', 'Админ ужин', 1500, 100001);
