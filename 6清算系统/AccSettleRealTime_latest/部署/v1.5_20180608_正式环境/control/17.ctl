load data
append into table w_acc_st.w_st_buf_itm_waste_card_data
fields terminated by ','
trailing nullcols
(

line_id           ,
station_id        ,
dev_type_id       ,
device_id         ,
operator_id       ,
water_no_op       ,
pop_datetime   date 'yyyymmddhh24miss' nullif pop_datetime='00000000000000'  ,
sjt_num           ,
sjt_discount_num  ,
balance_water_no  ,
balance_water_no_sub  ,
file_name         ,
check_flag        ,
water_no          "w_acc_st.w_seq_w_st_buf_itm_wt_cd_data.nextval"
)
