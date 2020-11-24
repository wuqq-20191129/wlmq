load data
append into table w_acc_st.w_st_buf_reg_agm_total
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

overflow_num             ,   
entry_num                ,   
exit_num                 ,   
refuse_num               ,   
reclaim_num              ,   
consume_num              ,   
consume_fee              , 
refuse_entry_num         ,   
overtime_close_num       ,   
refuse_exit_num          ,   
allow_pass_num           ,   
checked_pass_num         ,   
illegal_pass_num         ,   
checked_overtime_close_num , 

BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG    


   
)