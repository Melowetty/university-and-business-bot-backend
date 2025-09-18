-- changeset knbdan:1758208447000-0
ALTER TABLE company DROP COLUMN IF EXISTS description;
-- changeset knbdan:1758208447000-1
ALTER TABLE company ADD COLUMN description VARCHAR(1023);
