DROP TABLE "ACC_COMMU"."CM_EC_MSG_PRIORITY";
CREATE TABLE "ACC_COMMU"."CM_EC_MSG_PRIORITY"
(
    type   varchar2(1)   NOT NULL,
    msg_id varchar2(3)   NOT NULL,
    remark varchar2(100) NULL
);