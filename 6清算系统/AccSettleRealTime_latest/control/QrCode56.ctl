load data
append into table W_ACC_ST.W_ST_QP_BUF_UPDATE
fields terminated by ','
TRAILING NULLCOLS
(
    RECORD_VER,
    LINE_ID              , 
    STATION_ID           , 
    DEV_TYPE_ID          , 
    DEVICE_ID            , 
    SAM_LOGICAL_ID       , 
    SAM_TRADE_SEQ        , 
    CARD_MAIN_ID         , 
    CARD_SUB_ID          , 
    CARD_LOGICAL_ID      , 
    CARD_PHYSICAL_ID     , 
    CARD_CONSUME_SEQ     , 
    CARD_STATUS_ID       , 
    UPDATE_AREA          , 
    UPDATE_REASON_ID     , 
    UPDATE_DATETIME    date  'yyyymmddhh24miss' nullif UPDATE_DATETIME='00000000000000' , 
    PAY_TYPE             , 
    PENALTY_FEE          , 
    RECEIPT_ID           , 
    OPERATOR_ID          , 
    ENTRY_LINE_ID        , 
    ENTRY_STATION_ID     , 
    SHIFT_ID             , 
    CARD_APP_FLAG        ,
    LIMIT_MODE           , 
    LIMIT_ENTRY_STATION  , 
    LIMIT_EXIT_STATION   , 

    CARD_APP_MODE,
    TCT_ACTIVATE_DATETIME  date  'yyyymmddhh24miss' nullif TCT_ACTIVATE_DATETIME='00000000000000',

   BUSINESS_WATER_NO,
   BUSINESS_WATER_NO_REL,

    BALANCE_WATER_NO     , 
    BALANCE_WATER_NO_SUB     ,
    FILE_NAME            , 
    CHECK_FLAG           , 
    water_no "W_ACC_ST.W_SEQ_W_ST_QP_BUF_UPDATE.nextval"

)
