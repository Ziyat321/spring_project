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
values (1, 'Количество ядер'),
       (1, 'Количество потоков'),
       (1, 'Базовая частота (ГГц)'),
       (1,'Дата выхода'),
       (2, 'Разрешение'),
       (2, 'Размер экрана');