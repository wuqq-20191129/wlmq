load data
append into table ST_EXT_MTR_BUF_DEAL
fields terminated by ','
trailing nullcols
(
SAM_TRADE_SEQ,
CARD_MAIN_ID,
CARD_SUB_ID,
CARD_LOGICAL_ID,
CARD_PHYSICAL_ID,
LAST_SAM_LOGICAL_ID,
LAST_DEAL_DATETIME    date 'yyyymmddhh24miss',
SAM_LOGICAL_ID,
DEAL_DATETIME  date 'yyyymmddhh24miss',
ENTRY_SAM_LOGICAL_ID,
ENTRY_DATETIME date 'yyyymmddhh24miss',
PAY_MODE_ID,
DEAL_FEE,
DEAL_BALANCE_FEE,
CARD_CONSUME_SEQ,
TAC,
CITY_CODE,
BUSINESS_CODE,
TAC_DEAL_TYPE,
TAC_DEV_ID,

BALANCE_WATER_NO        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "SEQ_ST_EXT_MTR_BUF_DEAL.nextval"
  
   
)