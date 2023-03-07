-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 02, 2023 at 06:08 PM
-- Server version: 10.3.37-MariaDB
-- PHP Version: 8.1.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spr23_csc429_aspra3`
--

-- --------------------------------------------------------

--
-- Table structure for table `Patron`
--

CREATE TABLE `Patron` (
  `patronID` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `address` varchar(70) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `city` varchar(20) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `stateCode` char(2) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `zip` char(5) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `email` varchar(30) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `dateOfBirth` char(12) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `status` enum('Active','Inactive') CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Patron`
--
ALTER TABLE `Patron`
  ADD PRIMARY KEY (`patronID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Patron`
--
ALTER TABLE `Patron`
  MODIFY `patronID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
