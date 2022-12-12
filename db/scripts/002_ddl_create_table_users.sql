CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name varchar NOT NULL,
   email varchar NOT NULL UNIQUE,
   password varchar NOT NULL
);

comment on table users is 'Пользователи';
comment on column users.id is 'Идентификатор пользователя';
comment on column users.name is 'Имя пользователя';
comment on column users.email is 'Электронная почта пользователя';
comment on column users.password is 'Пароль пользователя';