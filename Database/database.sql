-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 20, 2017 at 08:46 PM
-- Server version: 5.7.17-0ubuntu0.16.04.1
-- PHP Version: 5.6.29-1+deb.sury.org~xenial+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2imn15`
--
CREATE DATABASE IF NOT EXISTS `2imn15` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `2imn15`;


CREATE USER 'iot'@'localhost' IDENTIFIED BY 'p8LqySJhb49ovpPf5Cq5';

-- --------------------------------------------------------

--
-- Table structure for table `desks`
--

CREATE TABLE `desks` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `LocX` int(11) NOT NULL,
  `LocY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `desks`
--

INSERT INTO `desks` (`ID`, `UserID`, `LocX`, `LocY`) VALUES
(1, 23, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `device2desk`
--

CREATE TABLE `device2desk` (
  `DeskID` int(11) NOT NULL,
  `DeviceID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `device2desk`
--

INSERT INTO `device2desk` (`DeskID`, `DeviceID`) VALUES
(1, 1),
(1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `ID` int(11) NOT NULL,
  `DeviceType` tinyint(1) NOT NULL,
  `State` tinyint(1) NOT NULL,
  `RoomNr` int(11) NOT NULL,
  `LocX` int(11) NOT NULL,
  `LocY` int(11) NOT NULL,
  `Deployment` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`ID`, `DeviceType`, `State`, `RoomNr`, `LocX`, `LocY`, `Deployment`) VALUES
(1, 1, 0, 3, 1, 5, 1),
(2, 1, 0, 3, 4, 5, 1),
(3, 0, 0, 3, 5, 5, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `timestamp` datetime NOT NULL,
  `deviceID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `newState` tinyint(1) NOT NULL,
  `userType` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`timestamp`, `deviceID`, `userID`, `newState`, `userType`) VALUES
('2017-01-20 20:38:40', 1, -1, 0, 0),
('2017-01-20 20:39:07', 2, -1, 0, 0),
('2017-01-20 20:39:08', 2, 23, 1, 1),
('2017-01-20 20:39:14', 3, -1, 0, 0),
('2017-01-20 20:39:15', 3, 23, 1, 1),
('2017-01-20 20:48:40', 1, 23, 1, 1),
('2017-01-20 20:58:40', 1, 23, 0, 1),
('2017-01-20 20:59:15', 3, 23, 0, 1),
('2017-01-20 21:58:40', 1, 23, 1, 2),
('2017-01-20 21:59:15', 3, 23, 1, 1),
('2017-01-20 22:39:08', 2, 23, 0, 1),
('2017-01-20 22:58:40', 1, 23, 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `RoomNr` int(11) NOT NULL,
  `Hostname` varchar(45) NOT NULL,
  `Port` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user2device`
--

CREATE TABLE `user2device` (
  `UserID` int(11) NOT NULL,
  `DeviceID` int(11) NOT NULL,
  `PrioLevel` tinyint(1) NOT NULL,
  `Red` smallint(3) NOT NULL,
  `Green` smallint(3) NOT NULL,
  `Blue` smallint(3) NOT NULL,
  `LowLight` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user2device`
--

INSERT INTO `user2device` (`UserID`, `DeviceID`, `PrioLevel`, `Red`, `Green`, `Blue`, `LowLight`) VALUES
(23, 1, 1, 200, 100, 30, 1);

-- --------------------------------------------------------

--
-- Table structure for table `useraccount`
--

CREATE TABLE `useraccount` (
  `GroupNr` int(11) NOT NULL,
  `RoomNr` int(11) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `Prefix` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `useraccount`
--

INSERT INTO `useraccount` (`GroupNr`, `RoomNr`, `FirstName`, `Prefix`, `LastName`, `Email`, `Password`) VALUES
(23, 2, 'Foo', '', 'Bar', 'foo@bar.com', 'pwd-23');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `desks`
--
ALTER TABLE `desks`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `device2desk`
--
ALTER TABLE `device2desk`
  ADD PRIMARY KEY (`DeskID`,`DeviceID`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`timestamp`,`deviceID`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`RoomNr`),
  ADD UNIQUE KEY `RoomNr_UNIQUE` (`RoomNr`);

--
-- Indexes for table `user2device`
--
ALTER TABLE `user2device`
  ADD PRIMARY KEY (`UserID`,`DeviceID`),
  ADD UNIQUE KEY `UserID` (`UserID`,`DeviceID`);

--
-- Indexes for table `useraccount`
--
ALTER TABLE `useraccount`
  ADD PRIMARY KEY (`GroupNr`),
  ADD UNIQUE KEY `GroupNr_UNIQUE` (`GroupNr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `desks`
--
ALTER TABLE `desks`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
