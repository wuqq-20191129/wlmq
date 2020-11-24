DROP TABLE "ACC_TK"."IC_ES_SIGN_CARD";
CREATE TABLE "ACC_TK"."IC_ES_SIGN_CARD"
(
    req_no           char(10) NOT NULL primary key,
    line_id          char(2)  NULL,
    station_id       char(2)  NULL,
    dev_type_id      char(2)  NULL,
    device_id        char(4)  NULL,
    proposer_name    char(8)  NOT NULL,
    proposer_sex     char(1)  NOT NULL,
    ind_type         char(1)  NOT NULL,
    national_id      char(18) NOT NULL,
    expired_date     date NOT NULL,
    tel_no           char(16) NOT NULL,
    fax              char(16) NOT NULL,
    address          char(30) NOT NULL,
    oper_id          char(8)  NULL,
    app_date         date NULL,
    bom_shiftid      char(2)  NULL,
    balance_water_no char(18) NULL,
    file_name        char(25) NULL,
    data_file_name   char(25) NULL,
    hdl_time         date NULL,
    hdl_flag         char(1)  NULL,
    order_no         char(14) NULL
)