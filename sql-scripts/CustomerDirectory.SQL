CREATE DATABASE IF NOT EXISTS `customer_directory`;

USE `customer_directory`;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
    `id` int NOT NULL AUTO_INCREMENT, `first_name` varchar(45) DEFAULT NULL, `last_name` varchar(45) DEFAULT NULL, `street` varchar(45) DEFAULT NULL, `address` varchar(45) DEFAULT NULL, `city` varchar(45) DEFAULT NULL, `state` varchar(45) DEFAULT NULL, `email` varchar(45) DEFAULT NULL, `phone` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = latin1;