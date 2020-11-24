load data
append into table W_ACC_ST.W_ST_BUF_DEAL
fields terminated by ','
trailing nullcols
(
    RECORD_VER               ,
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
    mobile_no             ,
    paid_channel_type     ,
    paid_channel_code     ,
  
    last_charge_datetime  date  'yyyymmddhh24miss'      ,
    
    ISSUE_UNIT      ,
    key_version         ,
    KEY_INDEX       ,
    KEY_RANDOM_NO   ,
    ALGORITHM_FLAG  ,
    HOLDER_NAME     ,
    IDENTITY_TYPE   ,
    IDENTITY_ID     ,
    BUY_TK_NUM      ,
    BUY_TK_UNIT_FEE ,

   BALANCE_WATER_NO,
   BALANCE_WATER_NO_SUB,
   FILE_NAME,
   CHECK_FLAG,
   water_no "W_ACC_ST.W_SEQ_W_ST_BUF_DEAL.nextval"
  
   
)