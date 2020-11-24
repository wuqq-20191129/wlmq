DROP SEQUENCE ACC_TK.SEQ_IC_ES_INFO_FILE;
CREATE SEQUENCE ACC_TK.SEQ_IC_ES_INFO_FILE;

DROP TABLE "ACC_TK"."IC_ES_INFO_FILE";
CREATE TABLE "ACC_TK"."IC_ES_INFO_FILE"
(
    water_no    number(18,0),
    device_id   varchar2(6)     NOT NULL,
    file_name   varchar2(30)    NOT NULL,
    status      varchar2(1)     NOT NULL,
    info_time   date            NOT NULL,
    update_time date            NULL,
    operator    varchar2(10)    NULL
);