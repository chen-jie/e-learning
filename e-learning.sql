/*
Navicat MySQL Data Transfer

Source Server         : localhsot
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : e-learning

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2017-01-16 14:09:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `type` varchar(10) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '权限管理', 'menu', null, '', '');
INSERT INTO `permission` VALUES ('2', '资源管理', 'menu', '1', 'admin/perm', 'admin/perm');
INSERT INTO `permission` VALUES ('3', '角色管理', 'menu', '1', 'admin/role', 'admin/role');
INSERT INTO `permission` VALUES ('4', '用户管理', 'menu', '1', 'admin/user', 'admin/user');
INSERT INTO `permission` VALUES ('5', '资源列表', 'action', '2', 'admin/perm/list', 'admin/perm/list');
INSERT INTO `permission` VALUES ('6', '添加资源', 'action', '2', 'admin/perm/add', 'admin/perm/add');
INSERT INTO `permission` VALUES ('7', '编辑资源', 'action', '2', 'admin/perm/edit', 'admin/perm/edit');
INSERT INTO `permission` VALUES ('8', '删除资源', 'action', '2', 'admin/perm/delete', 'admin/perm/delete');
INSERT INTO `permission` VALUES ('14', 'aaddddddddsa', 'dddsa', null, 'a', 'aaddddddddddd');
INSERT INTO `permission` VALUES ('15', '角色列表', 'action', '3', 'admin/role/list', 'admin/role/list');
INSERT INTO `permission` VALUES ('16', '添加角色', 'action', '3', 'admin/role/add', 'admin/role/add');
INSERT INTO `permission` VALUES ('17', '编辑角色', 'action', '3', 'admin/role/edit', 'admin/role/edit');
INSERT INTO `permission` VALUES ('18', '分配资源', 'action', '3', 'admin/role/assign', 'admin/role/assignPerm');
INSERT INTO `permission` VALUES ('19', '分配用户', 'action', '3', 'admin/role/assginUser', 'admin/role/assginUser');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `en_name` varchar(50) NOT NULL,
  `discription` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', 'admin', '最高权限');
INSERT INTO `role` VALUES ('2', '教师', 'teacher', null);
INSERT INTO `role` VALUES ('3', '学生', 'student', '不明真相的吃瓜群众');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');
INSERT INTO `role_permission` VALUES ('3', '1', '3');
INSERT INTO `role_permission` VALUES ('4', '1', '4');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID自动递增',
  `username` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `email` varchar(50) NOT NULL,
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '如墨', '737050283@qq.com', null);
INSERT INTO `user` VALUES ('2', 'zhigui', 'E9E4BB30F8B5A752B6369DA7449FFE80', '志贵', 'zhigui@qq.com', null);
INSERT INTO `user` VALUES ('3', 'dsads', '0B4E7A0E5FE84AD35FB5F95B9CEEAC79', 'dsa', 'adsad@qq.ccsa', null);
INSERT INTO `user` VALUES ('4', 'aaaaaa', '0B4E7A0E5FE84AD35FB5F95B9CEEAC79', 'aaaaaa', 'aaaa@qqdas.sa', null);
INSERT INTO `user` VALUES ('7', 'dsadsa', 'C91C03EA6C46A86CBC019BE3D71D0A1A', 'dsadsa', 'dsadsa@v.com', null);
INSERT INTO `user` VALUES ('8', 'dddddd', '980AC217C6B51E7DC41040BEC1EDFEC8', 'dddddd', 'dddddd@qq.com', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  ` id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (` id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '3', '8');
