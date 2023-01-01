CREATE TABLE IF NOT EXISTS tasks_categories(
    task_id int REFERENCES tasks(id),
    category_id int REFERENCES categories(id)
);

comment on table tasks_categories is 'Задания по категориям';
comment on column tasks_categories.task_id is 'Идентификатор задания';
comment on column tasks_categories.category_id is 'Идентификатор категории';