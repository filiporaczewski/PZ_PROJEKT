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
-- Table structure for table `GoscieArchiwum`
--

DROP TABLE IF EXISTS `GoscieArchiwum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GoscieArchiwum` (
  `IDGoscia` int(11) DEFAULT NULL,
  `Imie` varchar(255) DEFAULT NULL,
  `Nazwisko` varchar(255) DEFAULT NULL,
  `nrPokoju` varchar(255) DEFAULT NULL,
  `Adres` varchar(255) DEFAULT NULL,
  `DataPrzyjazdu` varchar(255) DEFAULT NULL,
  `DataOdjazdu` varchar(255) DEFAULT NULL,
  `Rachunek` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GoscieArchiwum`
--

LOCK TABLES `GoscieArchiwum` WRITE;
/*!40000 ALTER TABLE `GoscieArchiwum` DISABLE KEYS */;
INSERT INTO `GoscieArchiwum` VALUES (1,'Filip','Oraczewski','117','Spokojna 15','2015-02-06','2015-02-10',1040),(2,'Rafal','Dabrowski','119','Mazowiecka 5','2015-02-06','2015-02-11',1300),(3,'Piotr','Dmowski','101','Marszalkowska 16A','2015-02-07','2015-02-10',600),(4,'Amelia','Raciborska','116','Armi Krajowej 35','2015-02-07','2015-02-15',2080),(5,'Pawel','Gluchowski','110','Baczynskiego 1','2015-02-08','2015-02-12',880),(6,'Elzbieta','Grycuk','113','Kredytowa 12','2015-02-09','2015-02-10',240),(7,'Emil','Grzegorczyk','106','Egejska 37','2015-02-08','2015-02-11',660),(8,'Jan','Stachurski','124','Polinezyjska 5','2015-03-01','2015-03-08',2100),(9,'Artur','Oraczewski','108','Spokojna 15','2015-02-07','2015-02-10',660);
/*!40000 ALTER TABLE `GoscieArchiwum` ENABLE KEYS */;
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
