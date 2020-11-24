load data
append into table W_ACC_ST.W_ST_BUF_REPORT_LOSS
fields terminated by ','
trailing nullcols
(
    RECORD_VER,
    LINE_ID          ,
    STATION_ID       ,
    DEV_TYPE_ID      ,
    DEVICE_ID        ,
    APPLY_NAME       ,
    APPLY_SEX        ,
    IDENTITY_TYPE    ,
    IDENTITY_ID      ,
    EXPIRED_DATE  date 'yyyymmdd'   ,
    TEL_NO           ,
    FAX              ,
    ADDRESS          ,
    OPERATOR_ID      ,
    APPLY_DATETIME   date 'yyyymmddhh24miss'   ,
    SHIFT_ID         ,
    CARD_APP_FLAG    ,

    CARD_MAIN_ID     ,
    CARD_SUB_ID      ,
    APPLY_BUSINESS_TYPE  ,
    RECEIPT_ID       ,
    CARD_LOGICAL_ID  ,

    BALANCE_WATER_NO ,
    BALANCE_WATER_NO_SUB ,
    FILE_NAME        ,
    CHECK_FLAG       ,

    water_no "W_ACC_ST.W_seq_W_ST_BUF_REPORT_LOSS.nextval"

  
   
)  