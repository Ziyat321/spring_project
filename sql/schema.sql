create table category
(
    id   serial8,
    name varchar not null,
    primary key (id)
);

create table characteristic
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
    price       int4    not null,
    visibility  boolean not null,
    primary key (id),
    foreign key (category_id) references category (id)
);

create table characteristic_description
(
    id                serial8,
    characteristic_id int8,
    product_id        int8,
    description       varchar not null,
    primary key (id),
    foreign key (characteristic_id) references characteristic (id),
    foreign key (product_id) references product (id)
);

create table "user"
(
    id           serial8,
    role         int2           not null,
    login        varchar unique not null,
    password     varchar        not null,
    first_name   varchar        not null,
    last_name    varchar        not null,
    sign_up_date timestamp      not null,
    primary key (id)
);

create table "order"
(
    id         serial8,
    user_id    int8,
    status     int2,
    order_date timestamp,
    primary key (id),
    foreign key (user_id) references "user" (id)
);

create table order_product
(
    id         serial8,
    order_id   int8,
    product_id int8,
    amount     int2,
    foreign key (order_id) references "order" (id),
    foreign key (product_id) references product (id),
    primary key (id)
);

create table review
(
    id          serial8,
    user_id     int8      not null,
    product_id  int8      not null,
    published   boolean   not null,
    rating      int2 check (rating between 1 and 5) not null,
    commentary  varchar   not null,
    review_date timestamp not null,
    primary key (id),
    foreign key (user_id) references "user" (id),
    foreign key (product_id) references product (id)
);


create table cart
(
    id         serial8,
    user_id    int8 not null,
    product_id int8 not null,
    amount     int4 not null,
    primary key (id),
    foreign key (user_id) references "user" (id),
    foreign key (product_id) references product (id)
);

alter table review
add constraint unique_user_product unique (user_id, product_id);
