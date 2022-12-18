ALTER TABLE tasks ADD COLUMN user_id int NOT NULL;
ALTER TABLE tasks ADD CONSTRAINT fk_user FOREIGN KEY
(user_id) REFERENCES users(id);

COMMENT ON COLUMN tasks.user_id is 'Идентификатор пользователя';
COMMENT ON CONSTRAINT fk_user ON tasks is 'Идентификатор пользователя';