DROP SEQUENCE ACC_TK.SEQ_IC_ES_ORDER_NUM_CHANGE;
CREATE SEQUENCE ACC_TK.SEQ_IC_ES_ORDER_NUM_CHANGE;

DROP TABLE "ACC_TK"."IC_ES_ORDER_NUM_CHANGE";
CREATE TABLE "ACC_TK"."IC_ES_ORDER_NUM_CHANGE"
(
    water_no           number(18,0)   primary key,
    file_name          varchar2(30)   NOT NULL,
    order_no           varchar2(14)   NOT NULL,
    fini_pronum_reset  int           NOT NULL,
    fini_pronum_before int           NOT NULL,
    reset_time         date      NOT NULL,
    remark             varchar2(200)  NULL
);