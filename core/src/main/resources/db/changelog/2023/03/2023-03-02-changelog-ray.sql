CREATE TABLE "public"."chat_gpt_recode"
(
    "id"           BIGINT GENERATED BY DEFAULT AS IDENTITY primary key,
    "uid"          uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time" timestamptz(6) NOT NULL,
    "created_by"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time" timestamptz(6),
    "updated_by"   varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time" timestamptz(6),
    "deleted_by"   varchar(100) COLLATE "pg_catalog"."default",
    "member_id"    int4                                        NOT NULL,
    "content"      varchar(255)                                NOT NULL,
    "type"         varchar(255)                                NOT NULL,
    CONSTRAINT "fk_chat_gpt_recode_member" FOREIGN KEY ("member_id") REFERENCES "public"."member" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
)
;

CREATE
INDEX "index_chat_gpt_recode_id_member_id" ON "public"."chat_gpt_recode" USING btree (
  "id",
  "member_id"
);
