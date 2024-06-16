create table advertising
(
    id bigint generated always as identity,
    name text,
    image_url text not null
);