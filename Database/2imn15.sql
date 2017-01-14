-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 14, 2017 at 03:50 PM
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
  `State` tinyint(1) NOT NULL,
  `RoomNr` int(11) NOT NULL,
  `LocX` int(11) NOT NULL,
  `LocY` int(11) NOT NULL,
  `User1R` int(11) NOT NULL,
  `User1G` int(11) NOT NULL,
  `User1B` int(11) NOT NULL,
  `User1LL` tinyint(1) NOT NULL,
  `User2R` int(11) NOT NULL,
  `User2G` int(11) NOT NULL,
  `User2B` int(11) NOT NULL,
  `User2LL` tinyint(1) NOT NULL,
  `User3R` int(11) NOT NULL,
  `User3G` int(11) NOT NULL,
  `User3B` int(11) NOT NULL,
  `User3LL` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`ID`, `DeviceType`, `State`, `RoomNr`, `LocX`, `LocY`, `User1R`, `User1G`, `User1B`, `User1LL`, `User2R`, `User2G`, `User2B`, `User2LL`, `User3R`, `User3G`, `User3B`, `User3LL`) VALUES
(1, 1, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2, 1, 0, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(4, 1, 0, 4, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(8, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(16, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(32, 1, 0, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

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
-- Indexes for table `user2device`
--
ALTER TABLE `user2device`
  ADD PRIMARY KEY (`UserID`,`DeviceID`);

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
