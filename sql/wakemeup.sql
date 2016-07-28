-- Adminer 3.3.3 MySQL dump

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = 'SYSTEM';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `wakemeup`;
CREATE DATABASE `wakemeup` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wakemeup`;

DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `enabled` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `idVoter` bigint(20) unsigned zerofill NOT NULL,
  `ytlink` varchar(20) CHARACTER SET utf8 NOT NULL,
  `chosen` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`),
  KEY `idVoter` (`idVoter`),
  CONSTRAINT `alarm_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
  CONSTRAINT `alarm_ibfk_2` FOREIGN KEY (`idVoter`) REFERENCES `alarm` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;


DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `idAmi` bigint(20) unsigned zerofill NOT NULL,
  `pending` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `hasAccepted` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`),
  KEY `idAmi` (`idAmi`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
  CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`idAmi`) REFERENCES `members` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;


DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `cookie` varchar(255) CHARACTER SET utf8 NOT NULL,
  `mode` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'world',
  `pseudonyme` varchar(16) CHARACTER SET utf8 NOT NULL,
  `date_creation` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `pseudonyme` (`pseudonyme`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;


-- 2016-07-28 10:37:17
