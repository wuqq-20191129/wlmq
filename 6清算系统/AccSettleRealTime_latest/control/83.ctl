load data
append into table w_acc_st.w_st_buf_reg_bom_total
fields terminated by ','
(
water_no      ,
commu_datetime date 'yyyymmddhh24miss',
line_id       ,
station_id    ,
dev_type_id   ,
device_id     ,

gen_datetime date 'yyyymmddhh24miss' ,
total_num ,

overflow_num       ,
sale_sjt_num       ,
sale_sjt_fee       ,
tk_exit_free_num   ,
tk_exit_charge_num ,
tk_exit_charge_fee ,

BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG    


   
)
