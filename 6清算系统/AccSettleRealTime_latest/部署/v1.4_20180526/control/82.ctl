load data
append into table w_acc_st.w_st_buf_reg_tvm_total
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

 overflow_num          ,
 sale_num              ,
 sale_fee              ,
 charge_num            ,
 charge_fee            ,
 coin_receive_num      ,
 coin_receive_fee      ,
 note_receive_num      ,
 note_ceceive_fee      ,
 coin_change_num       ,
 coin_change_fee       ,
 note_change_num       ,
 note_change_fee       ,
 tk_waste_num          ,

BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG    


   
)
