load data
append into table ACC_ST.ST_NP_BUF_DEAL
fields terminated by ','
trailing nullcols
(
    LINE_ID               ,
    STATION_ID            ,
    DEV_TYPE_ID           ,
    DEVICE_ID             ,
    SAM_LOGICAL_ID        ,
    SAM_TRADE_SEQ         ,
    DEAL_DATETIME      date  'yyyymmddhh24miss'   ,
    CARD_MAIN_ID          ,
    CARD_SUB_ID           ,
    CARD_LOGICAL_ID       ,
    CARD_PHYSICAL_ID      ,
    CARD_STATUS_ID        ,
    DEAL_FEE              ,
    DEAL_BALANCE_FEE      ,
    CARD_CHARGE_SEQ       ,
    CARD_CONSUME_SEQ      ,
    PAY_MODE_ID           ,
    RECEIPT_ID            ,
    TAC                   ,
    ENTRY_LINE_ID         ,
    ENTRY_STATION_ID      ,
    ENTRY_SAM_LOGICAL_ID  ,
    ENTRY_DATETIME      date  'yyyymmddhh24miss'  ,
    OPERATOR_ID           ,
    SHIFT_ID              ,
    LAST_SAM_LOGICAL_ID   ,
    LAST_DEAL_DATETIME  date  'yyyymmddhh24miss'  ,
    DEAL_NO_DISCOUNT_FEE  ,
    
    CARD_APP_FLAG         ,
    LIMIT_MODE            ,
    LIMIT_ENTRY_STATION   ,
    LIMIT_EXIT_STATION    ,

    WORK_MODE             ,
    CITY_CODE             ,
    BUSINESS_CODE         ,
    TAC_DEAL_TYPE         ,
    TAC_DEV_ID            ,
    CARD_APP_MODE         ,
    ORDER_NO              ,

   BALANCE_WATER_NO,
   FILE_NAME,
   CHECK_FLAG,
   water_no "ACC_ST.seq_ST_NP_BUF_DEAL.nextval"
  
   
)