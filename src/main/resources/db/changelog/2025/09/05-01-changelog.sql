-- liquibase formatted sql

-- changeset knbdan:1757095709896-1
CREATE TABLE preregistration_user (tg_id BIGINT NOT NULL PRIMARY KEY);
-- changeset knbdan:1757095709896-2
ALTER TABLE users ADD COLUMN IF NOT EXISTS code VARCHAR(255);