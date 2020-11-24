CREATE GLOBAL TEMPORARY TABLE ACC_TK.IC_PDU_#USELESS_DETAIL
(
    bill_no      char(12)  NULL,
    order_no     char(14) NULL,
    card_no      char(20)  NULL,
    card_type    char(1)   NULL,
    ic_main_type char(2)  NULL,
    ic_sub_type  char(2)  NULL,
    line_id      char(2)  NULL,
    station_id   char(2)  NULL,
    card_money   int       NULL,
    valid_date   date NULL,
    machine_no   char(8)  NULL,
    flag         char(1)  NULL,
    phy_id       char(20) null,

    card_main_code  char(2) null,
    card_sub_code   char(2) null,
    line_code       char(2) null,
    station_code    char(2) null
)ON COMMIT PRESERVE ROWS;