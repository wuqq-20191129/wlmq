load data
append into table W_ACC_TK.W_IC_MB_LOGOUT_INFO
fields terminated by '\t'
TRAILING NULLCOLS
(LOGICAL_ID,
CARD_MAIN_TYPE,
CARD_SUB_TYPE,
REQ_NO,
PHY_ID,
PRINT_ID,
MANU_TIME date 'yyyy-mm-dd hh24:mi:ss',
CARD_MONEY,
PERI_AVADATE date 'yyyy-mm-dd hh24:mi:ss',
KDC_VERSION,
HDL_TIME date 'yyyy-mm-dd hh24:mi:ss',
ORDER_NO,
STATUS_FLAG,
CARD_TYPE,
CARD_AVA_DAYS,
EXIT_LINE_CODE,
EXIT_STATION_CODE,
MODEL,
card_producer_code,
phone_no
)