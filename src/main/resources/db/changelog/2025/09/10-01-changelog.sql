-- liquibase formatted sql

-- changeset denismalinin:1757497842000-1
DROP TABLE event;

-- changeset denismalinin:1757497842000-2
CREATE TABLE activity_event (
activity_id BIGINT NOT NULL,
name VARCHAR(255) NOT NULL,
description VARCHAR(1023) NOT NULL,
duration BIGINT,
status VARCHAR(31) NOT NULL,
answers TEXT NOT NULL,
right_answer VARCHAR(255),
reward INT NOT NULL,
CONSTRAINT pk_activity_event PRIMARY KEY (activity_id));

-- changeset denismalinin:1757497842000-3
ALTER TABLE activity DROP COLUMN event_id;

-- changeset denismalinin:1757497842000-4
ALTER TABLE activity_event DROP COLUMN duration;

-- changeset denismalinin:1757497842000-5
ALTER TABLE activity_event ADD COLUMN duration NUMERIC(21, 0);

-- changeset denismalinin:1757497842000-6
ALTER TABLE task DROP COLUMN duration;

-- changeset denismalinin:1757497842000-7
ALTER TABLE task ADD COLUMN duration NUMERIC(21, 0);