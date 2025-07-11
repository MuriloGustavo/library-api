create table users (
    id uuid  not null primary key,
    username varchar(20) not null unique,
    password varchar(200) not null,
    roles varchar[]
);

create table authors(
    id uuid not null primary key,
    name varchar(100) not null,
    birth_date date not null,
    nationality varchar(50) not null,
    registration_date timestamp,
    update_date timestamp,
    id_user uuid not null references users(id)
);

create table books (
    id uuid not null primary key,
    isbn varchar(20) unique not null,
    title varchar(150) not null,
    publication_date date not null,
    gender varchar(30) not null,
    price numeric(18,2),
    registration_date timestamp,
    update_date timestamp,
    id_user uuid not null references users(id),
    id_author uuid not null references authors(id),
    constraint chk_gender check (gender in ('FICTION', 'FANTASY', 'MYSTERY', 'ROMANCE', 'BIOGRAPHY', 'SCIENCE'))
);