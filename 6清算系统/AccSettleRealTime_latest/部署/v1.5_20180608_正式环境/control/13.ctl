load data
append into table W_ACC_ST.W_ST_BUF_TVM_BAL
fields terminated by ','
trailing nullcols
(
LINE_ID     ,          
STATION_ID  ,          
DEV_TYPE_ID ,          
DEVICE_ID   ,      
AUTO_BAL_DATETIME_BEGIN date 'yyyymmddhh24miss' nullif AUTO_BAL_DATETIME_BEGIN='00000000000000',  
AUTO_BAL_DATETIME_END   date 'yyyymmddhh24miss' nullif AUTO_BAL_DATETIME_END='00000000000000',  
SJT_SALE_FEE_TOTAL_1    ,  
SJT_SALE_FEE_TOTAL_2    ,  
CHARGE_FEE_TOTAL        ,                           
NOTE_RECV_FEE_TOTAL     ,  
COIN_RECV_FEE_TOTAL     ,   
NOTE_RECV_FEE_REP_TOTAL ,  
COIN_RECV_FEE_REP_TOTAL ,                          
COIN_BAL_FEE_TOTAL      ,  
NOTE_BAL_FEE_TOTAL      ,                           
NOTE_CHG_FEE_PUT_TOTAL_1 , 
NOTE_CHG_FEE_PUT_TOTAL_2 , 
COIN_CHG_FEE_PUT_TOTAL_1 , 
COIN_CHG_FEE_PUT_TOTAL_2 ,                          
COIN_CHG_FEE_TOTAL_1     , 
COIN_CHG_FEE_POP_TOTAL_2 ,                           
NOTE_CHG_FEE_TOTAL_1     , 
NOTE_CHG_FEE_TOTAL_2     ,                               
COIN_CHG_FEE_BAL_TOTAL_1 ,     
COIN_CHG_FEE_BAL_TOTAL_2 ,                                   
NOTE_CHG_FEE_BAL_TOTAL_1   ,   
NOTE_CHG_FEE_BAL_TOTAL_2   ,                                 
COIN_CHG_FEE_CLEAR_TOTAL_1 ,   
COIN_CHG_FEE_CLEAR_TOTAL_2 ,                                                                                            
COIN_CHG_FEE_BAL_SUM_TOTAL_1 , 
COIN_CHG_FEE_BAL_SUM_TOTAL_2 ,                               
NOTE_RECAIM_CHG_NUM ,          
NOTE_RECAIM_BAL_NUM ,                                                                      
SJT_NUM_PUT_TOTAL_1     ,   
SJT_NUM_PUT_TOTAL_2     ,                              
SJT_NUM_SALE_TOTAL_1    ,   
SJT_NUM_SALE_TOTAL_2    ,                             
SJT_NUM_CLEAR_TOTAL_1   ,   
SJT_NUM_CLEAR_TOTAL_2   ,                              
SJT_NUM_BAL_TOTAL_1     ,   
SJT_NUM_BAL_TOTAL_2     ,                            
SJT_NUM_WASTE_TOTAL     ,                            
TVM_BAL_FEE_TOTAL       , 

BALANCE_WATER_NO,
BALANCE_WATER_NO_SUB,
FILE_NAME,
CHECK_FLAG,
water_no "W_ACC_ST.W_seq_W_ST_BUF_TVM_BAL.nextval"

 
)