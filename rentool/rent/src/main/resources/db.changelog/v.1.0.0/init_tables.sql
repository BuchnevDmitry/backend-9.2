--liquibase formatted sql;

create extension if not exists "uuid-ossp";

create table status
(
    id bigint generated always as identity,
    name text not null,
    description text not null,
    primary key (id)
);

create table receiving_method
(
    id bigint generated always as identity,
    name text not null,
    description text not null,
    primary key (id)
);

create table rent
(
    id uuid primary key default uuid_generate_v4(),
    start_date timestamp with time zone not null,
    end_date timestamp with time zone not null,
    price bigint not null,
    user_id uuid not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    status_id bigint not null,
    receiving_method_id bigint not null,
    address text not null,
    foreign key (status_id) references status (id),
    foreign key (receiving_method_id) references receiving_method (id)
);

create table rent_tool
(
    tool_id uuid not null,
    rent_id uuid not null,
    count_tool bigint not null,
    foreign key (rent_id) references rent (id),
    primary key (tool_id, rent_id)
);
