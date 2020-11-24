CREATE GLOBAL TEMPORARY TABLE ACC_TK.IC_ES_#HUNCH_INFO
(
    logi_id        char(20)       NULL,
    card_main_code char(2)        NULL,
    card_sub_code  char(2)        NULL,
    req_no         char(10)       NULL,
    phy_id         char(20)       NULL,
    prin_id        char(16)       NULL,
    manu_time      date           NULL,
    card_mon       number(12,2)   NULL,
    peri_avadate   date           NULL,
    kdc_version    char(2)        NULL,
    hdl_time       date           NULL,
    order_no       char(14)       NULL,
    status_flag    char(2)        NULL,
    card_type      char(3)        NULL,
    line_code      char(2)        NULL,
    station_code   char(2)        NULL,
    card_start_ava char(8)        NULL
)ON COMMIT PRESERVE ROWS;