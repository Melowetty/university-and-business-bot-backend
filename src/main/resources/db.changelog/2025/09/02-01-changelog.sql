-- liquibase formatted sql

-- changeset denismalinin:1756763167603-1
CREATE SEQUENCE  IF NOT EXISTS activity_seq START WITH 1 INCREMENT BY 50;

-- changeset denismalinin:1756763167603-2
CREATE SEQUENCE  IF NOT EXISTS company_seq START WITH 1 INCREMENT BY 50;

-- changeset denismalinin:1756763167603-3
CREATE SEQUENCE  IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

-- changeset denismalinin:1756763167603-4
CREATE SEQUENCE  IF NOT EXISTS visit_seq START WITH 1 INCREMENT BY 50;

-- changeset denismalinin:1756763167603-5
CREATE TABLE activity (id BIGINT NOT NULL, code VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, location VARCHAR(255) NOT NULL, start_time time WITHOUT TIME ZONE NOT NULL, end_time time WITHOUT TIME ZONE NOT NULL, CONSTRAINT pk_activity PRIMARY KEY (id));

-- changeset denismalinin:1756763167603-6
CREATE TABLE company (id BIGINT NOT NULL, code VARCHAR(255) NOT NULL, name VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, vacancies_link VARCHAR(255) NOT NULL, CONSTRAINT pk_company PRIMARY KEY (id));

-- changeset denismalinin:1756763167603-7
CREATE TABLE users (id BIGINT NOT NULL, tg_id BIGINT NOT NULL, full_name VARCHAR(255) NOT NULL, course INTEGER NOT NULL, program VARCHAR(255) NOT NULL, email VARCHAR(255), is_complete_conference BOOLEAN NOT NULL, creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL, CONSTRAINT pk_users PRIMARY KEY (id));

-- changeset denismalinin:1756763167603-8
CREATE TABLE visit (id BIGINT NOT NULL, user_id BIGINT NOT NULL, target_id BIGINT NOT NULL, target_type VARCHAR(255) NOT NULL, time TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT pk_visit PRIMARY KEY (id));

-- changeset denismalinin:1756763167603-9
ALTER TABLE activity ADD CONSTRAINT uc_activity_code UNIQUE (code);

-- changeset denismalinin:1756763167603-10
ALTER TABLE company ADD CONSTRAINT uc_company_code UNIQUE (code);

-- changeset denismalinin:1756763167603-11
ALTER TABLE users ADD CONSTRAINT uc_users_tgid UNIQUE (tg_id);

-- changeset denismalinin:1756763167603-12
CREATE INDEX idx_users_tg_id ON users (tg_id);

-- changeset denismalinin:1756763167603-13
CREATE INDEX idx_visit_user_id ON visit (user_id);

