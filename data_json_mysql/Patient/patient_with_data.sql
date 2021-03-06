CREATE DATABASE  IF NOT EXISTS `mediscreen` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mediscreen`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: mediscreen
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `familly_name` varchar(60) NOT NULL,
  `given_name` varchar(60) NOT NULL,
  `date_of_birth` datetime NOT NULL,
  `gender` char(1) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'TestNone','Test','1966-12-31 00:00:00','F','1 Brookside St','100-222-3333'),(2,'TestBorderline','Test','1945-06-24 00:00:00','M','2 High St','200-333-4444'),(3,'TestInDanger','Test','2004-06-18 00:00:00','M','3 Club Road','300-444-5555'),(4,'TestEarlyOnset','Test','2002-06-28 00:00:00','F','4 Valley Dr','400-555-6666'),(5,'Ferguson','Lucas','1968-06-22 00:00:00','M','2 Warren Street','387-866-1399'),(6,'Rees','Pippa','1952-09-27 00:00:00','F','745 West Valley Farms Drive','628-423-0993'),(7,'Arnold','Edward','1952-11-11 00:00:00','M','599 East Garden Ave','123-727-2779'),(8,'Sharp','Anthony','1946-11-26 00:00:00','M','894 Hall Street','451-761-8383'),(9,'Ince','Wendy','1958-06-29 00:00:00','F','4 Southampton Road','802-911-9975'),(10,'Ross','Tracey','1949-12-07 00:00:00','F','40 Sulphur Springs Dr','131-396-5049'),(11,'Wilson','Claire','1966-12-31 00:00:00','F','12 Cobblestone St','300-452-1091'),(12,'Buckland','Max','1945-06-24 00:00:00','M','193 Vale St','833-534-0864'),(13,'Clark','Natalie','1964-06-18 00:00:00','F','12 Beechwood Road','241-467-9197'),(14,'Bailey','Piers','1959-06-28 00:00:00','M','1202 Bumble Dr','747-815-0557');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-29 16:37:18
