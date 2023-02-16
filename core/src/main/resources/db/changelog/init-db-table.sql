-- 创建UUID函数
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- 创建系统用户表
CREATE TABLE "sys_user"
(
    "id"            BIGINT primary key,
    "uid"           uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time"  timestamptz(6) NOT NULL,
    "created_by"    varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time"  timestamptz(6),
    "updated_by"    varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time"  timestamptz(6),
    "deleted_by"    varchar(100) COLLATE "pg_catalog"."default",
    "name"          varchar(50)                                 NOT NULL,
    "email"         varchar(100),
    "mobile_number" varchar(100),
    "account"       varchar(100)                                NOT NULL,
    "avatar"        varchar(100),
    "password"      varchar(100)                                NOT NULL,
    "role_id"       bigint,
    CONSTRAINT "uq_sys_user_account" UNIQUE ("account")
);

CREATE
INDEX "index_sys_user_id_account" ON "public"."sys_user" USING btree (
  "account",
  "id"
);

-- 创建角色表
CREATE TABLE "sys_role"
(
    "id"           BIGINT primary key,
    "uid"          uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time" timestamptz(6) NOT NULL,
    "created_by"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time" timestamptz(6),
    "updated_by"   varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time" timestamptz(6),
    "deleted_by"   varchar(100) COLLATE "pg_catalog"."default",
    "code"         varchar(100)                                NOT NULL,
    "description"  varchar(200),
    "status"       bool,
    CONSTRAINT "uq_sys_role_code" UNIQUE ("code")
);

CREATE
INDEX "index_sys_role_id_code" ON "public"."sys_role" USING btree (
  "code",
  "id"
);


-- 创建权限表
CREATE TABLE "sys_permission"
(
    "id"           BIGINT primary key,
    "uid"          uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time" timestamptz(6) NOT NULL,
    "created_by"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time" timestamptz(6),
    "updated_by"   varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time" timestamptz(6),
    "deleted_by"   varchar(100) COLLATE "pg_catalog"."default",
    "code"         varchar(100)                                NOT NULL,
    "description"  varchar(200),
    "status"       bool,
    CONSTRAINT "uq_sys_permission_code" UNIQUE ("code")
);

CREATE
INDEX "index_sys_permission_id_code" ON "public"."sys_permission" USING btree (
  "code",
  "id"
);


--  创建角色权限中间表
CREATE TABLE "sys_role_permission"
(
    "id"            BIGINT primary key,
    "uid"           uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time"  timestamptz(6) NOT NULL,
    "created_by"    varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time"  timestamptz(6),
    "updated_by"    varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time"  timestamptz(6),
    "deleted_by"    varchar(100) COLLATE "pg_catalog"."default",
    "role_id"       int4                                        NOT NULL,
    "permission_id" int4                                        NOT NULL
);

CREATE
INDEX "index_sys_role_permission_id" ON "public"."sys_role_permission" USING btree (
  "id"
);


-- 创建Member表
CREATE TABLE "member"
(
    "id"            BIGINT primary key,
    "uid"           uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time"  timestamptz(6) NOT NULL,
    "created_by"    varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time"  timestamptz(6),
    "updated_by"    varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time"  timestamptz(6),
    "deleted_by"    varchar(100) COLLATE "pg_catalog"."default",
    "nick_name"     varchar(100)                                NOT NULL,
    "gender"        bool,
    "mobile_number" varchar(100),
    "email"         varchar(100),
    "account"       varchar(100)                                NOT NULL,
    "avatar"        varchar(100),
    "password"      varchar(100)                                NOT NULL,
    "back_image"    varchar(100),
    "birthday"      varchar(100),
    "address"       varchar(255),
    CONSTRAINT "uq_member_account" UNIQUE ("account")
);

CREATE
INDEX "index_member_account_id" ON "public"."member" USING btree (
    "id",
    "account",
    "nick_name"
);

