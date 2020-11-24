load data
append into table W_ACC_ST.W_ST_BUF_NON_RTN_APP
fields terminated by ','
trailing nullcols
(
    LINE_ID          ,
    STATION_ID       ,
    DEV_TYPE_ID      ,
    DEVICE_ID        ,
    CARD_MAIN_ID     ,
    CARD_SUB_ID      ,
    CARD_LOGICAL_ID  ,
    CARD_PHYSICAL_ID ,
    CARD_PRINT_ID    ,
    APPLY_DATETIME  date  'yyyymmddhh24miss' ,
    RECEIPT_ID       ,
    OPERATOR_ID      ,
    APPLY_NAME       ,
    TEL_NO           ,
    IDENTITY_TYPE    ,
    IDENTITY_ID      ,
    IS_BROKEN        ,
    SHIFT_ID         ,
    CARD_APP_FLAG    ,
    BALANCE_WATER_NO ,
    BALANCE_WATER_NO_SUB ,
    FILE_NAME        ,
    CHECK_FLAG       ,
    water_no "W_ACC_ST.W_seq_W_ST_BUF_NON_RTN_APP.nextval"

)