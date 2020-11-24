DROP TABLE "ACC_TK"."IC_ES_HUNCH_INFO_BUF";
CREATE TABLE "ACC_TK"."IC_ES_HUNCH_INFO_BUF"
(
    logical_id          char(16)       NOT NULL,
    card_main_type      varchar2(2)        NULL,
    card_sub_type       varchar2(2)        NULL,
    req_no              char(10)       NULL,
    phy_id              char(20)       NOT NULL,
    print_id            char(16)       NOT NULL,
    manu_time           date           NULL,
    card_money          number(12,2)   NULL,
    peri_avadate        date           NULL,
    kdc_version         char(2)        NULL,
    hdl_time            date           NULL,
    order_no            char(14)       NOT NULL,
    status_flag         char(2)        NULL,
    card_type           char(3)        DEFAULT '001'      NULL,
    line_code           char(2)        DEFAULT '00'       NULL,
    station_code        char(2)        DEFAULT '00'       NULL,
    card_start_ava      char(8)        DEFAULT '00000000' NULL,
    card_ava_days       varchar2(3)     DEFAULT '000'      NULL,
    exit_line_code      varchar2(2)     DEFAULT '00'       NULL,
    exit_station_code   varchar2(2)     DEFAULT '00'       NULL,
    model              varchar2(3)     DEFAULT '000'      NULL
);
ALTER TABLE "ACC_TK"."IC_ES_HUNCH_INFO_BUF" ADD PRIMARY KEY (order_no,logical_id,phy_id);