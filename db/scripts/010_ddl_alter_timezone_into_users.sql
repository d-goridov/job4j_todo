ALTER TABLE users ADD COLUMN user_zone VARCHAR;

COMMENT ON COLUMN users.user_zone is 'Часовой пояс пользователя';