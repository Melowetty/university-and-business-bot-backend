-- changeset knbdan:1758205710000-0
ALTER TABLE company DROP COLUMN IF EXISTS vacancies_link;
-- changeset knbdan:1758205710000-1
ALTER TABLE company ADD COLUMN site_url VARCHAR(255);
