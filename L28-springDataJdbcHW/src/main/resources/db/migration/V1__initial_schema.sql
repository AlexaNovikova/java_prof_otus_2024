
-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);


create table address
(
    id   bigserial not null primary key,
    client_id bigint,
    street varchar(50),
    FOREIGN KEY (client_id) REFERENCES client (id)
);


create table phone
(
    id   bigserial not null primary key,
    number varchar(50),
    client_id bigint,
    FOREIGN KEY (client_id) REFERENCES client (id)
);

create table users
(
    id   bigserial not null primary key,
    name varchar(50),
    login varchar(50),
    password varchar(50)
);


