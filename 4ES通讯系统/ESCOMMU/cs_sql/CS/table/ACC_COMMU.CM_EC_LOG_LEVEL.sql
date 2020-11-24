DROP TABLE "ACC_COMMU"."CM_EC_LOG_LEVEL";
CREATE TABLE "ACC_COMMU"."CM_EC_LOG_LEVEL"
(
    sys_code    char(1)  NOT NULL,
    sys_level   char(1)  NOT NULL,
    set_time    date NOT NULL,
    operator    char(6)  NOT NULL,
    version_no  char(10) NOT NULL,
    record_flag char(1)  NOT NULL
);
ALTER TABLE "ACC_COMMU"."CM_EC_LOG_LEVEL" ADD PRIMARY KEY (sys_code,version_no,record_flag);