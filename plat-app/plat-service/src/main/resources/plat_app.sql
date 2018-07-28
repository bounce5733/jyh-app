-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: plat_app
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Current Database: `plat_app`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `plat_app` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `plat_app`;

--
-- Table structure for table `api`
--

DROP TABLE IF EXISTS `api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api` (
  `ID` char(32) NOT NULL,
  `SERVICE_ID` char(32) NOT NULL,
  `NAME` varchar(45) NOT NULL,
  `METHOD` char(32) NOT NULL,
  `PATH` varchar(255) NOT NULL,
  `PARAM` text,
  `DATA` text,
  `DESCN` varchar(45) DEFAULT NULL,
  `UPDATED_BY` char(32) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` char(32) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.api.service_id_idx` (`SERVICE_ID`),
  CONSTRAINT `FK.API.SERVICE_ID` FOREIGN KEY (`SERVICE_ID`) REFERENCES `service` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api`
--

LOCK TABLES `api` WRITE;
/*!40000 ALTER TABLE `api` DISABLE KEYS */;
INSERT INTO `api` VALUES ('5fd79fd3b594083d8309a1d3f08d558a','07251ca5359ed61790ff867645e504e8','更新用户','d192ef6b8a0f4169a98e5074f255a8ce','/sys/user/sys/user/sys/user/sys/user','','',NULL,'null','2018-03-14 14:12:42','16da73f6973f443ba1f754ccb294eb98','2018-03-09 15:50:49'),('e8cbb1439d3ab16380dd970e2dcafd90','07251ca5359ed61790ff867645e504e8','删除用户','30484258aad24fecb298f9888563dc13','/sys/user','null','null',NULL,'null','2018-03-14 11:26:34','16da73f6973f443ba1f754ccb294eb98','2018-03-09 15:38:39');
/*!40000 ALTER TABLE `api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `ID` char(32) NOT NULL,
  `APPID` varchar(45) NOT NULL COMMENT '应用标识',
  `OWNER` char(32) NOT NULL COMMENT '所属大类',
  `NAME` varchar(45) NOT NULL COMMENT '名称',
  `PWD` char(32) DEFAULT NULL COMMENT '密码:md5',
  `CONF` text COMMENT 'YAML配置信息',
  `DELETED` char(1) NOT NULL DEFAULT 'F',
  `DESCN` text,
  `UPDATED_BY` char(32) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` char(32) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.APP.OWNER_idx` (`OWNER`),
  CONSTRAINT `FK.APP.OWNER` FOREIGN KEY (`OWNER`) REFERENCES `app_type` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app`
--

LOCK TABLES `app` WRITE;
/*!40000 ALTER TABLE `app` DISABLE KEYS */;
INSERT INTO `app` VALUES ('76a66724c5624649b5f2bb8ccea97563','plat_attach','3d4edff05f40430cb75d186621a19f0e','文件服务','cb388433500c9eadac2fc793643dd6cd','server: \n  port: 8314\nspring:\n  application:\n    name: plat-attach\n  profiles:  \n    active: dev\n  session:\n    store-type: redis\n  cache:\n    cache-names: cache1\napp:\n  dfs:\n    tracker_servers: 10.88.20.25:22122,10.88.20.26:22122\n---\nspring:\n  profiles: dev\n  datasource:\n    url: jdbc:mysql://10.88.20.89:3306/plat_attach?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false\n    username: root\n    password: passw0rd\n    driver-class-name: com.mysql.jdbc.Driver\n    \n    max-idle: 20\n    min-idle: 5\n    initial-size: 5\n    validation-query: SELECT 1\n    test-on-borrow: false\n    test-while-idle: true\n\n  redis:\n    database: 0\n    host: 10.88.20.89\n    port: 6379\n    letture:\n      pool:\n        max-active: 50\n        max-idle: 10\n        min-idle: 0\n        max-wait: 5000\n\n  rabbitmq:\n    host: 10.88.20.15\n    port:  5672\n    username:  guest\n    password:  guest\n\ndubbo:\n  zookeeper:\n    ip: 10.88.20.15\n    port: 2181\n\n---\nspring:\n  profiles: prod','F','分布式文件系统元信息管理','null','2018-03-29 17:00:20','160503006','2018-02-24 05:57:36'),('91d691265ad74351aac7523068edc4dd','plat_console','3d4edff05f40430cb75d186621a19f0e','统一控制台','754443fd7ba24127b64e4d975433a86c','server: \n  port: 8312\nspring:\n  application:\n    name: plat-console\n  profiles:  \n    active: dev\n  session:\n    store-type: redis\n  cache:\n    cache-names: cache1\n---\nspring:\n  profiles: dev\n  datasource:\n    url: jdbc:mysql://192.168.3.5:3306/plat_console?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false\n    username: root\n    password: passw0rd\n    driver-class-name: com.mysql.jdbc.Driver\n    \n    max-idle: 20\n    min-idle: 5\n    initial-size: 5\n    validation-query: SELECT 1\n    test-on-borrow: false\n    test-while-idle: true\n\n  redis:\n    database: 0\n    host: 192.168.3.5\n    port: 6379\n    letture:\n      pool:\n        max-active: 50\n        max-idle: 10\n        min-idle: 0\n        max-wait: 5000\n\n  rabbitmq:\n    host: 192.168.3.5\n    port:  5672\n    username:  admin\n    password:  passw0rd\n\ndubbo:\n  zookeeper:\n    ip: 192.168.3.5\n    port: 2181\n---\nspring:\n  profiles: prod','F','','null','2018-03-29 09:39:16','160503006','2018-02-10 15:42:55');
/*!40000 ALTER TABLE `app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_type`
--

DROP TABLE IF EXISTS `app_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_type` (
  `ID` char(32) NOT NULL,
  `NAME` varchar(45) NOT NULL,
  `DELETED` char(1) NOT NULL DEFAULT 'F',
  `DESCN` text,
  `UPDATED_BY` char(32) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` char(32) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用大类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_type`
--

LOCK TABLES `app_type` WRITE;
/*!40000 ALTER TABLE `app_type` DISABLE KEYS */;
INSERT INTO `app_type` VALUES ('0e4aa2a7a576c6011fa7763e429e4275','3333','T','33333',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:50:17'),('12b50a26567b2faaca5547867b90319c','2222','T','2222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:24:56'),('24169ec384eabe55840700a9edc4c3d0','11','T','111','16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:21:31','16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:21:26'),('261af340b8b986741cf69af19bfd5248','3333','T','333',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:44:08'),('34fcd5c922e9bd02927461c65a0b47da','11','T','11',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:18:59'),('35f460da68c785b4aa55c9e2811db000','2222','T','2222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:24:21'),('3d4edff05f40430cb75d186621a19f0e','基础平台','F','**1111222**','null','2018-03-29 09:38:34','160503006','2018-02-10 15:41:56'),('4341f8204c5f4f5815b86e19cbcbaca0','111111','T','222222',NULL,NULL,'null','2018-03-29 09:20:22'),('4abd8511a706d8440085537d0fd672bd','222','T','222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:21:41'),('4fc6b778d5cf4849bd3c929b6689281a','333','T','333',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:13:39'),('522fe3fc48b86902b815a38cf04e4c90','dddd','T','ddddd',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:33:26'),('7796aa002e37d38e09e427892cbcb2e5','22222','T','2222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:36:55'),('98a11dfa7d074b1a8869ba72dcac8781','222','T','22',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:08:06'),('ac359f01f2d31ff6425c355c1a28f873','22222','T','22222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:42:13'),('b3a8deeb9d30b0a5be4ea0c723e435e4','111','T','11',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:42:06'),('b67bf595ef4381750f4aea7e5f3a612e','222','T','222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:34:50'),('cfe2d8582c164754988f158b2b26ef4b','22','T','222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:04:41'),('d9ca8886ce9766029ddb6a4c9a743ee5','111','T','111',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:28:55'),('e5e6039b05ab4a81ae7cde911d90aae7','3333','T','333','16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:13:18','16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:10:26'),('f42a3d0323a842f8b61d9521506bf76c','222','T','222',NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-12 10:07:31');
/*!40000 ALTER TABLE `app_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `ID` char(32) NOT NULL,
  `APP_ID` char(32) NOT NULL COMMENT '所属应用',
  `NAME` varchar(45) NOT NULL,
  `DESCN` varchar(255) DEFAULT NULL,
  `UPDATED_BY` char(32) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` char(32) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.APP_SERVICE.APP_ID_idx` (`APP_ID`),
  CONSTRAINT `FK.SERVICE.APP_ID` FOREIGN KEY (`APP_ID`) REFERENCES `app` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES ('07251ca5359ed61790ff867645e504e8','76a66724c5624649b5f2bb8ccea97563','用户服务','','null','2018-03-12 21:18:19','16da73f6973f443ba1f754ccb294eb98','2018-03-09 13:24:28'),('4df0a1175db13c6bf4443e14b921a7cd','76a66724c5624649b5f2bb8ccea97563','22',NULL,NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-10 20:13:33'),('a806e7f81b706d33068377b48804d74b','76a66724c5624649b5f2bb8ccea97563','11',NULL,NULL,NULL,'16da73f6973f443ba1f754ccb294eb98','2018-03-10 20:12:58');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 16:33:55
