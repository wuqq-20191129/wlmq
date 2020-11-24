load data
CHARACTERSET zhs16gbk
Append into table W_ACC_ST.W_ST_BUF_ADMIN
fields terminated by ','
trailing nullcols
(
  RECORD_VER,
 LINE_ID          ,   
 STATION_ID       ,   
 DEV_TYPE_ID      ,   
 DEVICE_ID        ,   
 ADMIN_DATETIME  date 'yyyymmddhh24miss' ,   
 ADMIN_WAY_ID     ,   
 CARD_MAIN_ID     ,   
 CARD_SUB_ID      ,   
 RETURN_FEE       ,   
 PENALTY_FEE      ,   
 ADMIN_REASON_ID  ,   
 DESCRIBE         ,   
 PASSENGER_NAME   ,   
 OPERATOR_ID      ,   
 SHIFT_ID         ,   
 BALANCE_WATER_NO ,  
BALANCE_WATER_NO_SUB , 
 FILE_NAME        ,   
 CHECK_FLAG       ,    
  water_no "W_ACC_ST.W_seq_W_ST_BUF_ADMIN.nextval"


)