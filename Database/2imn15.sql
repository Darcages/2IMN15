-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 18, 2017 at 11:08 PM
-- Server version: 5.7.16-0ubuntu0.16.04.1
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
(21, 4, 2, 2);

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
(21, 1),
(21, 2),
(21, 8);

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `ID` int(11) NOT NULL,
  `DeviceType` tinyint(1) NOT NULL,
  `State` tinyint(4) NOT NULL,
  `RoomNr` int(11) NOT NULL,
  `LocX` int(11) NOT NULL,
  `LocY` int(11) NOT NULL,
  `Deployment` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`ID`, `DeviceType`, `State`, `RoomNr`, `LocX`, `LocY`, `Deployment`) VALUES
(1, 1, 1, 3, 0, 0, 1),
(2, 1, 2, 2, 1, 1, 2),
(3, 1, 0, 123, 123, 123, 1),
(4, 1, 0, 4, 2, 3, 0),
(8, 0, 0, 6, 0, 0, NULL),
(16, 0, 0, 2, 2, 2, NULL),
(32, 1, 0, 5, 5, 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `timestamp` datetime NOT NULL,
  `deviceID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `newState` tinyint(4) NOT NULL,
  `userType` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`timestamp`, `deviceID`, `userID`, `newState`, `userType`) VALUES
('2017-01-15 00:00:00', 8, 2, 1, 0),
('2017-01-15 07:17:17', 2, 2, 1, 0),
('2017-01-15 13:50:31', 1, 2, 0, 0),
('2017-01-15 14:20:17', 1, 2, 1, 0),
('2017-01-15 14:20:22', 1, 2, 0, 0),
('2017-01-15 14:20:26', 1, 2, 1, 0),
('2017-01-15 14:20:30', 1, 2, 0, 0),
('2017-01-15 14:20:34', 1, 2, 1, 0),
('2017-01-15 14:54:41', 1, 2, 0, 0),
('2017-01-15 14:54:51', 1, 2, 1, 0),
('2017-01-15 14:55:28', 2, 2, 0, 0),
('2017-01-15 16:09:26', 3, -1, 0, 0),
('2017-01-16 00:00:00', 8, 2, 0, 0),
('2017-01-18 21:05:35', 2, 2, 1, 2),
('2017-01-18 21:06:53', 2, 2, 0, 2),
('2017-01-18 21:09:14', 2, 2, 1, 1),
('2017-01-18 21:12:14', 2, 2, 0, 1),
('2017-01-18 21:12:44', 2, 2, 1, 1),
('2017-01-18 21:14:00', 2, 2, 0, 1),
('2017-01-18 21:14:15', 2, 2, 1, 3),
('2017-01-18 21:15:00', 2, 2, 0, 3),
('2017-01-18 21:15:11', 2, 2, 1, 1),
('2017-01-18 21:15:55', 2, 2, 0, 1),
('2017-01-18 21:16:47', 2, 2, 1, 2),
('2017-01-18 22:26:45', 2, 2, 2, 2),
('2017-01-18 22:27:22', 2, 2, 0, 2),
('2017-01-18 22:28:14', 2, 2, 2, 2),
('2017-01-19 22:28:25', 2, 2, 0, 2);

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
  `PrioLevel` int(11) NOT NULL,
  `Red` int(11) NOT NULL,
  `Green` int(11) NOT NULL,
  `Blue` int(11) NOT NULL,
  `LowLight` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user2device`
--

INSERT INTO `user2device` (`UserID`, `DeviceID`, `PrioLevel`, `Red`, `Green`, `Blue`, `LowLight`) VALUES
(2, 1, 1, 255, 255, 255, 0),
(2, 2, 2, 2, 2, 2, 1),
(2, 4, 2, 2, 2, 2, 1);

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
(2, 4, 'asdf', '', 'asdf', 'asdf@asdf.com', 'asdf'),
(3, 2, 'Henk', '', 'Klaas', 'henk@kllaas.nl', 'hallo');

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
  ADD UNIQUE KEY `Prio_UNIQUE` (`DeviceID`,`PrioLevel`);

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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
