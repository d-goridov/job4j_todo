CREATE TABLE IF NOT EXISTS categories(
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

comment on table categories is 'Категории заданий';
comment on column categories.id is 'Идентификатор категории';
comment on column categories.name is 'Название категории';