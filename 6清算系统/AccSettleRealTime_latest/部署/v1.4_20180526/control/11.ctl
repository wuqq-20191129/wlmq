load data
append into table W_ACC_ST.W_ST_BUF_TVM_NOTE_RECLAIM
fields terminated by ','
trailing nullcols
(
water_no    ,
LINE_ID     ,          
STATION_ID  ,          
DEV_TYPE_ID ,          
DEVICE_ID   ,

NOTE_BOX_ID,
OPERATOR_ID,
POP_DATETIME date 'yyyymmddhh24miss' nullif pop_datetime='00000000000000',
WATER_NO_OP,
BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG

 
)