DROP SEQUENCE ACC_TK.SEQ_IC_ES_FILE_ERROR;
CREATE SEQUENCE ACC_TK.SEQ_IC_ES_FILE_ERROR;

DROP TABLE "ACC_TK"."IC_ES_FILE_ERROR";
CREATE TABLE "ACC_TK"."IC_ES_FILE_ERROR"
(
    water_no      number(18,0) ,
    device_id     varchar2(6)    NOT NULL,
    file_name     varchar2(30)   NOT NULL,
    error_code    varchar2(2)    NOT NULL,
    gen_time      date      NOT NULL,
    info_flag     varchar2(1)    NOT NULL,
    info_time     date      NULL,
    info_operator varchar2(10)   NULL,
    remark        varchar2(100)  NULL
);