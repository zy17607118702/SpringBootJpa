/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : localhost:3306
 Source Schema         : mysql

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 09/07/2019 19:30:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tr_sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tr_sys_role_resource`;
CREATE TABLE `tr_sys_role_resource`  (
  `tr_sys_role_resource_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `mark_status` bit(1) NULL DEFAULT NULL COMMENT '启用',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `tm_sys_resource_id` bigint(11) NOT NULL COMMENT '资源信息id',
  `tm_sys_role_id` bigint(11) NOT NULL COMMENT '角色信息id',
  PRIMARY KEY (`tr_sys_role_resource_id`) USING BTREE,
  INDEX `FKjriaau8vrbpbd6av40myq9cj0`(`tm_sys_resource_id`) USING BTREE,
  INDEX `FKee1y4xrltg6mcdo429n9qpkvu`(`tm_sys_role_id`) USING BTREE,
  CONSTRAINT `FKee1y4xrltg6mcdo429n9qpkvu` FOREIGN KEY (`tm_sys_role_id`) REFERENCES `tm_sys_role` (`tm_sys_role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjriaau8vrbpbd6av40myq9cj0` FOREIGN KEY (`tm_sys_resource_id`) REFERENCES `tm_sys_resource` (`tm_sys_resource_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tr_sys_role_resource
-- ----------------------------
INSERT INTO `tr_sys_role_resource` VALUES (1, '00000', '2019-07-09 08:41:20', b'1', '00000', '2019-07-09 08:41:26', 5, 1);
INSERT INTO `tr_sys_role_resource` VALUES (2, '00000', '2019-07-09 08:42:02', b'1', '00000', '2019-07-09 08:42:07', 7, 1);
INSERT INTO `tr_sys_role_resource` VALUES (3, '00000', '2019-07-09 08:42:26', NULL, '00000', '2019-07-09 08:42:31', 9, 1);

SET FOREIGN_KEY_CHECKS = 1;
