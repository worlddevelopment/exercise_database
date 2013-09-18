--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `swp`.`aufgabe` DROP PRIMARY KEY;

ALTER TABLE `swp`.`blatt` DROP PRIMARY KEY;

DROP TABLE `swp`.`veranstaltung`;

DROP TABLE `swp`.`typ`;

DROP TABLE `swp`.`test`;

DROP TABLE `swp`.`blatt`;

DROP TABLE `swp`.`dozent`;

DROP TABLE `swp`.`semester`;

DROP TABLE `swp`.`aufgabe`;

CREATE TABLE `swp`.`veranstaltung` (
);

CREATE TABLE `swp`.`typ` (
	`id` INT NOT NULL,
	`typ_bez` VARCHAR(15) NOT NULL
);

CREATE TABLE `swp`.`test` (
);

CREATE TABLE `swp`.`blatt` (
	`id` INT NOT NULL,
	`bl_bez` VARCHAR(30) NOT NULL,
	`beschr` TEXT,
	`aufg_anz` INT,
	`sem_id` INT NOT NULL,
	`ver_id` INT NOT NULL,
	`typ_id` INT NOT NULL,
	`doz_id` INT NOT NULL,
	`link` TEXT NOT NULL,
	`inhalt` TEXT NOT NULL,
	`bild_link` TEXT,
	PRIMARY KEY (`id`)
);

CREATE TABLE `swp`.`dozent` (
	`id` INT NOT NULL,
	`name` VARCHAR(30) NOT NULL
);

CREATE TABLE `swp`.`semester` (
);

CREATE TABLE `swp`.`aufgabe` (
	`id` INT NOT NULL,
	`aufg_bez` VARCHAR(30) NOT NULL,
	`bl_id` VARCHAR(30) NOT NULL,
	`link` TEXT NOT NULL,
	`inhalt` TEXT NOT NULL,
	`bild_link` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `swp`.`aufgabe` ADD PRIMARY KEY (`id`);


