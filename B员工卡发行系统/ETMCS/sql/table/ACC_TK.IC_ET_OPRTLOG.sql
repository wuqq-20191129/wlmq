DROP SEQUENCE ACC_TK.SEQ_IC_ET_OPRTLOG;
CREATE SEQUENCE ACC_TK.SEQ_IC_ET_OPRTLOG;  

DROP TABLE "ACC_TK"."IC_ET_OPRTLOG";
CREATE TABLE "ACC_TK"."IC_ET_OPRTLOG"(
    water_no          number(18,0)   primary key,
    oper_id           varchar(10)       NULL,
    oprt_time         date              NULL,
    oprt_content      varchar(256)      NULL,
    oprt_result       char(1)           NULL--1：失败；0：成功
);