--grant--------------------------------------
cm:
W_CM_EC_OPERATOR_LOG
W_CM_EC_LOG_LEVEL
W_CM_EC_CONNECT_LOG
W_CM_EC_RECV_SEND_LOG
W_CM_EC_FTP_LOG
W_CM_EC_LOG
W_CM_EC_MSG_PRIORITY

W_SEQ_W_CM_EC_CONNECT_LOG
W_SEQ_W_CM_EC_RECV_SEND_LOG
W_SEQ_W_CM_EC_FTP_LOG
W_SEQ_W_CM_EC_LOG

grant select, insert, update, delete on W_CM_EC_OPERATOR_LOG to W_ACC_CM_APP;
grant select on W_CM_EC_OPERATOR_LOG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_LOG_LEVEL to W_ACC_CM_APP;
grant select on W_CM_EC_LOG_LEVEL to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_CONNECT_LOG to W_ACC_CM_APP;
grant select on W_CM_EC_CONNECT_LOG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_RECV_SEND_LOG to W_ACC_CM_APP;
grant select on W_CM_EC_RECV_SEND_LOG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_FTP_LOG to W_ACC_CM_APP;
grant select on W_CM_EC_FTP_LOG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_LOG to W_ACC_CM_APP;
grant select on W_CM_EC_LOG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_CM_EC_MSG_PRIORITY to W_ACC_CM_APP;
grant select on W_CM_EC_MSG_PRIORITY to W_ACC_CM_DEV;

grant select on W_SEQ_W_CM_EC_CONNECT_LOG to W_ACC_CM_APP;
grant select on W_SEQ_W_CM_EC_RECV_SEND_LOG to W_ACC_CM_APP;
grant select on W_SEQ_W_CM_EC_FTP_LOG to W_ACC_CM_APP;
grant select on W_SEQ_W_CM_EC_LOG to W_ACC_CM_APP;


st:
w_st_list_sign_card
w_op_prm_phy_logic_list
W_OP_PRM_CARD_SUB
W_OP_SYS_OPERATOR
W_OP_SYS_GROUP_OPERATOR
W_OP_PRM_STATION
W_OP_PRM_LINE
W_OP_PRM_DEV_CODE
W_OP_PRM_FARE_TABLE
W_OP_PRM_SAM_LIST
W_OP_PRM_BLACK_LIST_OCT_SEC
W_OP_PRM_BLACK_LIST_OCT
W_OP_PRM_BLACK_LIST_MTR_SEC
W_OP_PRM_BLACK_LIST_MTR
W_OP_CFG_SYS

grant select, insert, update, delete on w_st_list_sign_card to W_ACC_CM;
grant select, insert, update, delete on w_st_list_sign_card to W_ACC_CM_APP;
grant select on w_st_list_sign_card to W_ACC_CM_DEV;

grant select, insert, update, delete on w_op_prm_phy_logic_list to W_ACC_CM;
grant select, insert, update, delete on w_op_prm_phy_logic_list to W_ACC_CM_APP;
grant select on w_op_prm_phy_logic_list to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_CARD_SUB to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_CARD_SUB to W_ACC_CM_APP;
grant select on W_OP_PRM_CARD_SUB to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_SYS_OPERATOR to W_ACC_CM;
grant select, insert, update, delete on W_OP_SYS_OPERATOR to W_ACC_CM_APP;
grant select on W_OP_SYS_OPERATOR to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_SYS_GROUP_OPERATOR to W_ACC_CM;
grant select, insert, update, delete on W_OP_SYS_GROUP_OPERATOR to W_ACC_CM_APP;
grant select on W_OP_SYS_GROUP_OPERATOR to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_STATION to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_STATION to W_ACC_CM_APP;
grant select on W_OP_PRM_STATION to W_ACC_CM_DEV;
--
grant select, insert, update, delete on W_OP_PRM_LINE to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_LINE to W_ACC_CM_APP;
grant select on W_OP_PRM_LINE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_DEV_CODE to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_DEV_CODE to W_ACC_CM_APP;
grant select on W_OP_PRM_DEV_CODE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_FARE_TABLE to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_FARE_TABLE to W_ACC_CM_APP;
grant select on W_OP_PRM_FARE_TABLE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_SAM_LIST to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_SAM_LIST to W_ACC_CM_APP;
grant select on W_OP_PRM_SAM_LIST to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_OCT_SEC to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_OCT_SEC to W_ACC_CM_APP;
grant select on W_OP_PRM_BLACK_LIST_OCT_SEC to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_OCT to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_OCT to W_ACC_CM_APP;
grant select on W_OP_PRM_BLACK_LIST_OCT to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_MTR_SEC to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_MTR_SEC to W_ACC_CM_APP;
grant select on W_OP_PRM_BLACK_LIST_MTR_SEC to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_MTR to W_ACC_CM;
grant select, insert, update, delete on W_OP_PRM_BLACK_LIST_MTR to W_ACC_CM_APP;
grant select on W_OP_PRM_BLACK_LIST_MTR to W_ACC_CM_DEV;

grant select, insert, update, delete on W_OP_CFG_SYS to W_ACC_CM;
grant select, insert, update, delete on W_OP_CFG_SYS to W_ACC_CM_APP;
grant select on W_OP_CFG_SYS to W_ACC_CM_DEV;


tk:
W_IC_PDU_ORDER_FORM
W_IC_ES_ORDER_NUM_CHANGE
W_IC_ES_PDU_REPEAT_LOGIC
W_IC_ES_ROLE
W_IC_ES_LEGAL_DEVTYPE
W_IC_ES_INFO_FILE
W_IC_ES_FILE_ERROR
W_IC_ES_FILE_AUDIT
W_IC_ES_STATUS
W_IC_ES_CFG_SYS
W_IC_BC_LOGIC_NO
W_IC_ES_BCP_CONFIG
W_IC_ES_INITI_INFO_BUF
W_IC_ES_HUNCH_INFO_BUF
W_IC_ES_AGAIN_INFO_BUF
W_IC_ES_LOGOUT_INFO_BUF
W_IC_MB_INITI_INFO
W_IC_MB_HUNCH_INFO
W_IC_MB_AGAIN_INFO
W_IC_MB_LOGOUT_INFO

W_SEQ_W_IC_ES_ORDER_NUM_CHANGE
W_SEQ_W_IC_ES_FILE_ERROR
W_SEQ_W_IC_ES_INFO_FILE
W_SEQ_W_IC_ES_FILE_AUDIT

W_UP_IC_ES_GEN_PRODUCE_BILL


grant select, insert, update, delete on W_IC_PDU_ORDER_FORM to W_ACC_CM;
grant select, insert, update, delete on W_IC_PDU_ORDER_FORM to W_ACC_CM_APP;
grant select on W_IC_PDU_ORDER_FORM to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_CM_APP;
grant select on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_CM_APP;
grant select on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_ROLE to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_ROLE to W_ACC_CM_APP;
grant select on W_IC_ES_ROLE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_LEGAL_DEVTYPE to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_LEGAL_DEVTYPE to W_ACC_CM_APP;
grant select on W_IC_ES_LEGAL_DEVTYPE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_INFO_FILE to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_INFO_FILE to W_ACC_CM_APP;
grant select on W_IC_ES_INFO_FILE to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_FILE_ERROR to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_FILE_ERROR to W_ACC_CM_APP;
grant select on W_IC_ES_FILE_ERROR to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_FILE_AUDIT to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_FILE_AUDIT to W_ACC_CM_APP;
grant select on W_IC_ES_FILE_AUDIT to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_STATUS to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_STATUS to W_ACC_CM_APP;
grant select on W_IC_ES_STATUS to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_CFG_SYS to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_CFG_SYS to W_ACC_CM_APP;
grant select on W_IC_ES_CFG_SYS to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_BC_LOGIC_NO to W_ACC_CM;
grant select, insert, update, delete on W_IC_BC_LOGIC_NO to W_ACC_CM_APP;
grant select on W_IC_BC_LOGIC_NO to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_BCP_CONFIG to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_BCP_CONFIG to W_ACC_CM_APP;
grant select on W_IC_ES_BCP_CONFIG to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_INITI_INFO_BUF to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_INITI_INFO_BUF to W_ACC_CM_APP;
grant select on W_IC_ES_INITI_INFO_BUF to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_HUNCH_INFO_BUF to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_HUNCH_INFO_BUF to W_ACC_CM_APP;
grant select on W_IC_ES_HUNCH_INFO_BUF to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_AGAIN_INFO_BUF to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_AGAIN_INFO_BUF to W_ACC_CM_APP;
grant select on W_IC_ES_AGAIN_INFO_BUF to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_ES_LOGOUT_INFO_BUF to W_ACC_CM;
grant select, insert, update, delete on W_IC_ES_LOGOUT_INFO_BUF to W_ACC_CM_APP;
grant select on W_IC_ES_LOGOUT_INFO_BUF to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_MB_INITI_INFO to W_ACC_CM;
grant select, insert, update, delete on W_IC_MB_INITI_INFO to W_ACC_CM_APP;
grant select on W_IC_MB_INITI_INFO to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_MB_HUNCH_INFO to W_ACC_CM;
grant select, insert, update, delete on W_IC_MB_HUNCH_INFO to W_ACC_CM_APP;
grant select on W_IC_MB_HUNCH_INFO to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_MB_AGAIN_INFO to W_ACC_CM;
grant select, insert, update, delete on W_IC_MB_AGAIN_INFO to W_ACC_CM_APP;
grant select on W_IC_MB_AGAIN_INFO to W_ACC_CM_DEV;

grant select, insert, update, delete on W_IC_MB_LOGOUT_INFO to W_ACC_CM;
grant select, insert, update, delete on W_IC_MB_LOGOUT_INFO to W_ACC_CM_APP;
grant select on W_IC_MB_LOGOUT_INFO to W_ACC_CM_DEV;

grant select on W_SEQ_W_IC_ES_ORDER_NUM_CHANGE to W_ACC_CM;
grant select on W_SEQ_W_IC_ES_ORDER_NUM_CHANGE to W_ACC_CM_APP;
grant select on W_SEQ_W_IC_ES_FILE_ERROR to W_ACC_CM;
grant select on W_SEQ_W_IC_ES_FILE_ERROR to W_ACC_CM_APP;
grant select on W_SEQ_W_IC_ES_INFO_FILE to W_ACC_CM;
grant select on W_SEQ_W_IC_ES_INFO_FILE to W_ACC_CM_APP;
grant select on W_SEQ_W_IC_ES_FILE_AUDIT to W_ACC_CM;
grant select on W_SEQ_W_IC_ES_FILE_AUDIT to W_ACC_CM_APP;

grant execute on W_UP_IC_ES_GEN_PRODUCE_BILL to W_ACC_CM;
grant execute on W_UP_IC_ES_GEN_PRODUCE_BILL to W_ACC_CM_APP;
