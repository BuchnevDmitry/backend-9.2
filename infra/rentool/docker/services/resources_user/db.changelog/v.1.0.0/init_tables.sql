--liquibase formatted sql;

create extension if not exists "uuid-ossp";


create table person
(
    id uuid primary key,
    login text not null,
    phone text not null,
    email text
);

