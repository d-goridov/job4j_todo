CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);

comment on table tasks is 'Задания';
comment on column tasks.id is 'Идентификатор задания';
comment on column tasks.description is 'Описание задания';
comment on column tasks.created is 'Время и дата создания задания';
comment on column tasks.done is 'Статус выполнения задания';