load data
append into table W_ACC_ST.W_ST_BUF_ENTRY
fields terminated by ','
trailing nullcols
(
     RECORD_VER,
    LINE_ID               ,
    STATION_ID            ,
    DEV_TYPE_ID           ,
    DEVICE_ID             ,
    SAM_LOGICAL_ID        ,
    SAM_TRADE_SEQ         ,
    CARD_MAIN_ID          ,
    CARD_SUB_ID           ,
    CARD_LOGICAL_ID       ,
    CARD_PHYSICAL_ID      ,
    ENTRY_DATETIME    date 'yyyymmddhh24miss',
    CARD_STATUS_ID        ,
    BALANCE_FEE           ,
    CARD_APP_FLAG         ,
     LIMIT_MODE            ,
    LIMIT_ENTRY_STATION   ,
    LIMIT_EXIT_STATION    ,

    WORK_MODE             ,
    CARD_CONSUME_SEQ      ,
    CARD_APP_MODE         ,
    tct_active_datetime  date 'yyyymmddhh24miss',

  BALANCE_WATER_NO,
  BALANCE_WATER_NO_SUB,
  FILE_NAME,
  CHECK_FLAG,
  water_no "W_ACC_ST.W_SEQ_W_ST_BUF_ENTRY.nextval"
  
   
)