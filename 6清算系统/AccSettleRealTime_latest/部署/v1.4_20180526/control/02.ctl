load data
append into table w_acc_st.w_st_buf_tvm_note_data
fields terminated by ','
trailing nullcols
(
water_no         ,
line_id          ,
station_id       ,
dev_type_id      ,
device_id        ,
note_box_id          ,
operator_id      ,
put_datetime    date 'yyyymmddhh24miss'  nullif put_datetime='00000000000000'   ,
pop_datetime     date 'yyyymmddhh24miss' nullif pop_datetime='00000000000000'   ,
water_no_op      ,
note_fee_total,
BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG
  
   
)