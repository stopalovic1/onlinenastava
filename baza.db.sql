BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `profesori` (
	`id`	INTEGER,
	`naziv`	TEXT,
	`username`	TEXT,
	`password`	TEXT,
	`satiutjednu` INTEGER,
	PRIMARY KEY(`id`)
);
INSERT INTO `profesori` VALUES (1,'Bajric Halid','hbajric','aaaaaa',23);
INSERT INTO `profesori` VALUES (2,'Maslesa Suad','msuad','bbbbb',8);
CREATE TABLE IF NOT EXISTS `predmeti` (
	`id`	INTEGER,
	`odjel`	TEXT,
	`grupa`	TEXT,
    `duzina` TEXT,
    `predmet` TEXT,
    `brojsati` INTEGER,
    `profesor_id` INTEGER,
    FOREIGN KEY (`profesor_id`) REFERENCES `profesori`,
	PRIMARY KEY(`id`)
);
INSERT INTO `predmeti` VALUES (1,'I-2 MTS','cijeli odjel','blok sat','tehnologija obrade',1,1);
INSERT INTO `predmeti` VALUES (2,'I-2 MTS','cijeli odjel','blok sat','masinski materijali',1,1);
INSERT INTO `predmeti` VALUES (3,'IV-4 arh','cijeli odjel','blok sat','organizacija gradenja',1,2);
INSERT INTO `predmeti` VALUES (4,'IV-4 arh','cijeli odjel','blok sat','projektovanje',1,2);
CREATE TABLE IF NOT EXISTS `sadrzaj` (
	`id`	INTEGER,
	`sadrzajcasa`	TEXT,
	`dan`	TEXT,
    `datumcasa` TEXT,
    `datumobjave` TEXT,
    `izostaliucenici` TEXT,
    `predmet_id` INTEGER,
    FOREIGN KEY (`predmet_id`) REFERENCES `predmeti`,
	PRIMARY KEY(`id`)
);
INSERT INTO `sadrzaj` VALUES (1,'način obrade crnih metala','ponedjeljak','6.4.2020','6.4.2020','opis neki',1);
INSERT INTO `sadrzaj` VALUES (2,'- koji sve elementi u mašinstvu postoje- ????- xxxx','utorak','7.4.2020','7.4.2020','opis neki111',2);
COMMIT;
