load data
append into table W_ACC_ST.W_ST_BUF_LCC
fields terminated by ','
trailing nullcols
(
LINE_ID                 ,
STATION_ID              ,
BOM_SALE_SJT_NUM        ,
BOM_SALE_SJT_FEE        ,
TVM_SALE_SJT_NUM        ,
TVM_SALE_SJT_FEE        ,
BOM_SALE_NUM            ,
BOM_SALE_DEPOSIT_FEE    ,
BOM_CHARGE_FEE          ,
TVM_CHARGE_NUM          ,
TVM_CHARGE_FEE          ,
RETURN_NUM              ,
RETURN_FEE              ,
NON_RETURN_NUM          ,
NON_RET_DEPOSIT_FEE     ,
NON_RET_ACTUAL_RET_BALA ,
NEGATIVE_CHARGE_NUM     ,
NEGATIVE_CHARGE_FEE     ,
DEAL_NUM                ,
DEAL_FEE                ,
UPDATE_CASH_NUM         ,
UPDATE_CASH_FEE         ,
UPDATE_NONCASH_NUM      ,
UPDATE_NONCASH_FEE      ,
ADMIN_NUM               ,
ADMIN_RETURN_FEE        ,
ADMIN_PENALTY_FEE       ,
itm_sale_sjt_num        ,
itm_sale_sjt_fee        ,
itm_charge_num          ,
itm_charge_fee          ,


BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB    ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_seq_W_ST_BUF_LCC.nextval"
  
   
)