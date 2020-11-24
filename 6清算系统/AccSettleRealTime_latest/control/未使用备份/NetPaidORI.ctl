Load data
append into table ACC_ST.ST_NP_BUF_ORDER_IMP
fields terminated by ','
trailing nullcols
(
order_no            ,
order_type          ,
finish_datetime  date 'yyyymmddhh24miss'   ,
line_id             ,
station_id          ,
dev_type_id         ,
dev_code            ,
status              ,
card_main_id        ,
card_sub_id         ,
deal_num            ,
deal_num_not        ,
deal_fee            ,
refund_fee          ,
auxi_fee            ,

balance_water_no    ,
file_name           ,
check_flag          ,
water_no    "ACC_ST.seq_ST_NP_BUF_ORDER_IMP.nextval"

)
