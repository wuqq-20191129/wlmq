create global temporary table ACC_TK.T#IC_PDU_ORDER_FORM
(
  ORDER_NO          CHAR(14) not null,
  CARD_MAIN_CODE    VARCHAR2(2),
  CARD_SUB_CODE     VARCHAR2(2),
  CARD_PER_AVA      DATE,
  CARD_MON          NUMBER(18),
  DRAW_NUM          INTEGER,
  FINI_PRONUM       INTEGER,
  SURPLUS_NUM       INTEGER,
  TRASHY_NUM        INTEGER,
  ES_SAMNO          VARCHAR2(20),
  LINE_CODE         CHAR(2),
  STATION_CODE      CHAR(2),
  CARD_AVA_DAYS     VARCHAR2(3),
  EXIT_LINE_CODE    VARCHAR2(2),
  EXIT_STATION_CODE VARCHAR2(2),
  MODEL             VARCHAR2(3)
)
on commit preserve rows;