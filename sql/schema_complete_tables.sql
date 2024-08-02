drop table if exists product;
drop table if exists category;

insert into category(name)
values ('Processors'), ('Monitors');

insert into product(category_id, name, price, visibility)
values (1, 'Ryzen-Threadripper-PRO-7995WX', 10000, true),
       (1, 'Ryzen-Threadripper-7980X', 9000, true),
       (1, 'EPYC-9654', 8000, true),
       (2, 'Asus ROG Strix XG43UQ', 1729, true),
       (2, 'Samsung Odyssey G9', 1593, true),
       (2, 'Asus TUF Gaming VG28UQL1A', 858, true),
       (2, 'Dell UltraSharp U3425WE', 1148, true);

insert into characteristic(category_id, name)
values (4, 'Количество ядер'),
       (4, 'Количество потоков'),
       (4, 'Базовая частота (ГГц)'),
       (4,'Дата выхода'),
       (5, 'Разрешение'),
       (5, 'Размер экрана');

insert into characteristic_description (characteristic_id, product_id, description)
values (1, 8, '96'),
       (2, 8, '192'),
       (3, 8, '2.5'),
       (4, 8, '2023-10-19');


insert into "user" (role, login, password, first_name, last_name, sign_up_date)
values (1, 'user984', 'greatest_programmer', 'John', 'Norton', '2024-07-29 12:53:00');

insert into review (user_id, product_id, published, rating, commentary, review_date)
values (1, 8, true, 4, 'Ryzen Threadripper PRO 7995WX is 42.4x faster than Core i5-7300U. It can run Fortnite at ' ||
                       'recommended settings according to Epic Games.', '2024-07-29 14:25:00');


