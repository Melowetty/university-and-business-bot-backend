-- changeset knbdan:1758318020000-0
ALTER TABLE users ADD COLUMN is_got_survey_reward BOOLEAN NOT NULL DEFAULT false;