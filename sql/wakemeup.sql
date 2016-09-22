-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 22, 2016 at 12:47 PM
-- Server version: 5.7.15-0ubuntu0.16.04.1
-- PHP Version: 7.0.8-0ubuntu0.16.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wakemeup`
--

-- --------------------------------------------------------

--
-- Table structure for table `alarm`
--

CREATE TABLE `alarm` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `idUser` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `enabled` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `idVoter` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `ytlink` varchar(20) CHARACTER SET utf8 NOT NULL,
  `chosen` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `idUser` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `idAmi` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `pending` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `hasAccepted` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `cookie` varchar(255) CHARACTER SET utf8 NOT NULL,
  `mode` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'world',
  `pseudonyme` varchar(16) CHARACTER SET utf8 NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `idSender` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `idReceiver` bigint(20) UNSIGNED ZEROFILL NOT NULL,
  `msg` varchar(140) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alarm`
--
ALTER TABLE `alarm`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idVoter` (`idVoter`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idAmi` (`idAmi`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `pseudonyme` (`pseudonyme`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idSender` (`idSender`),
  ADD KEY `idReceiver` (`idReceiver`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alarm`
--
ALTER TABLE `alarm`
  MODIFY `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `alarm`
--
ALTER TABLE `alarm`
  ADD CONSTRAINT `alarm_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `alarm_ibfk_2` FOREIGN KEY (`idVoter`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`idAmi`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`idSender`) REFERENCES `members` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`idReceiver`) REFERENCES `members` (`id`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
