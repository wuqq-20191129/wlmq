DROP SEQUENCE ACC_COMMU.SEQ_CM_EC_LOG;
CREATE SEQUENCE ACC_COMMU.SEQ_CM_EC_LOG;

DROP TABLE "ACC_COMMU"."CM_EC_LOG";
CREATE TABLE "ACC_COMMU"."CM_EC_LOG"
(
    water_no     number(18,0)  primary key,
    message_id   varchar2(3)    NULL,
    message_name varchar2(50)   NULL,
    message_from varchar2(50)   NULL,
    start_time   date           NULL,
    end_time     date           NULL,
    use_time     int           NULL,
    result       char(1)       NULL,
    hdl_thread   varchar2(50)   NULL,
    sys_level    char(1)       NULL,
    remark       varchar2(512)  NULL
);