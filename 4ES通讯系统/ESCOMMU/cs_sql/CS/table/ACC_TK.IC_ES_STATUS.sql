DROP TABLE "ACC_TK"."IC_ES_STATUS";
CREATE TABLE "ACC_TK"."IC_ES_STATUS"
(
    device_id   varchar2(6)  NOT NULL,
    operator_id varchar2(10) NOT NULL,
    status_time varchar2(20) NOT NULL,
    status      varchar2(4)  NOT NULL,
    remark      varchar2(30) NULL
);
ALTER TABLE "ACC_TK"."IC_ES_STATUS" ADD PRIMARY KEY (device_id,status_time,status);