create table category
(
    id   serial8,
    name varchar not null,
    primary key (id)
);

create table characteristics
(
    id          serial8,
    category_id int8    not null,
    name        varchar not null,
    primary key (id),
    foreign key (category_id) references category (id)
);


create table product
(
    id          serial8,
    category_id int8,
    name        varchar not null,
    visibility  boolean not null,
    primary key (id),
    foreign key (category_id) references category (id)
);

create table characteristics_descriptions
(
    id                 serial8,
    characteristics_id int8,
    product_id         int8,
    description        varchar not null,
    primary key (id),
    foreign key (characteristics_id) references characteristics (id),
    foreign key (product_id) references product (id)
);

create table user
(
    id serial8,
    role int2 not null ,
    login varchar unique not null ,
    password varchar not null ,
    first_name varchar not null,
    last_name varchar not null,
    sign_up_date timestamp not null,
    primary key (id)
);

create table order(
  id serial8,
  user_id int8,
  status int2,
  order_date timestamp,
  primary key (id),
  foreign key (user_id) references user(id)
);

create table order_product(
    id serial8,
    order_id int8,
    product_id int8,
    foreign key (order_id) references order(id),
    foreign key (product_id) references product(id),
    primary key (id)
);
