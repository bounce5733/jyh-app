-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: 192.168.3.9    Database: plat_console
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
-- Current Database: `plat_console`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `plat_console` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `plat_console`;

--
-- Table structure for table `sys_code`
--

DROP TABLE IF EXISTS `sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_code` (
  `CODE` varchar(32) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `CREATED_TIME` char(32) NOT NULL,
  `CREATED_BY` varchar(45) NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_code`
--

LOCK TABLES `sys_code` WRITE;
/*!40000 ALTER TABLE `sys_code` DISABLE KEYS */;
INSERT INTO `sys_code` VALUES ('http_method','HTTP方法','2018-03-07 16:52:27','admin');
/*!40000 ALTER TABLE `sys_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_code_item`
--

DROP TABLE IF EXISTS `sys_code_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_code_item` (
  `ID` char(32) NOT NULL COMMENT 'UUID主键',
  `PID` char(32) NOT NULL DEFAULT '0' COMMENT '父主键',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `TYPE` char(32) NOT NULL COMMENT '所属类型',
  `SORT` tinyint(2) NOT NULL COMMENT '排序',
  `UPDATED_BY` varchar(45) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_TIME` char(19) NOT NULL,
  `CREATED_BY` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.BAS_CODE_ITEM.TYPE_idx` (`TYPE`),
  CONSTRAINT `FK.BAS_CODE_ITEM.TYPE` FOREIGN KEY (`TYPE`) REFERENCES `sys_code` (`CODE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编码子项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_code_item`
--

LOCK TABLES `sys_code_item` WRITE;
/*!40000 ALTER TABLE `sys_code_item` DISABLE KEYS */;
INSERT INTO `sys_code_item` VALUES ('30484258aad24fecb298f9888563dc13','0','patch','http_method',2,'admin','2018-05-05 09:02:29','2018-03-07 16:53:09','admin'),('3fa8effa370849bb8b75ffeef4c0eb4e','0','put','http_method',4,'admin','2018-05-04 22:33:21','2018-03-07 16:53:25','admin'),('4b89e085a5314d549184afaaefc9a869','0','get','http_method',1,'admin','2018-05-04 22:33:15','2018-03-07 16:52:44','admin'),('6c13c03291174c6dbbd8c27ae941f3c3','0','delete','http_method',3,'admin','2018-03-09 17:30:58','2018-03-07 16:53:16','admin'),('d192ef6b8a0f4169a98e5074f255a8ce','0','post','http_method',5,'admin','2018-03-14 11:10:01','2018-03-07 16:52:53','admin');
/*!40000 ALTER TABLE `sys_code_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ID` char(32) NOT NULL COMMENT 'ID',
  `NAME` varchar(64) NOT NULL COMMENT '名称',
  `DESCN` varchar(255) DEFAULT NULL COMMENT '描述',
  `ENABLED` char(1) NOT NULL DEFAULT 'T' COMMENT '是否启动 T|F',
  `UPDATED_BY` varchar(45) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` varchar(45) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('e7b043dd693b418ab8ce71af1a08c850','super_admin','超级管理员','T','160503006','2018-01-19 16:20:24','160503006','2018-01-09 10:53:26');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `ID` char(32) NOT NULL,
  `ROLE_ID` char(32) NOT NULL,
  `MENU_ID` char(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.SYS_ROLE_MENU.ROLEID_idx` (`ROLE_ID`),
  CONSTRAINT `FK.SYS_ROLE_MENU.ROLEID` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES ('23a762765c3b49f2821d66847f3fd891','e7b043dd693b418ab8ce71af1a08c850','console_code'),('3bd91bc47f40474c902bfb74a56f0fbb','e7b043dd693b418ab8ce71af1a08c850','console_user'),('8d759db74a4b4dfd887a95011d09fa73','e7b043dd693b418ab8ce71af1a08c850','console_role'),('fe9f0f5856b147b380b5b524937eada6','e7b043dd693b418ab8ce71af1a08c850','console');
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_user` (
  `ID` char(32) NOT NULL,
  `USER_ID` char(32) NOT NULL COMMENT '工号',
  `ROLE_ID` char(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK.SYS_ROLE_USER.ROLEID_idx` (`ROLE_ID`),
  CONSTRAINT `FK.SYS_ROLE_USER.ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES ('940210d7fc1641d0b75c6497494a091e','16da73f6973f443ba1f754ccb294eb98','e7b043dd693b418ab8ce71af1a08c850'),('fcbc91177d6640bcbc0767073fbf8986','2531896e9a8e4913b77dbda63efbedf0','e7b043dd693b418ab8ce71af1a08c850');
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_set`
--

DROP TABLE IF EXISTS `sys_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_set` (
  `CODE` varchar(32) NOT NULL COMMENT '设置类别',
  `CONTENT` mediumtext NOT NULL COMMENT 'JSON内容',
  `UPDATED_BY` varchar(45) NOT NULL COMMENT '更新人',
  `UPDATED_TIME` char(19) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_set`
--

LOCK TABLES `sys_set` WRITE;
/*!40000 ALTER TABLE `sys_set` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `ID` char(32) NOT NULL,
  `NAME` varchar(45) NOT NULL,
  `ACCOUNT` varchar(45) NOT NULL,
  `PWD` varchar(45) NOT NULL,
  `UPDATED_BY` varchar(45) DEFAULT NULL,
  `UPDATED_TIME` char(19) DEFAULT NULL COMMENT '更新时间',
  `CREATED_BY` varchar(45) NOT NULL,
  `CREATED_TIME` char(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('16da73f6973f443ba1f754ccb294eb98','管理员','admin','202cb962ac59075b964b07152d234b70','admin','2018-05-04 16:32:00','admin','2018-02-24 05:57:36'),('2531896e9a8e4913b77dbda63efbedf0','江永华','jiangyonghua','202cb962ac59075b964b07152d234b70','admin','2018-05-03 12:57:03','admin','2018-02-27 17:08:59');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-28 22:40:09
