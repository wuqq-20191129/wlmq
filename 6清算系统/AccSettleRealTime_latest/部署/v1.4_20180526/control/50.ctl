load data
append into table W_ACC_ST.W_ST_BUF_SALE_SJT
fields terminated by ','
trailing nullcols
(
  LINE_ID,
  STATION_ID,
  DEV_TYPE_ID,
  DEVICE_ID,
  SAM_LOGICAL_ID,
  SAM_TRADE_SEQ,
  SALE_DATETIME date 'yyyymmddhh24miss',
  PAY_TYPE,
  CARD_LOGICAL_ID_PAY,
  CARD_COUNT_USED,
  CARD_LOGICAL_ID,
  CARD_PHYSICAL_ID,
  card_status_id,
  SALE_FEE,
  CARD_MAIN_ID,
  CARD_SUB_ID,
  ZONE_ID,
  TAC,
  DEPOSIT_TYPE,
  DEPOSIT_FEE,
  OPERATOR_ID,
  SHIFT_ID,
  AUXI_FEE,
  CARD_APP_FLAG,

  TAC_DEAL_TYPE,
  TAC_DEV_ID,

  BALANCE_WATER_NO,
  BALANCE_WATER_NO_SUB,
  FILE_NAME,
  CHECK_FLAG,
  water_no "W_ACC_ST.W_SEQ_W_ST_BUF_SALE_SJT.nextval"
  
   
)