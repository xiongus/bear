/*
 Navicat Premium Data Transfer

 Source Server         : 172.16.185.129
 Source Server Type    : PostgreSQL
 Source Server Version : 140005
 Source Host           : 172.16.185.129:5432
 Source Catalog        : bear
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140005
 File Encoding         : 65001

 Date: 03/09/2022 17:40:50
*/


-- ----------------------------
-- Sequence structure for sys_role_permission_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_role_permission_id_seq";
CREATE SEQUENCE "public"."sys_role_permission_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."sys_role_permission_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_user_role_id_seq";
CREATE SEQUENCE "public"."sys_user_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."sys_user_role_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS "public"."oauth2_registered_client";
CREATE TABLE "public"."oauth2_registered_client" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id_issued_at" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "client_secret" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "client_secret_expires_at" timestamp(6) NOT NULL DEFAULT '2038-01-09 03:14:07'::timestamp without time zone,
  "client_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "client_authentication_methods" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "authorization_grant_types" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "redirect_uris" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "scopes" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "client_settings" varchar(2000) COLLATE "pg_catalog"."default" NOT NULL,
  "token_settings" varchar(2000) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."oauth2_registered_client" OWNER TO "postgres";
COMMENT ON TABLE "public"."oauth2_registered_client" IS 'OAuth2.0客户端';

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
BEGIN;
INSERT INTO "public"."oauth2_registered_client" ("id", "client_id", "client_id_issued_at", "client_secret", "client_secret_expires_at", "client_name", "client_authentication_methods", "authorization_grant_types", "redirect_uris", "scopes", "client_settings", "token_settings") VALUES ('165bc9c9-9633-4eec-9706-b1d47f552bc7', 'bear-client', '2022-09-03 09:09:04.870207', '$2a$10$EWNLbM9FFHxgJEVSWdqgEuZKcdMLcMwwW5z/l1dLqsqhHILifxkcC', '2038-01-09 03:14:07', 'bear', 'client_secret_basic', 'authorization_code', 'http://127.0.0.1:8080/callback', 'openid,console,market,portal', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}');
COMMIT;

-- ----------------------------
-- Table structure for sys_audit
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_audit";
CREATE TABLE "public"."sys_audit" (
  "id" int8 NOT NULL,
  "entity_id" int8 NOT NULL,
  "entity_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "op_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "comment" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_audit" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_audit"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_audit"."entity_id" IS '实体ID';
COMMENT ON COLUMN "public"."sys_audit"."entity_name" IS '实体名称';
COMMENT ON COLUMN "public"."sys_audit"."op_name" IS '操作类型';
COMMENT ON COLUMN "public"."sys_audit"."comment" IS '操作内容';
COMMENT ON COLUMN "public"."sys_audit"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_audit"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_audit"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_audit"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_audit"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_audit"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_audit" IS '审计表';

-- ----------------------------
-- Records of sys_audit
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_permission";
CREATE TABLE "public"."sys_permission" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "resource" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "resource_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "action" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" int8 DEFAULT 0,
  "comment" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_permission" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_permission"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_permission"."name" IS '权限名称';
COMMENT ON COLUMN "public"."sys_permission"."resource" IS '资源';
COMMENT ON COLUMN "public"."sys_permission"."resource_type" IS '资源类型,app-系统、menu-菜单、action-操作';
COMMENT ON COLUMN "public"."sys_permission"."action" IS '操作方式,GET、POST、PUT、DELETE';
COMMENT ON COLUMN "public"."sys_permission"."parent_id" IS '主键父ID';
COMMENT ON COLUMN "public"."sys_permission"."comment" IS '权限描述';
COMMENT ON COLUMN "public"."sys_permission"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_permission"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_permission"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_permission"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_permission"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_permission" IS '权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "comment" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_role"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_role"."name" IS '角色名称';
COMMENT ON COLUMN "public"."sys_role"."role" IS '角色标识';
COMMENT ON COLUMN "public"."sys_role"."comment" IS '角色描述';
COMMENT ON COLUMN "public"."sys_role"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_role"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_role"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_role"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_role" IS '角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_role" ("id", "name", "role", "comment", "deleted", "deleted_time", "create_time", "create_by", "modify_time", "modify_by") VALUES (1, '系统管理员', 'ROLE_ADMIN', '超级管理员', '0', 0, '2022-09-03 09:39:49.792233', '', '2022-09-03 09:39:49.792233', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_permission";
CREATE TABLE "public"."sys_role_permission" (
  "id" int8 NOT NULL DEFAULT nextval('sys_role_permission_id_seq'::regclass),
  "role_id" int8 NOT NULL,
  "permission_id" int8 NOT NULL,
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_role_permission" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_role_permission"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_role_permission"."role_id" IS '用户ID';
COMMENT ON COLUMN "public"."sys_role_permission"."permission_id" IS '角色ID';
COMMENT ON COLUMN "public"."sys_role_permission"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_role_permission"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_role_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role_permission"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_role_permission"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_role_permission"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_role_permission" IS '角色权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" int8 NOT NULL,
  "username" varchar(64) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '0'::"bit",
  "password" varchar(64) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0,
  "avatar_url" varchar(128) COLLATE "pg_catalog"."default" NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "display_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(64) COLLATE "pg_catalog"."default" NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "phone_number" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "address" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bit(1) NOT NULL DEFAULT '0'::"bit",
  "locked" bit(1) NOT NULL DEFAULT '0'::"bit",
  "is_verify" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_user" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_user"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_user"."username" IS '用户账号';
COMMENT ON COLUMN "public"."sys_user"."password" IS '用户密码';
COMMENT ON COLUMN "public"."sys_user"."avatar_url" IS '用户头像';
COMMENT ON COLUMN "public"."sys_user"."display_name" IS '用户名称';
COMMENT ON COLUMN "public"."sys_user"."email" IS '电子邮箱';
COMMENT ON COLUMN "public"."sys_user"."phone_number" IS '联系方式';
COMMENT ON COLUMN "public"."sys_user"."address" IS '联系地址';
COMMENT ON COLUMN "public"."sys_user"."enabled" IS '是否有效，1 是 0 否';
COMMENT ON COLUMN "public"."sys_user"."locked" IS '是否锁定，1 是 0 否';
COMMENT ON COLUMN "public"."sys_user"."is_verify" IS '是否验证，1 是 0 否';
COMMENT ON COLUMN "public"."sys_user"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_user"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_user"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_user"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_user" IS '用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_user" ("id", "username", "password", "avatar_url", "display_name", "email", "phone_number", "address", "enabled", "locked", "is_verify", "deleted", "deleted_time", "create_time", "create_by", "modify_time", "modify_by") VALUES (1, 'bear', '$2a$10$dd1.cbTK8LwyDYfOMjWEpe.HWO4BFp44IPTIhdA7hrA2niTmhbWri', 'https://lh3.googleusercontent.com/ogw/AOh-ky3yeQeyoNs6y6oUlAtyCTqycyFFx4Kygv6ZN1Pf=s64-c-mo', 'Gus', 'Gus@bear.com', '21334709650', '', '0', '0', '0', '0', 0, '2022-07-26 13:54:01', '', '2022-07-26 13:54:01', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "id" int8 NOT NULL DEFAULT nextval('sys_user_role_id_seq'::regclass),
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "deleted" bit(1) NOT NULL DEFAULT '0'::"bit",
  "deleted_time" int8 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "create_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "modify_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "modify_by" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."sys_user_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."sys_user_role"."id" IS '主键ID';
COMMENT ON COLUMN "public"."sys_user_role"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."sys_user_role"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."sys_user_role"."deleted" IS '1: deleted, 0: normal';
COMMENT ON COLUMN "public"."sys_user_role"."deleted_time" IS 'Delete timestamp based on milliseconds';
COMMENT ON COLUMN "public"."sys_user_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user_role"."create_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_user_role"."modify_time" IS '最后更新时间';
COMMENT ON COLUMN "public"."sys_user_role"."modify_by" IS '最后更新人';
COMMENT ON TABLE "public"."sys_user_role" IS '用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."sys_user_role" ("id", "user_id", "role_id", "deleted", "deleted_time", "create_time", "create_by", "modify_time", "modify_by") VALUES (1, 1, 1, '0', 0, '2022-09-03 09:40:15.069159', '', '2022-09-03 09:40:15.069159', '');
COMMIT;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."sys_role_permission_id_seq"
OWNED BY "public"."sys_role_permission"."id";
SELECT setval('"public"."sys_role_permission_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."sys_user_role_id_seq"
OWNED BY "public"."sys_user_role"."id";
SELECT setval('"public"."sys_user_role_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table oauth2_registered_client
-- ----------------------------
ALTER TABLE "public"."oauth2_registered_client" ADD CONSTRAINT "oauth2_registered_client_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_audit
-- ----------------------------
ALTER TABLE "public"."sys_audit" ADD CONSTRAINT "sys_audit_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_permission
-- ----------------------------
CREATE INDEX "uk_permission_resource_action" ON "public"."sys_permission" USING btree (
  "resource" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "resource_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "action" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "deleted_time" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_permission
-- ----------------------------
ALTER TABLE "public"."sys_permission" ADD CONSTRAINT "sys_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE INDEX "uk_role" ON "public"."sys_role" USING btree (
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_permission
-- ----------------------------
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "sys_role_permission_pkey" PRIMARY KEY ("role_id", "permission_id", "deleted_time");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE UNIQUE INDEX "uk_username" ON "public"."sys_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("user_id", "role_id", "deleted_time");
