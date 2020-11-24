load data
append into table W_ACC_ST.W_ST_MB_BUF_LCC
fields terminated by ','
trailing nullcols
(
LINE_ID                 ,
STATION_ID              ,
CHARGE_FEE              ,
CHARGE_NUM              ,
PAID_CHANNEL_TYPE_CHARGE,
PAID_CHANNEL_CODE_CHARGE,
RETURN_NUM              ,
RETURN_FEE              ,
PAID_CHANNEL_TYPE_RETURN,
PAID_CHANNEL_CODE_RETURN,
SALE_NUM              ,
SALE_FEE              ,
LOCK_NUM              ,
UNLOCK_NUM              ,
SALE_SJT_NUM              ,
SALE_SJT_FEE              ,
PAID_CHANNEL_TYPE_SALE_SJT,
PAID_CHANNEL_CODE_SALE_SJT,

PAID_CHANNEL_TYPE_SALE,
PAID_CHANNEL_CODE_SALE,
CHARGE_TCT_TNUM,
CHARGE_TCT_NUM,
CHARGE_TCT_FEE,
PAID_CHANNEL_TYPE_BUY,
PAID_CHANNEL_CODE_BUY,

BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_seq_W_ST_MB_BUF_LCC.nextval"
  
   
)