-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.19 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for carorder
DROP DATABASE IF EXISTS `carorder`;
CREATE DATABASE IF NOT EXISTS `carorder` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `carorder`;

-- Dumping structure for table carorder.customers
DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `orderNumber` varchar(50) DEFAULT NULL,
  `orderDate` date DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `street` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

-- Dumping data for table carorder.customers: ~5 rows (approximately)
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id`, `name`, `orderNumber`, `orderDate`, `country`, `street`, `city`, `model`, `quantity`, `price`) VALUES
	(52, 'Kevin', '78198', '2016-12-12', 'Ireland', 'main street', 'Galway', 'Renault', 1, 200),
	(53, 'David', '250', '2018-12-31', 'USA', '4th Street', 'New York', 'Mercedes', 3, 1800),
	(54, 'Frank Bruno', '49153', '2018-12-20', 'UK', 'Lilly Street', 'London', 'Renault', 2, 400),
	(55, 'Gary Murphy', '56764', '2018-12-24', 'Ireland', '1 Forkhill Road', 'Dundalk', 'BMW', 1, 500),
	(56, 'Eric Leeson', '12799', '2018-12-28', 'Northern Ireland', '145 Seaview Road', 'Belfast', 'BMW', 1, 500);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
