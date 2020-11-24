load data
append into table W_ACC_ST.W_ST_MB_BUF_RETURN
fields terminated by ','
trailing nullcols
(
    LINE_ID           ,
    STATION_ID        ,
    DEV_TYPE_ID       ,
    DEVICE_ID         ,
    SAM_LOGICAL_ID    ,
    SAM_TRADE_SEQ     ,
    CARD_MAIN_ID      ,
    CARD_SUB_ID       ,
    CARD_LOGICAL_ID   ,
    CARD_PHYSICAL_ID  ,
    CARD_STATUS_ID    ,
    RETURN_BALANCE_FEE,
    RETURN_DEPOSIT_FEE,
    PENALTY_FEE       ,
    PENALTY_REASON_ID ,
    CARD_CONSUME_SEQ  ,
    RETURN_TYPE       ,
    RETURN_DATETIME  date  'yyyymmddhh24miss' nullif RETURN_DATETIME='00000000000000' ,
    RECEIPT_ID        ,
    APPLY_DATETIME  date 'yyyymmddhh24miss' nullif RETURN_DATETIME='00000000000000' ,
    TAC               ,
    OPERATOR_ID       ,
    SHIFT_ID          ,
    AUXI_FEE          ,
    CARD_APP_FLAG     ,

    TAC_DEAL_TYPE     ,
    TAC_DEV_ID     ,
    
    mobile_no             ,
    paid_channel_type     ,
    paid_channel_code     ,
    

    BALANCE_WATER_NO  ,
    BALANCE_WATER_NO_SUB  ,
    FILE_NAME         ,
    CHECK_FLAG        ,
    water_no "W_ACC_ST.W_SEQ_W_ST_MB_BUF_RETURN.nextval"

)