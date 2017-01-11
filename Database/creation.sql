CREATE DATABASE `2imn15`;

CREATE USER 'iot'@'localhost' IDENTIFIED BY 'p8LqySJhb49ovpPf5Cq5';

CREATE TABLE `useraccount` (
  `GroupNr` int(11) NOT NULL,
  `RoomNr` int(11) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `Prefix` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`GroupNr`),
  UNIQUE KEY `GroupNr_UNIQUE` (`GroupNr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
