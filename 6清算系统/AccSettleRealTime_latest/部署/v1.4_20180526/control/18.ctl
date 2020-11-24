load data
append into table W_ACC_ST.W_ST_BUF_ITM_AUTO_BAL
fields terminated by ','
trailing nullcols
(
LINE_ID     ,          
STATION_ID  ,          
DEV_TYPE_ID ,          
DEVICE_ID   ,      
AUTO_BAL_DATETIME_BEGIN date 'yyyymmddhh24miss' nullif AUTO_BAL_DATETIME_BEGIN='00000000000000',  
AUTO_BAL_DATETIME_END   date 'yyyymmddhh24miss' nullif AUTO_BAL_DATETIME_END='00000000000000', 
SJT_NUM_SALE_TOTAL_1    ,   
SJT_NUM_SALE_TOTAL_2    ,  
SJT_SALE_FEE_TOTAL_1    ,  
SJT_SALE_FEE_TOTAL_2    ,  
CHARGE_FEE_TOTAL        ,                           
                                                                   
SJT_NUM_PUT_TOTAL_1     ,   
SJT_NUM_PUT_TOTAL_2     ,                              
                            
SJT_NUM_CLEAR_TOTAL_1   ,   
SJT_NUM_CLEAR_TOTAL_2   ,                              
SJT_NUM_BAL_TOTAL_1     ,   
SJT_NUM_BAL_TOTAL_2     ,                            
SJT_NUM_WASTE_TOTAL     ,                            
ITM_BAL_FEE_TOTAL       , 

BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG,
water_no "W_ACC_ST.W_seq_W_ST_BUF_ITM_AUTO_BAL.nextval"

 
)
