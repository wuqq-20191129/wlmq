load data
append into table W_ACC_ST.W_st_buf_agm_cutoff_total
fields terminated by ','
(
water_no     ,
op_date     date 'yyyymmdd' ,
line_id      ,
station_id   ,
dev_type_id  ,
device_id    ,
cur_datetime date 'yyyymmddhh24miss',
box_pop_num  ,
total_num    ,
total_fee ,
BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG

   
)