DROP SEQUENCE ACC_COMMU.SEQ_CM_EC_CONNECT_LOG;
CREATE SEQUENCE ACC_COMMU.SEQ_CM_EC_CONNECT_LOG;

DROP TABLE "ACC_COMMU"."CM_EC_CONNECT_LOG";
CREATE TABLE "ACC_COMMU"."CM_EC_CONNECT_LOG"
(
    water_no         number(8,0) PRIMARY KEY,
    connect_datetime date     NOT NULL,
    connect_ip       varchar2(15)  NOT NULL,
    connect_result   char(1)      NULL,
    remark           varchar2(100) NULL
);