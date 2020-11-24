load data
append into table w_acc_st.w_st_buf_tvm_coin_data
fields terminated by ','
trailing nullcols
(
water_no              ,
line_id               ,  
station_id            ,  
dev_type_id           ,  
device_id             ,  
coin_box_id           ,  
operator_id           ,  
put_datetime    date 'yyyymmddhh24miss' nullif put_datetime='00000000000000'     ,  
pop_datetime     date 'yyyymmddhh24miss' nullif pop_datetime='00000000000000'      ,  
water_no_op           ,  
coin_fee_total        ,  
balance_water_no      ,  
balance_water_no_sub      ,
file_name             ,  
check_flag           

)