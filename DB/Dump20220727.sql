CREATE DATABASE  IF NOT EXISTS `withculture` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `withculture`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: withculture
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `tb_attach`
--

DROP TABLE IF EXISTS `tb_attach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_attach` (
  `attach_seq` int NOT NULL AUTO_INCREMENT,
  `attach_name` varchar(100) DEFAULT NULL,
  `attach_file_url` text,
  `attach_created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`attach_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_attach`
--

LOCK TABLES `tb_attach` WRITE;
/*!40000 ALTER TABLE `tb_attach` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_attach` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `tb_daily_memo`
--

DROP TABLE IF EXISTS `tb_daily_memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_daily_memo` (
  `my_daily_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `my_daily_memo_date` date NOT NULL,
  `my_daily_memo_registed_at` timestamp NOT NULL,
  `my_daily_memo_updated_at` timestamp NOT NULL,
  `my_daily_memo` text NOT NULL,
  PRIMARY KEY (`my_daily_seq`),
  KEY `fk_tb_daily_memo_tb_user_idx` (`user_seq`),
  CONSTRAINT `fk_tb_daily_memo_tb_user` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_daily_memo`
--

LOCK TABLES `tb_daily_memo` WRITE;
/*!40000 ALTER TABLE `tb_daily_memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_daily_memo` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `tb_eval`
--

DROP TABLE IF EXISTS `tb_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_eval` (
  `eval_seq` int NOT NULL,
  `user_seq` int NOT NULL,
  `eval_list1` int NOT NULL,
  `eval_list2` int NOT NULL,
  `eval_list3` int NOT NULL,
  `eval_list4` int NOT NULL,
  `eval_list5` int NOT NULL,
  `eval_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`eval_seq`),
  KEY `user_seq_idx` (`user_seq`),
  KEY `eval_user_seq_idx` (`user_seq`),
  CONSTRAINT `eval_user_seq` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_eval`
--

LOCK TABLES `tb_eval` WRITE;
/*!40000 ALTER TABLE `tb_eval` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_eval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_feed`
--

DROP TABLE IF EXISTS `tb_feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed` (
  `feed_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `attach_seq` int NOT NULL,
  `feed_title` varchar(100) NOT NULL,
  `feed_content` text NOT NULL,
  `feed_open` char(1) NOT NULL,
  `feed_created_at` timestamp NULL DEFAULT NULL,
  `feed_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`feed_seq`),
  KEY `user_seq_idx` (`user_seq`),
  KEY `attach_seq_idx` (`attach_seq`),
  CONSTRAINT `attach_seq` FOREIGN KEY (`attach_seq`) REFERENCES `tb_attach` (`attach_seq`),
  CONSTRAINT `user_seq` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_feed`
--

LOCK TABLES `tb_feed` WRITE;
/*!40000 ALTER TABLE `tb_feed` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_feed_hashtag`
--

DROP TABLE IF EXISTS `tb_feed_hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed_hashtag` (
  `fh_seq` int NOT NULL AUTO_INCREMENT,
  `feed_seq` int NOT NULL,
  `fh_tag` varchar(20) NOT NULL,
  `fh_created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`fh_seq`),
  KEY `feed_seq_idx` (`feed_seq`),
  CONSTRAINT `feed_seq` FOREIGN KEY (`feed_seq`) REFERENCES `tb_feed` (`feed_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_feed_hashtag`
--

LOCK TABLES `tb_feed_hashtag` WRITE;
/*!40000 ALTER TABLE `tb_feed_hashtag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_feed_hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_feed_like`
--

DROP TABLE IF EXISTS `tb_feed_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_feed_like` (
  `feed_like_seq` int NOT NULL AUTO_INCREMENT,
  `feed_seq` int NOT NULL,
  `user_seq` int NOT NULL,
  PRIMARY KEY (`feed_like_seq`),
  KEY `user_seq_idx` (`user_seq`),
  KEY `feed_seq_idx` (`feed_seq`),
  CONSTRAINT `feed_seq1` FOREIGN KEY (`feed_seq`) REFERENCES `tb_feed` (`feed_seq`),
  CONSTRAINT `user_seq1` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_feed_like`
--

LOCK TABLES `tb_feed_like` WRITE;
/*!40000 ALTER TABLE `tb_feed_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_feed_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group`
--

DROP TABLE IF EXISTS `tb_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group` (
  `group_seq` int NOT NULL AUTO_INCREMENT,
  `attach_seq` int NOT NULL,
  `group_name` varchar(50) NOT NULL,
  `group_maker` int NOT NULL,
  `group_description` varchar(255) DEFAULT NULL,
  `group_access_type` char(1) NOT NULL,
  `group_pwd` varchar(40) DEFAULT NULL,
  `group_is_active` char(1) NOT NULL,
  `group_status` char(1) NOT NULL,
  `group_created_at` timestamp NULL DEFAULT NULL,
  `group_updated_at` timestamp NULL DEFAULT NULL,
  `group_max` int NOT NULL,
  PRIMARY KEY (`group_seq`),
  KEY `attach_seq_idx` (`attach_seq`),
  CONSTRAINT `cover_attach_seq` FOREIGN KEY (`attach_seq`) REFERENCES `tb_attach` (`attach_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group`
--

LOCK TABLES `tb_group` WRITE;
/*!40000 ALTER TABLE `tb_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_attribute`
--

DROP TABLE IF EXISTS `tb_group_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_attribute` (
  `ga_seq` int NOT NULL AUTO_INCREMENT,
  `group_seq` int NOT NULL,
  `ga_region` varchar(45) NOT NULL,
  `ga_start_date` int NOT NULL,
  `ga_end_date` int NOT NULL,
  `ga_child_join` char(1) NOT NULL,
  `ga_global_join` char(1) NOT NULL,
  `ga_age` int NOT NULL,
  `ga_created_at` timestamp NULL DEFAULT NULL,
  `ga_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ga_seq`),
  KEY `group_seq_idx` (`group_seq`),
  CONSTRAINT `group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_attribute`
--

LOCK TABLES `tb_group_attribute` WRITE;
/*!40000 ALTER TABLE `tb_group_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_chat`
--

DROP TABLE IF EXISTS `tb_group_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_chat` (
  `chat_seq` int NOT NULL AUTO_INCREMENT,
  `group_seq` int NOT NULL,
  `user_seq` int NOT NULL,
  `attach_seq` int NOT NULL,
  `chat_content_type` char(1) NOT NULL,
  `chat_content` varchar(255) NOT NULL,
  `chat_created_at` timestamp NULL DEFAULT NULL,
  `chat_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`chat_seq`),
  KEY `gc_group_seq_idx` (`group_seq`),
  KEY `gc_user_seq_idx` (`user_seq`),
  KEY `gc_attach_seq_idx` (`attach_seq`),
  CONSTRAINT `gc_attach_seq` FOREIGN KEY (`attach_seq`) REFERENCES `tb_attach` (`attach_seq`),
  CONSTRAINT `gc_group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`),
  CONSTRAINT `gc_user_seq` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_chat`
--

LOCK TABLES `tb_group_chat` WRITE;
/*!40000 ALTER TABLE `tb_group_chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_daily_memo`
--

DROP TABLE IF EXISTS `tb_group_daily_memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_daily_memo` (
  `gdm_seq` int NOT NULL AUTO_INCREMENT,
  `group_seq` int NOT NULL,
  `gdm_date` int NOT NULL,
  `gdm_content` text NOT NULL,
  `gdm_created_at` timestamp NULL DEFAULT NULL,
  `gdm_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`gdm_seq`),
  KEY `gdm_group_seq_idx` (`group_seq`),
  CONSTRAINT `gdm_group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_daily_memo`
--

LOCK TABLES `tb_group_daily_memo` WRITE;
/*!40000 ALTER TABLE `tb_group_daily_memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_daily_memo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_destination`
--

DROP TABLE IF EXISTS `tb_group_destination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_destination` (
  `gd_seq` int NOT NULL AUTO_INCREMENT,
  `group_seq` int NOT NULL,
  `heritage_seq` int NOT NULL,
  `gd_completed` char(1) DEFAULT NULL,
  PRIMARY KEY (`gd_seq`),
  KEY `group_seq_idx` (`group_seq`),
  KEY `heritage_seq_idx` (`heritage_seq`),
  CONSTRAINT `gd_group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`),
  CONSTRAINT `gd_heritage_seq` FOREIGN KEY (`heritage_seq`) REFERENCES `tb_heritage` (`heritage_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_destination`
--

LOCK TABLES `tb_group_destination` WRITE;
/*!40000 ALTER TABLE `tb_group_destination` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_destination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_member`
--

DROP TABLE IF EXISTS `tb_group_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_member` (
  `member_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `group_seq` int NOT NULL,
  `member_status` int NOT NULL,
  `member_in_at` timestamp NULL DEFAULT NULL,
  `member_join_appeal` varchar(255) NOT NULL,
  `member_eval` char(1) NOT NULL,
  `member_created_at` timestamp NULL DEFAULT NULL,
  `member_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`member_seq`),
  KEY `gm_user_seq_idx` (`user_seq`),
  KEY `gm_group_seq_idx` (`group_seq`),
  CONSTRAINT `gm_group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`),
  CONSTRAINT `gm_user_seq` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_member`
--

LOCK TABLES `tb_group_member` WRITE;
/*!40000 ALTER TABLE `tb_group_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_group_schedule`
--

DROP TABLE IF EXISTS `tb_group_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_group_schedule` (
  `gs_seq` int NOT NULL AUTO_INCREMENT,
  `group_seq` int NOT NULL,
  `gs_date` int NOT NULL,
  `gs_time` int NOT NULL,
  `gs_content` varchar(80) NOT NULL,
  `gs_registered_at` timestamp NULL DEFAULT NULL,
  `gs_updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`gs_seq`),
  KEY `group_seq_idx` (`group_seq`),
  CONSTRAINT `shedule_group_seq` FOREIGN KEY (`group_seq`) REFERENCES `tb_group` (`group_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_group_schedule`
--

LOCK TABLES `tb_group_schedule` WRITE;
/*!40000 ALTER TABLE `tb_group_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_group_schedule` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `tb_heritage`
--

DROP TABLE IF EXISTS `tb_heritage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_heritage` (
  `heritage_seq` int NOT NULL AUTO_INCREMENT,
  `heritage_name` varchar(50) NOT NULL,
  `heritage_era` varchar(30) DEFAULT NULL,
  `heritage_address` varchar(50) NOT NULL,
  `heritage_category` varchar(10) NOT NULL,
  `heritage_lng` varchar(30) NOT NULL,
  `heritage_lat` varchar(30) NOT NULL,
  `heritage_img_url` varchar(100) DEFAULT NULL,
  `heritage_memo` text NOT NULL,
  `heritage_voice` text,
  `stamp_exist` char(1) NOT NULL,
  `heritage_class` varchar(5) NOT NULL,
  `heritage_scrap_cnt` int NOT NULL,
  `heritage_review_cnt` int NOT NULL,
  PRIMARY KEY (`heritage_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_heritage`
--

LOCK TABLES `tb_heritage` WRITE;
/*!40000 ALTER TABLE `tb_heritage` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_heritage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_heritage_review`
--

DROP TABLE IF EXISTS `tb_heritage_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_heritage_review` (
  `heritage_review_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `heritage_seq` int NOT NULL,
  `heritage_review_text` text NOT NULL,
  `heritage_review_registed_at` timestamp NOT NULL,
  PRIMARY KEY (`heritage_review_seq`),
  KEY `fk_tb_heritage_review_tb_user1_idx` (`user_seq`),
  KEY `fk_tb_heritage_review_tb_heritage1_idx` (`heritage_seq`),
  CONSTRAINT `fk_tb_heritage_review_tb_heritage1` FOREIGN KEY (`heritage_seq`) REFERENCES `tb_heritage` (`heritage_seq`),
  CONSTRAINT `fk_tb_heritage_review_tb_user1` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_heritage_review`
--

LOCK TABLES `tb_heritage_review` WRITE;
/*!40000 ALTER TABLE `tb_heritage_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_heritage_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_heritage_scrap`
--

DROP TABLE IF EXISTS `tb_heritage_scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_heritage_scrap` (
  `heritage_scrap_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `heritage_seq` int NOT NULL,
  `heritage_scrap_registed_at` timestamp NOT NULL,
  PRIMARY KEY (`heritage_scrap_seq`),
  KEY `fk_tb_heritage_scrap_tb_user1_idx` (`user_seq`),
  KEY `fk_tb_heritage_scrap_tb_heritage1_idx` (`heritage_seq`),
  CONSTRAINT `fk_tb_heritage_scrap_tb_heritage1` FOREIGN KEY (`heritage_seq`) REFERENCES `tb_heritage` (`heritage_seq`),
  CONSTRAINT `fk_tb_heritage_scrap_tb_user1` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_heritage_scrap`
--

LOCK TABLES `tb_heritage_scrap` WRITE;
/*!40000 ALTER TABLE `tb_heritage_scrap` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_heritage_scrap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_my_keyword`
--

DROP TABLE IF EXISTS `tb_my_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_my_keyword` (
  `my_keyword_seq` int NOT NULL AUTO_INCREMENT,
  `my_keyword_name` varchar(20) NOT NULL,
  `my_keyword_registed_at` timestamp NOT NULL,
  `my_keyword_isSelected` char(1) NOT NULL,
  `user_seq` int NOT NULL,
  PRIMARY KEY (`my_keyword_seq`),
  KEY `fk_tb_my_keyword_tb_user_idx` (`user_seq`),
  CONSTRAINT `fk_tb_my_keyword_tb_user` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_my_keyword`
--

LOCK TABLES `tb_my_keyword` WRITE;
/*!40000 ALTER TABLE `tb_my_keyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_my_keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_my_schedule`
--

DROP TABLE IF EXISTS `tb_my_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_my_schedule` (
  `my_schedule_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `my_schedule_date` int NOT NULL,
  `my_schedule_time` int NOT NULL,
  `my_schedule_content` text NOT NULL,
  `my_schedule_registed_at` timestamp NOT NULL,
  `my_schedule_updated_at` timestamp NOT NULL,
  PRIMARY KEY (`my_schedule_seq`),
  KEY `fk_tb_my_schedule_tb_user1_idx` (`user_seq`),
  CONSTRAINT `fk_tb_my_schedule_tb_user1` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_my_schedule`
--

LOCK TABLES `tb_my_schedule` WRITE;
/*!40000 ALTER TABLE `tb_my_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_my_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_my_stamp`
--

DROP TABLE IF EXISTS `tb_my_stamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_my_stamp` (
  `my_stamp_seq` int NOT NULL AUTO_INCREMENT,
  `user_seq` int NOT NULL,
  `stamp_seq` int NOT NULL,
  `my_stamp_registed_at` timestamp NOT NULL,
  PRIMARY KEY (`my_stamp_seq`),
  KEY `fk_tb_my_stamp_tb_user1_idx` (`user_seq`),
  KEY `fk_tb_my_stamp_tb_stamp1_idx` (`stamp_seq`),
  CONSTRAINT `fk_tb_my_stamp_tb_stamp1` FOREIGN KEY (`stamp_seq`) REFERENCES `tb_stamp` (`stamp_seq`),
  CONSTRAINT `fk_tb_my_stamp_tb_user1` FOREIGN KEY (`user_seq`) REFERENCES `tb_user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_my_stamp`
--

LOCK TABLES `tb_my_stamp` WRITE;
/*!40000 ALTER TABLE `tb_my_stamp` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_my_stamp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_stamp`
--

DROP TABLE IF EXISTS `tb_stamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_stamp` (
  `stamp_seq` int NOT NULL AUTO_INCREMENT,
  `heritage_seq` int NOT NULL,
  `stamp_img_url` text NOT NULL,
  `stamp_title` varchar(15) NOT NULL,
  `stamp_text` varchar(100) NOT NULL,
  PRIMARY KEY (`stamp_seq`),
  KEY `fk_tb_stamp_tb_heritage1_idx` (`heritage_seq`),
  CONSTRAINT `fk_tb_stamp_tb_heritage1` FOREIGN KEY (`heritage_seq`) REFERENCES `tb_heritage` (`heritage_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_stamp`
--

LOCK TABLES `tb_stamp` WRITE;
/*!40000 ALTER TABLE `tb_stamp` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_stamp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `user_seq` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `user_nickname` varchar(20) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_birth` varchar(10) NOT NULL,
  `social_login_type` varchar(10) NOT NULL,
  `user_gender` char(1) NOT NULL,
  `profile_img_url` varchar(100) NOT NULL,
  `jwt_token` varchar(50) NOT NULL,
  `fcm_token` varchar(50) NOT NULL,
  `user_registed_at` timestamp NOT NULL,
  `user_updated_at` timestamp NOT NULL,
  `is_deleted` char(1) NOT NULL,
  PRIMARY KEY (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (8,'1234@naver.com','JS','{bcrypt}$2a$10$.wqB6phyxkPQKWwh1boYJ.NWj4AQm/bSPXnu964hXi3NytfEGzuMy','19960917','none','M','link','aaa','bbb','2022-07-26 04:42:13','2022-07-26 04:42:13','N'),(9,'2345@naver.com','JS','{bcrypt}$2a$10$qhNxYiMJTK9ptzZCJoi2QOE43JJrMIlN9GIxEZi.u6UEY4AGuu82m','19960917','none','M','link','aaa','bbb','2022-07-26 04:42:16','2022-07-26 04:42:16','N'),(10,'kkkk@naver.com','JS','{bcrypt}$2a$10$QQWsNbpO7Ye2VG8j2Vx4VurZktlGsItD6Gae2SFVYC37SLvrRX5VC','19960917','none','M','link','aaa','bbb','2022-07-26 04:42:18','2022-07-26 04:42:18','N'),(11,'bbbbb@naver.com','JSsss','{bcrypt}$2a$10$hkg2zWWFZvCLjMYOitAjQuvpMH3Ygdvtg3TmHiDxQVroA4oDV.3s.','19960917','none','M','link','aaa','bbb','2022-07-26 06:00:37','2022-07-26 06:00:37','N');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-27  0:19:52
