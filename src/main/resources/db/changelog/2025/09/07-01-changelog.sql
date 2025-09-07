-- liquibase formatted sql

-- changeset knbdan:1757256459000-1
CREATE SEQUENCE IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 50;
-- changeset knbdan:1757256459000-2
CREATE TABLE task (id BIGINT NOT NULL, name VARCHAR(255) NOT NULL, is_available BOOLEAN, description VARCHAR(255) NOT NULL, points INTEGER NOT NULL, CONSTRAINT pk_task PRIMARY KEY (id));
-- changeset knbdan:1757256459000-3
ALTER TABLE activity ADD COLUMN points INTEGER NOT NULL;
-- changeset knbdan:1757256459000-4
ALTER TABLE activity ADD COLUMN key_word VARCHAR(255);
-- changeset knbdan:1757256459000-5
CREATE SEQUENCE IF NOT EXISTS user_task_completions_seq START WITH 1 INCREMENT BY 50;
-- changeset knbdan:1757256459000-6
CREATE TABLE user_task_completions (id BIGINT NOT NULL, user_id BIGINT NOT NULL, task_id BIGINT NOT NULL, time_taken TIME WITHOUT TIME ZONE NOT NULL, CONSTRAINT pk_user_task_completions PRIMARY KEY (id));


