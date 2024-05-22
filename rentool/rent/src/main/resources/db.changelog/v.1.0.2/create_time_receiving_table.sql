create table time_receiving
(
    id bigint generated always as identity,
    name text not null,
    description text not null,
    primary key (id)
);
