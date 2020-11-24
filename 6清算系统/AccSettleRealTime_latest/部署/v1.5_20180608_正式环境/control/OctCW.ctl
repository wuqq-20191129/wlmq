load data
append into table W_ACC_ST.W_ST_EXT_OCT_ERR_TRADE
fields terminated by ','
trailing nullcols
(
serial_no           ,
sam_logical_id      ,
terminal_flag       ,
deal_datetime       ,

sam_trade_seq       ,
card_logical_id     ,
card_physical_id    ,
card_main_id        ,
card_sub_id         ,

last_sam_logical_id ,
last_deal_datetime  ,
deal_fee            ,
deal_balance_fee    ,
deal_no_discount_fee,
pay_mode_id         ,
entry_sam_logical_id,
entry_datetime      ,
card_charge_seq     ,
card_consume_seq    ,

tac                 ,
city_code           ,
business_code       ,
key_version         ,
reserve_field       ,
last_charge_datetime,
err_code            ,


BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_SEQ_W_ST_EXT_OCT_ERR_TRADE.nextval"
  
   
)