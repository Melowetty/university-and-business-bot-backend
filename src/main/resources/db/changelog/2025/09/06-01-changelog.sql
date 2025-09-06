-- liquibase formatted sql

-- changeset knbdan:1757159050142-1
CREATE TABLE vote (id BIGINT NOT NULL, user_id BIGINT NOT NULL, answer VARCHAR(255) NOT NULL, event_id BIGINT NOT NULL, CONSTRAINT pk_vote PRIMARY KEY (id);
-- changeset knbdan:1757159050142-2
CREATE TABLE event (id BIGINT NOT NULL,  status VARCHAR(255) NOT NULL, answers TEXT NOT NULL, CONSTRAINT pk_event PRIMARY KEY (id);
