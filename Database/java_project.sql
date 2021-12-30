-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 30, 2021 at 09:19 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` text NOT NULL,
  `upload_date` datetime NOT NULL,
  `file_path` text NOT NULL,
  `uploader` text NOT NULL,
  `caption` text CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `upload_date`, `file_path`, `uploader`, `caption`) VALUES
('574798659', '2021-12-21 02:42:14', 'http://localhost/java_project/uploads/574798659.jpg', 'opsf', 'Deneme 1'),
('794506557', '2021-12-21 02:42:28', 'http://localhost/java_project/uploads/794506557.jpg', 'opsf', 'Bu da 2. deneme'),
('909077448', '2021-12-21 02:42:46', 'http://localhost/java_project/uploads/909077448.jpg', 'opsf', '3.ye de Türkçe karakter katalım :*'),
('431130943', '2021-12-21 03:32:51', 'http://localhost/java_project/uploads/431130943.jpg', 'opsf', 'aaaaaaaaaaa'),
('932156498', '2021-12-21 14:45:24', 'http://localhost/java_project/uploads/932156498.jpg', 'opsf', 'jko'),
('412199428', '2021-12-23 02:04:11', 'http://localhost/java_project/uploads/412199428.jpg', 'test', 'new acc\nFollow <3'),
('683490296', '2021-12-23 02:07:22', 'http://localhost/java_project/uploads/683490296.jpg', 'test', 'sdfsfds'),
('952554390', '2021-12-27 14:36:27', 'http://localhost/java_project/uploads/952554390.jpg', 'test', 'Post deneme amaçlı');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `username` text NOT NULL,
  `email` text CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `password` text NOT NULL,
  `post_count` text NOT NULL DEFAULT '0',
  `biography` text CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL DEFAULT 'This guy is too lazy even cannot write a bio :(',
  `profile_pic` text NOT NULL DEFAULT 'http://localhost/java_project/profile-pictures/default.jpg',
  `posts` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `username`, `email`, `password`, `post_count`, `biography`, `profile_pic`, `posts`) VALUES
(1, 'opsf', 'prodenizpro@gmail.com', '1234', '8', 'World is not wonderful :(', 'http://localhost/java_project/profile-pictures/hq720.jpg', '537846432, 103796119, 581471630, 574798659, 794506557, 909077448, 431130943, 932156498, '),
(4, 'test', 'deniz@deniz.com', '1234', '3', 'This guy is too lazy even cannot write a bio :(', 'http://localhost/java_project/profile-pictures/default.jpg', '412199428, 683490296, 952554390, ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD UNIQUE KEY `id` (`id`) USING HASH;

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `UserID` (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
