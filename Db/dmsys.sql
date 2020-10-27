/*
Navicat MySQL Data Transfer

Source Server         : PHP_MySQL
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : dmsys

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-10-27 18:48:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_dorm
-- ----------------------------
DROP TABLE IF EXISTS `tb_dorm`;
CREATE TABLE `tb_dorm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dorm_num` int(50) NOT NULL,
  `building_num` int(8) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `limit` int(2) NOT NULL,
  `in_num` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_building_id` (`building_num`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_refresh_token`;
CREATE TABLE `tb_refresh_token` (
  `id` varchar(20) NOT NULL,
  `refresh_token` text,
  `user_id` varchar(20) NOT NULL,
  `token_key` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_settings
-- ----------------------------
DROP TABLE IF EXISTS `tb_settings`;
CREATE TABLE `tb_settings` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `key` varchar(32) NOT NULL COMMENT '键',
  `value` varchar(512) NOT NULL COMMENT '值',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `student_num` bigint(11) unsigned NOT NULL COMMENT '学号',
  `student_name` varchar(20) NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `dorm_id` varchar(8) DEFAULT NULL,
  `address` text NOT NULL,
  `sex` varchar(2) NOT NULL,
  `dorm_num` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dorm_id` (`dorm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(65) NOT NULL COMMENT '密码',
  `roles` varchar(100) NOT NULL COMMENT '角色',
  `state` varchar(1) NOT NULL COMMENT '状态：0表示删除，1表示正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
