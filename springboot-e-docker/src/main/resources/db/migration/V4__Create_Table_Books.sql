CREATE TABLE IF NOT EXISTS `books` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `launch_date` date NOT NULL,
  `price` decimal(65,2) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)