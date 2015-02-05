CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `category_UNIQUE` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `sex` (
  `sex_id` int(11) NOT NULL AUTO_INCREMENT,
  `sex_name` varchar(45) NOT NULL,
  PRIMARY KEY (`sex_id`),
  UNIQUE KEY `sex_id_UNIQUE` (`sex_id`),
  UNIQUE KEY `sex_name_UNIQUE` (`sex_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `times_available` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `time_UNIQUE` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `days_available` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `day_UNIQUE` (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(128) NOT NULL,
  `password_hash` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `last_name` varchar(128) NOT NULL,
  `birthdate` date NOT NULL,
  `car` tinyint(4) DEFAULT NULL,
  `abouttext` longtext,
  `fk_sex` int(11) DEFAULT NULL,
  `picture` longblob,
  `street` varchar(45) DEFAULT NULL,
  `plz` int(11) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail_UNIQUE` (`mail`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `const_member_sex_idx` (`fk_sex`),
  CONSTRAINT `const_member_sex` FOREIGN KEY (`fk_sex`) REFERENCES `sex` (`sex_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `member_day_time_available` (
  `fk_member_id` int(11) NOT NULL,
  `fk_day_id` int(11) NOT NULL,
  `fk_time_id` int(11) NOT NULL,
  PRIMARY KEY (`fk_member_id`,`fk_day_id`,`fk_time_id`),
  KEY `const_days_id_idx` (`fk_day_id`),
  KEY `const_mdta_time_idx` (`fk_time_id`),
  CONSTRAINT `const_mdta_time` FOREIGN KEY (`fk_time_id`) REFERENCES `times_available` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_mdta_day` FOREIGN KEY (`fk_day_id`) REFERENCES `days_available` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_mdta_member` FOREIGN KEY (`fk_member_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `advertiser_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `fk_time_id` int(11) NOT NULL,
  `limit` double NOT NULL,
  `income` double NOT NULL,
  `text` longtext NOT NULL,
  `fk_category` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `fav_market` varchar(45) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `const_ad_advertiser_id_idx` (`advertiser_id`),
  KEY `const_ad_time_id_idx` (`fk_time_id`),
  KEY `const_ad_category_idx` (`fk_category`),
  KEY `const_ad_buyer_idx` (`buyer_id`),
  CONSTRAINT `const_ad_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_ad_advertiser_id` FOREIGN KEY (`advertiser_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_ad_category` FOREIGN KEY (`fk_category`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_ad_time_id` FOREIGN KEY (`fk_time_id`) REFERENCES `times_available` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `time_sent` datetime NOT NULL,
  `ad_id` int(11) DEFAULT NULL,
  `text` longtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `const_message_sender_idx` (`sender_id`),
  KEY `const_message_receiver_idx` (`receiver_id`),
  KEY `const_message_ad_idx` (`ad_id`),
  CONSTRAINT `const_message_ad` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_message_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_message_sender` FOREIGN KEY (`sender_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buyer_id` int(11) NOT NULL,
  `advertiser_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `text` longtext NOT NULL,
  `ad_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `const_rating_buyer_idx` (`buyer_id`),
  KEY `const_rating_advertiser_idx` (`advertiser_id`),
  KEY `const_rating_ad_idx` (`ad_id`),
  CONSTRAINT `const_rating_ad` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_rating_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `const_rating_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `category` (`id`, `category`) VALUES
	(3, 'Apotheke'),
	(2, 'Baumarkt'),
	(5, 'Bäcker'),
	(1, 'Supermarkt'),
	(4, 'Tierbedarf');
	
INSERT INTO `sex` (`sex_id`, `sex_name`) VALUES (1, 'Männlich');
INSERT INTO `sex` (`sex_id`, `sex_name`) VALUES (2, 'Weiblich');
INSERT INTO `sex` (`sex_id`, `sex_name`) VALUES (3, 'Unsicher');

INSERT INTO `times_available` (`id`, `time`) VALUES (1, 'Morgens');
INSERT INTO `times_available` (`id`, `time`) VALUES (2, 'Vormittags');
INSERT INTO `times_available` (`id`, `time`) VALUES (3, 'Mittags');
INSERT INTO `times_available` (`id`, `time`) VALUES (4, 'Nachmittags');
INSERT INTO `times_available` (`id`, `time`) VALUES (5, 'Abends');

INSERT INTO `days_available` (`id`, `day`) VALUES (1, 'Montag');
INSERT INTO `days_available` (`id`, `day`) VALUES (2, 'Dienstag');
INSERT INTO `days_available` (`id`, `day`) VALUES (3, 'Mittwoch');
INSERT INTO `days_available` (`id`, `day`) VALUES (4, 'Donnerstag');
INSERT INTO `days_available` (`id`, `day`) VALUES (5, 'Freitag');
INSERT INTO `days_available` (`id`, `day`) VALUES (6, 'Samstag');

INSERT INTO `member` (`id`, `mail`, `password_hash`, `name`, `last_name`, `birthdate`, `car`, `abouttext`, `fk_sex`, `picture`, `street`, `plz`, `phone`) VALUES (1, 'cgerdon@gmail.com', 'asdf', 'Christoph', 'Gerdon', '1987-10-19', 1, 'Das bin Ich!', 1, NULL, 'Luise-Riegger-Straße 54', 76137, '01719028828');
INSERT INTO `member` (`id`, `mail`, `password_hash`, `name`, `last_name`, `birthdate`, `car`, `abouttext`, `fk_sex`, `picture`, `street`, `plz`, `phone`) VALUES (2, 'achim@gmx.de', 'achim', 'Achim', 'Adam', '1980-01-01', 1, 'Der Achim', 1, NULL, 'Müllerweg 123', 76829, '0190123456');
INSERT INTO `member` (`id`, `mail`, `password_hash`, `name`, `last_name`, `birthdate`, `car`, `abouttext`, `fk_sex`, `picture`, `street`, `plz`, `phone`) VALUES (3, 'Beate@web.de', 'beate', 'Beate', 'Braun', '1955-05-05', 0, 'Beate in da house', 3, NULL, 'Weinweg 5', 68165, '051512535123');
INSERT INTO `member` (`id`, `mail`, `password_hash`, `name`, `last_name`, `birthdate`, `car`, `abouttext`, `fk_sex`, `picture`, `street`, `plz`, `phone`) VALUES (4, 'carmen@gmail.com', 'carmen', 'Carmen', 'Schmörebröd', '1990-12-24', 1, 'Knäckebrot fetzt arg', 2, NULL, 'Ikealand 1', 10823, '0049123456778');
INSERT INTO `member` (`id`, `mail`, `password_hash`, `name`, `last_name`, `birthdate`, `car`, `abouttext`, `fk_sex`, `picture`, `street`, `plz`, `phone`) VALUES (5, 'dada@dudu.com', 'dada', 'Dada', 'Dudu', '1918-12-11', 0, 'gna', 3, NULL, 'Ja!', 76137, '0727171458525');

INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 1, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 1, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 1, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 1, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 1, 5);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 2, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 2, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 2, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 2, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 2, 5);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 3, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 3, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 3, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 3, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 3, 5);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 4, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 4, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 4, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 4, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (1, 4, 5);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (3, 4, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (3, 4, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (3, 4, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (3, 4, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (3, 4, 5);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (2, 4, 1);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (2, 4, 2);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (2, 4, 3);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (2, 4, 4);
INSERT INTO `member_day_time_available` (`fk_member_id`, `fk_day_id`, `fk_time_id`) VALUES (2, 4, 5);

INSERT INTO `ad` (`id`, `advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES (2, 3, '2014-11-05', 3, 10, 1, 'Hallo, ich mag bitte eine Flasche Schnaps haben!', 1, 0, 'Aldi', NULL);
INSERT INTO `ad` (`id`, `advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES (3, 5, '2014-12-07', 1, 100, 10, 'Hallo,\r\nich hätte gerne einen kompletten Wocheneinkauf. Also auch Wasser, Bier, Wurst, Brot, Käse, Marmelade und ein KG feinstes, reines, russisches Heroin.\r\nDanke', 1, 0, 'Mir latte', NULL);
INSERT INTO `ad` (`id`, `advertiser_id`, `date`, `fk_time_id`, `limit`, `income`, `text`, `fk_category`, `status`, `fav_market`, `buyer_id`) VALUES (4, 3, '2015-02-04', 1, 1, 0, 'Ich bin Einsam. Will eigentlich gar nix eingekauft bekommen', 4, 1, 'egal', NULL);

INSERT INTO `message` (`id`, `sender_id`, `receiver_id`, `time_sent`, `ad_id`, `text`) VALUES (1, 2, 3, '2015-01-05 14:52:23', 4, 'Such dir halt Freunde!');
INSERT INTO `message` (`id`, `sender_id`, `receiver_id`, `time_sent`, `ad_id`, `text`) VALUES (2, 3, 2, '2015-02-05 16:52:54', 4, 'Verpiss dich halt!');

INSERT INTO `rating` (`id`, `buyer_id`, `advertiser_id`, `rating`, `text`, `ad_id`) VALUES (1, 2, 3, 5, 'Sie war Einsam. Und es gab einen Jägermeister für mich!', 4);