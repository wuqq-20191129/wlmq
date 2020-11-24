load data
append into table w_acc_st.w_st_buf_bom_shift_total
fields terminated by ','
(
water_no                      ,
line_id                       ,
station_id                    ,
dev_type_id                   ,
device_id                     ,
operator_id                   ,
shift_id                      ,
start_time     date 'yyyymmddhh24miss'   nullif start_time='00000000000000'            ,
end_time       date 'yyyymmddhh24miss'   nullif end_time='00000000000000'           ,
sale_total_num                ,
sale_total_fee                ,
charge_total_num              ,
charge_total_fee              ,
update_total_num              ,
update_total_fee              ,
update_total_fee_cash         ,
admin_total_num               ,
admin_total_fee_return        ,
admin_total_fee_income        ,
delay_total_num               ,
return_total_num              ,
return_total_fee              ,
return_non_total_num          ,
return_non_total_fee          ,
return_non_app_total_num      ,
charge_update_total_num       ,
charge_update_total_fee       ,
unlock_total_num              ,
total_fee_nocash              ,
total_fee_cash                ,
balance_water_no              ,
balance_water_no_sub              ,
file_name                     ,
check_flag                    


   
)