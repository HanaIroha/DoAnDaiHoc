-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: mtshop
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
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banners` (
  `id_banner` int NOT NULL AUTO_INCREMENT,
  `upper_title` varchar(200) DEFAULT NULL,
  `main_title` varchar(200) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `link_navigate` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_banner`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (28,'Khuyến mãi duy nhất','Mừng xuân 2022','1652342694694qc dien thoai.jpg','https://www.youtube.com/watch?v=-nTgh7FhMUA'),(31,'Cùng loạt ưu đãi hấp dẫn','Kim Ji Won trở thành đại sứ Samsung','1652611400177DDD01_(5).jpg','https://www.youtube.com/watch?v=cLEGei-7NpE&t=5768s&ab_channel=Mininero%EB%AF%B8%EB%8B%88%EB%84%A4%EB%A1%9C');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (22,'Điện thoại',NULL,'2022-03-12 19:42:01','2022-05-12 08:14:24'),(23,'Tai nghe',NULL,'2022-03-12 21:59:36','2022-05-12 08:14:28');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id_comment` int NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_product` int NOT NULL,
  `id_parent_comment` int DEFAULT NULL,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `FkComment2_idx` (`id_product`),
  CONSTRAINT `FkComment2` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (5,'admin',9,NULL,'Nice','2022-03-30 06:13:00','2022-03-30 06:13:00');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id_image` int NOT NULL AUTO_INCREMENT,
  `id_product` int NOT NULL,
  `image_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_image`),
  KEY `FKImage_idx` (`id_product`),
  CONSTRAINT `FKImage` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (2,9,'1647179043803memetuoigi.jpg'),(3,9,'1648622524017Untitled.png'),(4,9,'1652315874741DDD01_(5).jpg'),(5,13,'165231602947105★백화점(로고).jpg');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `full_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phonenumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(191) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `activation_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Admin Iroha','Tuyên Quang','03495123','admin@localhost','1647395231556IU8.jpg',_binary '','vi',NULL,NULL,'system',NULL,NULL,'admin','2022-05-11 22:58:43'),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User',NULL,'hanairoha2ktest@gmail.com','1647179030239avtIU.jpg',_binary '','vi',NULL,'BtL2R5rTwTVo9caTaOP3','system',NULL,'2022-03-12 19:23:42','admin','2022-03-15 11:05:13'),(3,'test1','$2a$10$K70dycc4nz5fnJlQgocCsOdQcGaJKrEV8pWXSOnYehE5gCVtWOBty','Phạm Minh Tư',NULL,NULL,'pmsccs@gmail.com',NULL,_binary '','vi',NULL,NULL,'anonymousUser','2022-03-14 23:52:08',NULL,'admin','2022-05-11 22:26:06'),(4,'iroha2k','$2a$10$XzFokjnFMKaIXobx94knqOwBThacGqf6jaV0Hj265Q/FBfUONdrQu','Hana Iroha',NULL,NULL,'pmt04122000@gmail.com',NULL,_binary '','vi',NULL,NULL,'anonymousUser','2022-03-15 20:41:18',NULL,'anonymousUser','2022-03-15 21:28:31');
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id_order_detail` int NOT NULL AUTO_INCREMENT,
  `id_order` int NOT NULL,
  `id_product` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_order_detail`),
  KEY `FKOrderDetail1_idx` (`id_order`),
  CONSTRAINT `FKOrderDetail1` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (3,3,9,23,2444,'2022-03-15 21:56:07','2022-03-15 21:56:07'),(4,9,9,1,95000,'2022-03-16 04:12:10','2022-03-16 04:12:10'),(5,10,9,1,95000,'2022-03-16 04:12:47','2022-03-16 04:12:47'),(6,11,9,1,95000,'2022-03-16 04:13:48','2022-03-16 04:13:48'),(7,12,9,1,95000,'2022-03-16 04:17:06','2022-03-16 04:17:06'),(8,13,11,2,0,'2022-03-30 06:24:18','2022-03-30 06:24:18'),(9,13,9,1,8550000,'2022-03-30 06:24:18','2022-03-30 06:24:18'),(10,14,9,99,8550000,'2022-03-30 06:26:50','2022-03-30 06:26:50'),(11,15,9,1,8550000,'2022-05-12 08:40:51','2022-05-12 08:40:51'),(12,15,11,1,0,'2022-05-12 08:40:51','2022-05-12 08:40:51');
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `order_code` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_status` int DEFAULT NULL,
  `id_payment` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_order`),
  KEY `FKOder2_idx` (`id_payment`),
  CONSTRAINT `FKOrder2` FOREIGN KEY (`id_payment`) REFERENCES `payments` (`id_payment`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (3,'admin','user1647381366816','User','23',NULL,'askmdaksmksemkasesaesaesaeaseaseaseaseaseaseeaseasdasdasdasdasdasdas',1,1,'2022-03-15 21:56:07','2022-03-15 23:41:35'),(9,'admin','admin1647403928284','Administrator','03495123',NULL,'asdasd',2,1,'2022-03-16 04:12:08','2022-05-12 05:20:22'),(10,'admin','admin1647403965745','Administrator','03495123',NULL,'asd',1,2,'2022-03-16 04:12:46','2022-05-15 17:06:12'),(11,'admin','admin1647404026878','Administrator','03495123',NULL,'sd',1,2,'2022-03-16 04:13:47','2022-05-15 17:12:33'),(12,'iroha2k','iroha2k1647404223476','Hana Iroha','1231243',NULL,'asdasd',1,1,'2022-03-16 04:17:03','2022-05-15 18:06:31'),(13,'admin','admin1648621454567','Administrator','03495123',NULL,'Hà nội',1,1,'2022-03-30 06:24:15','2022-05-15 18:15:06'),(14,'iroha2k','iroha2k1648621608977','Hana Iroha','0349413312',NULL,'Tuyên Quang',0,1,'2022-03-30 06:26:49','2022-05-15 18:19:58'),(15,'admin','admin1652344843466','Admin Iroha','03495123',NULL,'Tuyên Quang',1,1,'2022-05-12 08:40:43','2022-05-15 22:04:25');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id_payment` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_payment`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'COD','Trả tiền khi nhận hàng','2022-03-12 19:56:13','2022-05-12 08:13:19'),(2,'Chuyển khoản','Trả tiền trước','2022-03-12 19:57:12','2022-05-12 08:13:29');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producers`
--

DROP TABLE IF EXISTS `producers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producers` (
  `id_producer` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id_producer`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producers`
--

LOCK TABLES `producers` WRITE;
/*!40000 ALTER TABLE `producers` DISABLE KEYS */;
INSERT INTO `producers` VALUES (1,'ASUS','2022-03-12 20:37:04','2022-05-12 08:17:28'),(2,'GIGABYTE','2022-05-11 20:54:20','2022-05-12 08:17:33');
/*!40000 ALTER TABLE `producers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_specs`
--

DROP TABLE IF EXISTS `product_specs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_specs` (
  `id_product_spec` int NOT NULL AUTO_INCREMENT,
  `id_product` int NOT NULL,
  `keyname` varchar(100) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_product_spec`),
  KEY `PKProductSpec1_idx` (`id_product`),
  CONSTRAINT `PKProductSpec1` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_specs`
--

LOCK TABLES `product_specs` WRITE;
/*!40000 ALTER TABLE `product_specs` DISABLE KEYS */;
INSERT INTO `product_specs` VALUES (1,13,'a','b'),(2,13,'a','1'),(3,16,'sd','xx'),(4,16,'zx','xz'),(6,17,'xc','sdsd'),(7,17,'bnb','vbvb'),(8,19,'Bước sóng','5k');
/*!40000 ALTER TABLE `product_specs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productdetails`
--

DROP TABLE IF EXISTS `productdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productdetails` (
  `IdProductDetail` int NOT NULL AUTO_INCREMENT,
  `IdProduct` int NOT NULL,
  `ROM` int DEFAULT NULL,
  `Color` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ImportQuantity` int DEFAULT NULL,
  `Quantity` int DEFAULT NULL,
  `ImportPrice` int DEFAULT NULL,
  `SalePrice` int DEFAULT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime NOT NULL,
  PRIMARY KEY (`IdProductDetail`),
  KEY `FKProductDetail1_idx` (`IdProduct`),
  CONSTRAINT `FKProductDetail1` FOREIGN KEY (`IdProduct`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productdetails`
--

LOCK TABLES `productdetails` WRITE;
/*!40000 ALTER TABLE `productdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `productdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id_product` int NOT NULL AUTO_INCREMENT,
  `id_category` int NOT NULL,
  `id_producer` int NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `price` int DEFAULT NULL,
  `sale_percent` int DEFAULT NULL,
  `last_price` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `information` longtext COLLATE utf8_unicode_ci,
  `information_details` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_disable` tinyint DEFAULT '1',
  PRIMARY KEY (`id_product`),
  KEY `FK1_idx` (`id_category`),
  KEY `FK2_idx` (`id_producer`),
  CONSTRAINT `FKProduct1` FOREIGN KEY (`id_category`) REFERENCES `categories` (`id_category`),
  CONSTRAINT `FKProduct2` FOREIGN KEY (`id_producer`) REFERENCES `producers` (`id_producer`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (9,22,1,'Xiaomi Redmi Note 10 Pro','1648620584882tong-hop-tat-tan-tat-ve-redmi-note-10-series-vua-d-1.png','01Xiaomi',9000000,5,8550000,26,NULL,'<p>sdfsdf</p><p><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/190928_%EB%A9%94%EB%94%94%ED%9E%90_%ED%8C%AC%EC%82%AC%EC%9D%B8%ED%9A%8C_%EA%B9%80%EC%A7%80%EC%9B%90.jpg/640px-190928_%EB%A9%94%EB%94%94%ED%9E%90_%ED%8C%AC%EC%82%AC%EC%9D%B8%ED%9A%8C_%EA%B9%80%EC%A7%80%EC%9B%90.jpg\" alt=\"\" width=\"\" height=\"\"></p>','2022-03-13 04:51:44','2022-05-15 18:19:58',0),(10,22,1,'asdas','1647148413648avtIU2.png','asd',50000,5,NULL,NULL,NULL,'zxcs','2022-03-13 04:54:35','2022-03-13 09:02:10',1),(11,22,2,'Iphone 13','1648620626151lo-gia-iphone-13-re-nhat-bao-nhieu.jpg','01Iphone',29000000,100,0,213115,NULL,'asdasxzczxczxczxwasedasd','2022-03-14 21:06:39','2022-05-15 18:15:01',0),(12,23,1,'abc',NULL,'sdsdsd',100000,5,NULL,50,'sddsadasd','<p>sadasdsad</p>','2022-05-11 18:09:17','2022-05-11 18:58:54',1),(13,23,1,'213213',NULL,'ádasd',123123,112,-14772,50,'ád','<p>ád</p>','2022-05-11 18:16:23','2022-05-12 08:15:16',1),(14,22,1,'zbc','16522963237702109450733_5ykvopqG_1d2683e988fe29bb3e0a6a758c7f7288dd19028b.jpg','asd',50000,2321321,NULL,50,'ád','<p>xxx</p>','2022-05-11 19:12:04','2022-05-11 19:15:54',1),(15,23,1,'Phạm Minh Tư','1652296425888DSC00404.jpg','asd',100000,5,NULL,50,'s','<p>s</p>','2022-05-11 19:13:46','2022-05-12 08:15:15',1),(16,23,1,'zxczxc','1652296532925DSC00404.jpg','xcxc',9000000,5,8550000,50,'ád','<p>ss</p>','2022-05-11 19:15:33','2022-05-12 08:15:17',1),(17,23,1,'xxxx','1652298754097DDD01_(5).jpg','zzz',50000,5,47500,50,'213123','<p>asdasd</p>','2022-05-11 19:52:34','2022-05-12 08:15:18',1),(18,22,1,'zbc','16522987732662109450733_b4vtcV70_10071a29f525b39a2be2d0f505f41da2f10ffa0c.jpg','asd',100000,5,95000,50,'asd','<p>asd</p>','2022-05-11 19:52:53','2022-05-12 08:15:19',1),(19,23,1,'Tai nghe Bluetooth','1652343417812tai-nghe-bluetooth-kanen-k6-den-2-org.jpg','BLTX020',250000,0,250000,10,'Tai nghe không giây','<p>Nghe cực đã</p>','2022-05-12 08:16:58','2022-05-12 08:16:58',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `IdUser` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Fname` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Avatar` varchar(100) DEFAULT NULL,
  `LastLoginDatetime` datetime DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT '1',
  `Role` varchar(10) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `idusers_UNIQUE` (`IdUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-19 23:07:38
