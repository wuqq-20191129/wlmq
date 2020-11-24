DROP TABLE "ACC_COMMU"."CM_EC_OPERATOR_LOG";
CREATE TABLE "ACC_COMMU"."CM_EC_OPERATOR_LOG"
(
    oper_id    varchar(10)    NOT NULL,
    dev_id     varchar(8)        NOT NULL,
    login_time date       NULL,
    exit_time  date       NULL,
    memo       char(30)       NULL
);