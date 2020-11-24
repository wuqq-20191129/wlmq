load data
append into table w_acc_st.w_st_buf_tvm_card_clear
fields terminated by ','
trailing nullcols
(

line_id               ,
station_id            ,
dev_type_id           ,
device_id             ,
operator_id           ,
clear_datetime   date 'yyyymmddhh24miss' nullif clear_datetime='00000000000000'    ,
water_no_op           ,
card_main_id_hopper_1 ,
card_sub_id_hopper_1  ,
hopper_1_num          ,
card_main_id_hopper_2 ,
card_sub_id_hopper_2  ,
hopper_2_num          ,
balance_water_no      ,
balance_water_no_sub      ,
file_name             ,
check_flag            ,
water_no      "w_acc_st.w_seq_w_st_buf_tvm_card_clear.nextval"



   
)