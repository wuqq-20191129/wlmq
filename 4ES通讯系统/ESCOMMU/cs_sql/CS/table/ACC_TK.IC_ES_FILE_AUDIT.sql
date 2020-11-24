DROP SEQUENCE ACC_TK.SEQ_IC_ES_FILE_AUDIT;
CREATE SEQUENCE ACC_TK.SEQ_IC_ES_FILE_AUDIT;

DROP TABLE "ACC_TK"."IC_ES_FILE_AUDIT";
CREATE TABLE "ACC_TK"."IC_ES_FILE_AUDIT"
(
    water_no    number(18,0),
    device_id   varchar2(6)    NOT NULL,
    file_name   varchar2(30)   NOT NULL,
    status      varchar2(1)    NOT NULL,
    info_time   date            NOT NULL,
    info_operator    varchar2(10)   NULL
);