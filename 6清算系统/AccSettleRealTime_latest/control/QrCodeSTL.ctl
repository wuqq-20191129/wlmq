load data
append into table W_ACC_ST.W_ST_QP_BUF_LCC
fields terminated by ','
trailing nullcols
(
RECORD_VER              ,
LINE_ID                 ,
STATION_ID              ,

QR_ENTRY_NUM            ,            
ELECT_TK_TCT_ENTRY_NUM  ,  
ELECT_TK_ENTRY_NUM      ,     
QR_DEAL_NUM             ,              
QR_DEAL_FEE             ,             
ELECT_TK_TCT_DEAL_NUM   ,   
ELECT_TK_DEAL_NUM       ,        
ELECT_TK_DEAL_FEE       , 

QR_MATCH_NUM           ,     
QR_MATCH_FEE           ,
QR_NOT_MATCH_NUM       ,
QR_NOT_MATCH_FEE       ,

BALANCE_WATER_NO        ,
BALANCE_WATER_NO_SUB        ,
FILE_NAME               ,
CHECK_FLAG              ,
WATER_NO                "W_ACC_ST.W_SEQ_W_ST_QP_BUF_LCC.nextval"   
)
