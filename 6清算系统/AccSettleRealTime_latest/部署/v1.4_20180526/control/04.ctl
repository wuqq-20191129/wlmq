load data
append into table w_acc_st.w_st_buf_tvm_coin_clear
fields terminated by ','
trailing nullcols
(
water_no              ,
line_id               ,
station_id            ,
dev_type_id           ,
device_id             ,
operator_id           ,
coin_box_id           ,
clear_datetime date 'yyyymmddhh24miss' nullif clear_datetime='00000000000000'       ,
water_no_op           ,
hopper_1_unit_fee     ,
hopper_1_unit_num     ,
hopper_2_unit_fee     ,
hopper_2_unit_num     ,
coin_fee_total        ,
balance_water_no      ,
balance_water_no_sub      ,
file_name             ,
check_flag                 

)