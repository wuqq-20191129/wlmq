load data
append into table W_ACC_ST.W_ST_MB_BUF_LCC
fields terminated by ','
trailing nullcols
(
LINE_ID                 ,
STATION_ID              ,
CHARGE_FEE              ,
CHARGE_NUM              ,
RETURN_NUM              ,
RETURN_FEE              ,
SALE_NUM              ,
SALE_FEE              ,
LOCK_NUM              ,
UNLOCK_NUM              ,

BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_seq_W_ST_MB_BUF_LCC.nextval"
  
   
)