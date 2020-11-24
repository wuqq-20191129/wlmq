load data
append into table W_ACC_ST.W_ST_MB_BUF_LOCK
fields terminated by ','
trailing nullcols
(
    LINE_ID          ,
    STATION_ID       ,
    DEV_TYPE_ID      ,
    DEVICE_ID        ,
    SAM_LOGICAL_ID   ,
    SAM_TRADE_SEQ    ,
    CARD_MAIN_ID     ,
    CARD_SUB_ID      ,
    CARD_LOGICAL_ID  ,
    CARD_PHYSICAL_ID ,
    CARD_STATUS_ID   ,
    LOCK_FLAG        ,
    LOCK_DATETIME  date 'yyyymmddhh24miss'  ,
    OPERATOR_ID      ,
    SHIFT_ID         ,
    CARD_APP_FLAG    ,

    CARD_APP_MODE    ,
    mobile_no        ,

    BALANCE_WATER_NO ,
    BALANCE_WATER_NO_SUB ,
    FILE_NAME        ,
    CHECK_FLAG       ,
    water_no "W_acc_st.W_seq_W_ST_MB_BUF_LOCK.nextval"

)