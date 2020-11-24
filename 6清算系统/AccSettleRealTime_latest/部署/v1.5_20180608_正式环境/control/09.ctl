load data
append into table W_ACC_ST.W_ST_BUF_TVM_NOTE_CHG_PUT
fields terminated by ','
trailing nullcols
(
LINE_ID     ,          
STATION_ID  ,          
DEV_TYPE_ID ,          
DEVICE_ID   ,

NOTE_BOX_TYPE,
NOTE_BOX_ID,
OPERATOR_ID,
PUT_DATETIME date 'yyyymmddhh24miss' nullif PUT_DATETIME='00000000000000',
WATER_NO_OP,
NOTE_FEE,
NOTE_NUM,
NOTE_FEE_TOTAL,
 
BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG,
water_no "W_ACC_ST.W_seq_W_ST_BUF_TVM_NT_CHG_PUT.nextval"

 
)