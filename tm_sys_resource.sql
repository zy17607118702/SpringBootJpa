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

 Date: 09/07/2019 19:30:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tm_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `tm_sys_resource`;
CREATE TABLE `tm_sys_resource`  (
  `tm_sys_resource_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `mark_status` bit(1) NULL DEFAULT NULL COMMENT '启用',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `parent_res_id` bigint(11) NULL DEFAULT NULL COMMENT '上级菜单id',
  `res_level` int(5) NULL DEFAULT NULL COMMENT '菜单等级',
  `res_name_c` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单中文名',
  `res_name_e` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单英文名',
  `res_path` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `res_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜单类型',
  `seq_no` int(5) NOT NULL,
  `is_leaf` bit(1) NOT NULL COMMENT '是否叶节点',
  PRIMARY KEY (`tm_sys_resource_id`) USING BTREE,
  UNIQUE INDEX `UK1tb5b0slommvwxbksocpq42ma`(`res_type`, `res_name_c`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '资源信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tm_sys_resource
-- ----------------------------
INSERT INTO `tm_sys_resource` VALUES (1, '00000', '2019-07-07 13:59:44', b'1', '00000', '2019-07-07 13:59:52', NULL, 0, 'web端', 'web', '-', 'WEB', 1, b'0');
INSERT INTO `tm_sys_resource` VALUES (2, '00000', '2019-07-08 19:47:45', b'1', '00000', '2019-07-08 19:47:56', NULL, 0, 'pad端', 'pad', '-', 'DEVICE', 2, b'0');
INSERT INTO `tm_sys_resource` VALUES (3, '00000', '2019-07-08 19:49:10', b'1', '00000', '2019-07-08 19:49:19', 1, 1, '系统管理', 'system', '/system', 'WEB-CAT', 1, b'0');
INSERT INTO `tm_sys_resource` VALUES (4, '00000', '2019-07-08 19:50:29', b'1', '00000', '2019-07-08 19:50:35', 3, 2, '人员管理', 'usermanage', '-', 'FUNC', 1, b'0');
INSERT INTO `tm_sys_resource` VALUES (5, '00000', '2019-07-08 20:02:23', b'1', '00000', '2019-07-08 20:02:30', 4, 3, '人员信息', 'user', '/system/user', 'URL', 1, b'1');
INSERT INTO `tm_sys_resource` VALUES (6, '00000', '2019-07-08 20:48:49', b'1', '00000', '2019-07-08 20:48:57', 1, 1, '业务主数据', 'mainData', '/mainData', 'WEB-CAT', 2, b'0');
INSERT INTO `tm_sys_resource` VALUES (7, '00000', '2019-07-08 20:50:28', b'1', '00000', '2019-07-08 20:50:35', 4, 3, '权限信息', 'roleManage', '/system/role', 'URL', 2, b'1');
INSERT INTO `tm_sys_resource` VALUES (8, '00000', '2019-07-08 20:54:15', b'1', '00000', '2019-07-08 20:54:21', 6, 2, '零件数据', 'part Data', '-', 'FUNC', 1, b'0');
INSERT INTO `tm_sys_resource` VALUES (9, '00000', '2019-07-08 20:55:44', b'1', '00000', '2019-07-08 20:55:50', 8, 3, '零件信息', 'part ', '/mainData/part', 'URL', 1, b'1');

SET FOREIGN_KEY_CHECKS = 1;
