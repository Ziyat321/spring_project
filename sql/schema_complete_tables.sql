drop table if exists product;
drop table if exists category;

insert into category(name)
values ('Processors'), ('Monitors');

insert into product(category_id, name, price, visibility)
values (4, 'Ryzen-Threadripper-PRO-7995WX', 10000, true),
       (4, 'Ryzen-Threadripper-7980X', 9000, true),
       (4, 'EPYC-9654', 8000, true),
       (5, 'Asus ROG Strix XG43UQ', 1729, true),
       (5, 'Samsung Odyssey G9', 1593, true),
       (5, 'Asus TUF Gaming VG28UQL1A', 858, true),
       (5, 'Dell UltraSharp U3425WE', 1148, true);
