ALTER TABLE tasks ADD COLUMN priority_id int NOT NULL;
ALTER TABLE tasks ADD CONSTRAINT fk_priority FOREIGN KEY
(priority_id) REFERENCES priorities(id);

COMMENT ON COLUMN tasks.priority_id is 'Идентификатор приоритета';