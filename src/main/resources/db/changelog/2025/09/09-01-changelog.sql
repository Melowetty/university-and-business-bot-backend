-- liquibase formatted sql

-- changeset knbdan:1757429229000-1
ALTER TABLE task ADD COLUMN duration INTERVAL;
-- changeset knbdan:1757429229000-2
ALTER TABLE task ADD COLUMN type VARCHAR(31) NOT NULL;
-- changeset knbdan:1757429229000-3
ALTER TABLE task DROP COLUMN is_available;
-- changeset knbdan:1757429229000-4
ALTER TABLE task ADD COLUMN status VARCHAR(31) NOT NULL;