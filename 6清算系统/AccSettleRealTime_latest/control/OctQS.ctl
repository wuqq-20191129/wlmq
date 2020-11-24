load data
append into table W_ACC_ST.W_ST_EXT_OCT_AUDIT_BALANCE
fields terminated by ','
trailing nullcols
(
settle_data_type   ,
SAM_LOGICAL_ID     ,
PAY_MODE_ID        ,
TOTAL_DEAL_NUM      ,
TOTAL_DEAL_FEE     ,

BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_SEQ_ST_EXT_OCT_AUDIT_BALANCE.nextval"
  
   
)