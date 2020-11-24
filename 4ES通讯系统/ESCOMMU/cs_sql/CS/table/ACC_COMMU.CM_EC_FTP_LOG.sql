DROP SEQUENCE ACC_COMMU.SEQ_CM_EC_FTP_LOG;
CREATE SEQUENCE ACC_COMMU.SEQ_CM_EC_FTP_LOG;

DROP TABLE "ACC_COMMU"."CM_EC_FTP_LOG";
CREATE TABLE "ACC_COMMU"."CM_EC_FTP_LOG"
(
    water_no        number(8,0)    PRIMARY KEY,
    datetime_ftp    date           NOT NULL,
    ip              varchar2(15)   NOT NULL,
    filename        varchar2(50)   NULL,
    start_time      date           NULL,
    spend_time      number(8,0)    NULL,
    result          char(1)        NULL,
    remark          varchar2(512)  NULL
);