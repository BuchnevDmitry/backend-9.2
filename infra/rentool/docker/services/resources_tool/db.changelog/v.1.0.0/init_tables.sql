--liquibase formatted sql;

create extension if not exists "uuid-ossp";

create table brand
(
    id bigint generated always as identity,
    name text not null,
    primary key (id)
);

create table category
(
    id bigint generated always as identity,
    name text not null,
    primary key (id)
);

create table tool
(
    id uuid primary key default uuid_generate_v4(),
    model text not null,
    description text not null,
    image_url text not null,
    price_hour bigint not null,
    count bigint not null,
    brand_id bigint not null,
    category_id bigint not null,
    foreign key (brand_id) references brand (id),
    foreign key (category_id) references category (id)
);

