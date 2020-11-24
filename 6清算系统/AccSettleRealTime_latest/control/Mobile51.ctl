load data
append into table W_ACC_ST.W_ST_MB_BUF_SALE
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
    CARD_STATUS_ID   ,
    WATER_NO_BUSINESS ,
    SAM_LOGICAL_ID   ,
    SAM_TRADE_SEQ    ,
    DEPOSIT_TYPE     ,
    DEPOSIT_FEE      ,
    SALE_DATETIME     date 'yyyymmddhh24miss'   ,
    RECEIPT_ID       ,
    OPERATOR_ID      ,
    SHIFT_ID         ,
    AUXI_FEE         ,
    CARD_APP_FLAG    ,
    MOBILE_NO        ,
    PAID_CHANNEL_TYPE ,
    PAID_CHANNEL_CODE ,
   
   BALANCE_WATER_NO,
   BALANCE_WATER_NO_SUB,
   FILE_NAME,
   CHECK_FLAG,
   water_no "W_acc_st.W_seq_W_ST_MB_BUF_SALE.nextval"
  
   
)