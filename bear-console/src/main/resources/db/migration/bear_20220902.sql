/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100803
 Source Host           : 172.16.185.129:3306
 Source Schema         : bear

 Target Server Type    : MySQL
 Target Server Version : 100803
 File Encoding         : 65001

 Date: 02/09/2022 17:20:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `client_secret_expires_at` timestamp NOT NULL DEFAULT '2038-01-09 03:14:07',
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2.0客户端';

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
BEGIN;
INSERT INTO `oauth2_registered_client` (`id`, `client_id`, `client_id_issued_at`, `client_secret`, `client_secret_expires_at`, `client_name`, `client_authentication_methods`, `authorization_grant_types`, `redirect_uris`, `scopes`, `client_settings`, `token_settings`) VALUES ('165bc9c9-9633-4eec-9706-b1d47f552bc7', 'bear-client', '0000-00-00 00:00:00', '$2a$10$EWNLbM9FFHxgJEVSWdqgEuZKcdMLcMwwW5z/l1dLqsqhHILifxkcC', '2038-01-09 03:14:07', 'bear', 'client_secret_basic', 'authorization_code', 'http://127.0.0.1:8080/callback', 'openid,console,market,portal', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');
COMMIT;

-- ----------------------------
-- Table structure for sys_audit
-- ----------------------------
DROP TABLE IF EXISTS `sys_audit`;
CREATE TABLE `sys_audit` (
  `id` bigint(20) unsigned NOT NULL COMMENT '标识用户的唯一ID',
  `entity_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '实体ID',
  `entity_name` varchar(64) NOT NULL DEFAULT '' COMMENT '实体名称',
  `op_name` varchar(128) NOT NULL DEFAULT '' COMMENT '操作类型',
  `comment` varchar(64) NOT NULL DEFAULT '' COMMENT '内容',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Delete timestamp based on milliseconds',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT '' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
  `modify_by` varchar(32) DEFAULT '' COMMENT '最后更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审计表';

-- ----------------------------
-- Records of sys_audit
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT '标识权限的唯一ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `resource` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '资源',
  `resource_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '资源类型,menu-菜单、action-操作',
  `action` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作方式,GET、POST、PUT、DELETE',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '权限父ID',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Delete timestamp based on milliseconds',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT '' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
  `modify_by` varchar(32) DEFAULT '' COMMENT '最后更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_resource_action` (`resource`,`action`,`deleted_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '标识角色的唯一ID',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '标识权限的唯一ID',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Delete timestamp based on milliseconds',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT '' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
  `modify_by` varchar(32) DEFAULT '' COMMENT '最后更新人',
  PRIMARY KEY (`role_id`,`permission_id`,`deleted_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '标识用户的唯一ID',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户账号',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
  `avatar_url` varchar(128) NOT NULL DEFAULT '' COMMENT '头像',
  `display_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `phone_number` varchar(64) NOT NULL DEFAULT '' COMMENT '手机号码',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '地址',
  `enabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否有效，1 是 0 否',
  `locked` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否锁定，1 是 0 否',
  `is_verify` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否验证，1 是 0 否',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Delete timestamp based on milliseconds',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT '' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
  `modify_by` varchar(32) DEFAULT '' COMMENT '最后更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `avatar_url`, `display_name`, `email`, `phone_number`, `address`, `enabled`, `locked`, `is_verify`, `deleted`, `deleted_time`, `create_time`, `create_by`, `modify_time`, `modify_by`) VALUES (1, 'bear', '$2a$10$dd1.cbTK8LwyDYfOMjWEpe.HWO4BFp44IPTIhdA7hrA2niTmhbWri', 'https://lh3.googleusercontent.com/ogw/AOh-ky3yeQeyoNs6y6oUlAtyCTqycyFFx4Kygv6ZN1Pf=s64-c-mo', 'Gus', 'Gus@bear.com', '21334709650', '', b'0', b'0', b'0', b'0', 0, '2022-07-26 13:54:01', '', '2022-07-26 13:54:01', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '标识用户的唯一ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '标识角色的唯一ID',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `deleted_time` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Delete timestamp based on milliseconds',
  `create_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT '' COMMENT '创建人',
  `modify_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
  `modify_by` varchar(32) DEFAULT '' COMMENT '最后更新人',
  PRIMARY KEY (`user_id`,`role_id`,`deleted_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `deleted`, `deleted_time`, `create_time`, `create_by`, `modify_time`, `modify_by`) VALUES (1, 1, 1, b'0', 0, '2022-07-26 13:54:25', '', '2022-07-26 13:54:25', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
