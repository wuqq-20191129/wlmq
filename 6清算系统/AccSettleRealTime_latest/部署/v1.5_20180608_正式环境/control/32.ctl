load data
append into table W_ACC_ST.W_st_buf_tvm_cutoff_total
fields terminated by ','
(
water_no            ,
op_date         date 'yyyymmdd'     ,
line_id              ,
station_id           ,
dev_type_id          ,
device_id            ,
cur_datetime   date 'yyyymmddhh24miss'      ,
card_put_num         ,
coin_put_num         ,
coin_put_fee         ,
card_clear_num       ,
coin_clear_num       ,
coin_clear_fee       ,
coin_reclaim_num     ,
coin_reclaim_fee     ,
note_reclaim_num     ,
note_reclaim_fee     ,

balance_water_no     ,
balance_water_no_sub     ,
file_name            ,
check_flag           

)