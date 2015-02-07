CREATE DATABASE  IF NOT EXISTS `testdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `testdb`;
-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: testdb
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Pokoje`
--

DROP TABLE IF EXISTS `Pokoje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pokoje` (
  `nrPokoju` varchar(255) DEFAULT NULL,
  `RodzajPokoju` int(11) DEFAULT NULL,
  `Cena` int(11) DEFAULT NULL,
  `IDGoscia` int(11) DEFAULT NULL,
  `DataPrzyjazdu` varchar(255) DEFAULT NULL,
  `DataOdjazdu` varchar(255) DEFAULT NULL,
  `WolnyWTerminie` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pokoje`
--

LOCK TABLES `Pokoje` WRITE;
/*!40000 ALTER TABLE `Pokoje` DISABLE KEYS */;
INSERT INTO `Pokoje` VALUES ('101',1,200,3,'2015-02-07','2015-02-10',0),('102',1,200,NULL,NULL,NULL,1),('103',1,200,NULL,NULL,NULL,1),('104',1,200,NULL,NULL,NULL,1),('105',1,200,NULL,NULL,NULL,1),('106',2,220,7,'2015-02-08','2015-02-11',0),('107',2,220,NULL,NULL,NULL,1),('108',2,220,NULL,NULL,NULL,1),('109',2,220,NULL,NULL,NULL,1),('110',2,220,5,'2015-02-08','2015-02-12',0),('111',3,240,NULL,NULL,NULL,1),('112',3,240,NULL,NULL,NULL,1),('113',3,240,6,'2015-02-09','2015-02-10',0),('114',3,240,NULL,NULL,NULL,1),('115',3,240,NULL,NULL,NULL,1),('116',4,260,4,'2015-02-07','2015-02-15',0),('117',4,260,1,'2015-02-06','2015-02-10',0),('118',4,260,NULL,NULL,NULL,1),('119',4,260,2,'2015-02-06','2015-02-11',0),('120',4,260,NULL,NULL,NULL,1),('121',5,280,NULL,NULL,NULL,1),('122',5,280,NULL,NULL,NULL,1),('123',5,280,NULL,NULL,NULL,1),('124',6,300,8,'2015-03-01','2015-03-08',1),('125',6,300,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `Pokoje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-08  0:13:27
