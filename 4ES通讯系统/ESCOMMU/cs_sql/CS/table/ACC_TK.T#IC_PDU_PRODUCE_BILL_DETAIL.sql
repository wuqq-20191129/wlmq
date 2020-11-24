create global temporary table ACC_TK.T#IC_PDU_PRODUCE_BILL_DETAIL
(
  BILL_NO             CHAR(12) not null,
  IC_MAIN_TYPE        VARCHAR2(2),
  IC_SUB_TYPE         VARCHAR2(2),
  CARD_MONEY          INTEGER,
  VAILD_DATE          DATE,
  LINE_ID             CHAR(2),
  STATION_ID          CHAR(2),
  DRAW_QUANTITY       INTEGER,
  START_BOX_ID        CHAR(14),
  END_BOX_ID          CHAR(14),
  MACHINE_NO          VARCHAR2(4),
  ES_SAMNO            VARCHAR2(20),
  AFC_MAIN_TYPE       CHAR(2),
  AFC_SUB_TYPE        CHAR(2),
  AFC_LINE_ID         CHAR(2),
  AFC_STATION_ID      CHAR(2),
  ORDER_NO            CHAR(14),
  OUT_BILL_NO         CHAR(12),
  OUT_WATER_NO        VARCHAR2(18),
  CARD_AVA_DAYS       VARCHAR2(3),
  EXIT_LINE_ID        VARCHAR2(2),
  EXIT_STATION_ID     VARCHAR2(2),
  MODAL               VARCHAR2(3),
  AFC_EXIT_LINE_ID    VARCHAR2(2),
  AFC_EXIT_STATION_ID VARCHAR2(2)
)
on commit preserve rows;