load data
append into table ACC_ST.ST_NP_BUF_ORDER
fields terminated by ','
trailing nullcols
(
order_no             ,
order_type           ,
status               ,
generate_datetime  date 'yyyymmddhh24miss'  ,
paid_datetime      date 'yyyymmddhh24miss'  ,
card_main_id         ,
card_sub_id          ,
line_id_start        ,
station_id_start     ,
line_id_end          ,
station_id_end       ,
deal_unit_fee        ,
deal_num             ,
deal_fee             ,
order_type_buy       ,
paid_channel_type    ,
paid_channel_code    ,
mobile_no            ,

balance_water_no     ,
file_name            ,
check_flag           ,

water_no   "ACC_ST.seq_ST_NP_BUF_ORDER.nextval"          

  
)