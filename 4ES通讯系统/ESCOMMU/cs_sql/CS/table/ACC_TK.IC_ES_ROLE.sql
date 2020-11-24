DROP TABLE "ACC_TK"."IC_ES_ROLE";
CREATE TABLE "ACC_TK"."IC_ES_ROLE"
(
    sys_group_id   varchar2(4)  NOT NULL,
    group_level    varchar2(2) NOT NULL,
    remark      varchar2(100) NULL
);
ALTER TABLE "ACC_TK"."IC_ES_ROLE" ADD PRIMARY KEY (sys_group_id);