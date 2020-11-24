load data
append into table W_ACC_ST.W_ST_BUF_TVM_COIN_PUT
fields terminated by ','
trailing nullcols
(

line_id               ,
station_id            ,
dev_type_id           ,
device_id             ,
hopper_id             ,
operator_id           ,
put_datetime   date 'yyyymmddhh24miss' nullif put_datetime='00000000000000'      ,
water_no_op           ,
coin_unit_fee         ,
coin_num              ,
coin_fee_total        ,
balance_water_no      ,
balance_water_no_sub      ,
file_name             ,
check_flag            ,
water_no       "W_ACC_ST.W_SEQ_W_ST_BUF_TVM_COIN_PUT.NEXTVAL"       


)