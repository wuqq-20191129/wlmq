load data
append into table W_ACC_ST.W_ST_BUF_DELAY
fields terminated by ','
trailing nullcols
(
    LINE_ID             ,
    STATION_ID          ,
    DEV_TYPE_ID         ,
    DEVICE_ID           ,
    SAM_LOGICAL_ID      ,
    SAM_TRADE_SEQ       ,
    CARD_MAIN_ID        ,
    CARD_SUB_ID         ,
    CARD_LOGICAL_ID     ,
    CARD_PHYSICAL_ID    ,
    CARD_STATUS_ID      ,
    OLD_EXPIRE_DATETIME  date  'yyyymmddhh24miss',
    NEW_EXPIRE_DATETIME  date  'yyyymmddhh24miss' ,
    OPERATE_DATETIME    date  'yyyymmddhh24miss' nullif OPERATE_DATETIME='00000000000000' ,
    OPERATOR_ID         ,
    SHIFT_ID            ,
    CARD_APP_FLAG       ,
    BALANCE_WATER_NO    ,
    BALANCE_WATER_NO_SUB    ,
    FILE_NAME           ,
    CHECK_FLAG          ,
   water_no "W_ACC_ST.W_seq_W_ST_BUF_DELAY.nextval"
  
   
)