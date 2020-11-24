DROP TABLE "ACC_TK"."IC_ES_BCP_CONFIG";
CREATE TABLE "ACC_TK"."IC_ES_BCP_CONFIG"(
    server   varchar2(50)  NOT NULL,
    db       varchar2(50)  NOT NULL,
    account  varchar2(8)   NOT NULL,
    password varchar2(100) NOT NULL,
    enc_flag varchar2(1)   DEFAULT '0' NOT NULL,
    remark   varchar2(100) NULL
);