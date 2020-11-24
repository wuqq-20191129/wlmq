DROP SEQUENCE ACC_COMMU.SEQ_CM_EC_RECV_SEND_LOG;
CREATE SEQUENCE ACC_COMMU.SEQ_CM_EC_RECV_SEND_LOG;

DROP TABLE "ACC_COMMU"."CM_EC_RECV_SEND_LOG";
CREATE TABLE "ACC_COMMU"."CM_EC_RECV_SEND_LOG"
(
    water_no     number(10,0) ,
    datetime_rec date           NOT NULL,
    ip           varchar2(15)   NOT NULL,
    type         char(1)       NULL,
    message_code char(2)       NULL,
    message_sequ char(22)      NULL,
    message      BLOB         NULL,
    result       char(1)       NULL
);
ALTER TABLE "ACC_COMMU"."CM_EC_RECV_SEND_LOG" ADD PRIMARY KEY (water_no,datetime_rec,ip);