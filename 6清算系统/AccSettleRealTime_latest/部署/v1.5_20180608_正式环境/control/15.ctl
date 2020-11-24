load data
append into table w_acc_st.w_st_buf_itm_card_put
fields terminated by ','
trailing nullcols
(


line_id          ,
station_id       ,
dev_type_id      ,
device_id        ,
hopper_id        ,
operator_id      ,
box_id           ,
put_datetime  date 'yyyymmddhh24miss' nullif put_datetime='00000000000000'  ,
water_no_op      ,
card_main_id     ,
card_sub_id      ,
num              ,
balance_water_no ,
balance_water_no_sub ,
file_name        ,
check_flag       ,
water_no     "w_acc_st.w_seq_w_st_buf_itm_card_put.nextval"


)
