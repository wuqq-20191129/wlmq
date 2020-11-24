--清理表数据
truncate table w_ic_err_card_repeat_detail;
truncate table w_ic_es_again_info;
truncate table w_ic_es_again_info_buf;
truncate table w_ic_es_again_info_svt;
truncate table w_ic_es_bcp_config;
truncate table w_ic_es_cfg_sys;
truncate table w_ic_es_file_audit;
truncate table w_ic_es_file_error;
truncate table w_ic_es_hunch_info;
truncate table w_ic_es_hunch_info_buf;
truncate table w_ic_es_info_file;
truncate table w_ic_es_initi_info;
truncate table w_ic_es_initi_info_buf;
truncate table w_ic_es_legal_devtype;
truncate table w_ic_es_logout_info;
truncate table w_ic_es_logout_info_buf;
truncate table w_ic_es_order_num_change;
truncate table w_ic_es_pdu_repeat_logic;
truncate table w_ic_es_role;
truncate table w_ic_es_status;
truncate table w_ic_mb_initi_info;
truncate table w_ic_mb_pdu_order_form;



--初始化数据
insert into w_ic_es_bcp_config (SERVER, DB, ACCOUNT, PASSWORD, ENC_FLAG, REMARK)
values ('10.99.10.15:1521', 'waccdb', 'w_acc_tk_app', 'w_acc_tk_app', '0', null);
insert into w_ic_es_cfg_sys (SENDER_CODE, CITY_CODE, BUSI_CODE, CARD_VERSION, APP_VERSION, KEY_VERSION)
values ('8303', '8300', '0003', '1000', '1000', '00');
insert into w_ic_es_legal_devtype (DEV_TYPE_ID)
values ('09');
insert into w_ic_es_legal_devtype (DEV_TYPE_ID)
values ('15');
insert into w_ic_es_role (SYS_GROUP_ID, GROUP_LEVEL, REMARK)
values ('71', '71', '管理员');
insert into w_ic_es_role (SYS_GROUP_ID, GROUP_LEVEL, REMARK)
values ('72', '72', '制票操作员');
insert into w_ic_es_role (SYS_GROUP_ID, GROUP_LEVEL, REMARK)
values ('73', '73', '维护人员');
commit;

--删除序列号
drop sequence w_seq_w_ic_es_file_audit;
drop sequence w_seq_w_ic_es_file_error;
drop sequence w_seq_w_ic_es_info_file;
drop sequence w_seq_w_ic_es_order_num_change;

--重建序列号
create sequence W_SEQ_W_IC_ES_FILE_AUDIT
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_IC_ES_FILE_ERROR
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_IC_ES_INFO_FILE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_IC_ES_ORDER_NUM_CHANGE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;

--授权es表
grant select, insert, update, delete on w_ic_es_again_info to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_again_info to w_acc_tk_app;
grant select on w_ic_es_again_info to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_again_info_buf to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_again_info_buf to w_acc_tk_app;
grant select on w_ic_es_again_info_buf to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_again_info_svt to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_again_info_svt to w_acc_tk_app;
grant select on w_ic_es_again_info_svt to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_bcp_config to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_bcp_config to w_acc_tk_app;
grant select on w_ic_es_bcp_config to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_cfg_sys to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_cfg_sys to w_acc_tk_app;
grant select on w_ic_es_cfg_sys to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_file_audit to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_file_audit to w_acc_tk_app;
grant select on w_ic_es_file_audit to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_file_error to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_file_error to w_acc_tk_app;
grant select on w_ic_es_file_error to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_hunch_info to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_hunch_info to w_acc_tk_app;
grant select on w_ic_es_hunch_info to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_hunch_info_buf to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_hunch_info_buf to w_acc_tk_app;
grant select on w_ic_es_hunch_info_buf to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_info_file to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_info_file to w_acc_tk_app;
grant select on w_ic_es_info_file to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_initi_info to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_initi_info to w_acc_tk_app;
grant select on w_ic_es_initi_info to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_initi_info_buf to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_initi_info_buf to w_acc_tk_app;
grant select on w_ic_es_initi_info_buf to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_legal_devtype to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_legal_devtype to w_acc_tk_app;
grant select on w_ic_es_legal_devtype to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_logout_info to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_logout_info to w_acc_tk_app;
grant select on w_ic_es_logout_info to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_logout_info_buf to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_logout_info_buf to w_acc_tk_app;
grant select on w_ic_es_logout_info_buf to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_order_num_change to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_order_num_change to w_acc_tk_app;
grant select on w_ic_es_order_num_change to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_pdu_repeat_logic to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_pdu_repeat_logic to w_acc_tk_app;
grant select on w_ic_es_pdu_repeat_logic to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_role to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_role to w_acc_tk_app;
grant select on w_ic_es_role to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_es_status to w_acc_cm_app;
grant select, insert, update, delete on w_ic_es_status to w_acc_tk_app;
grant select on w_ic_es_status to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_mb_initi_info to w_acc_cm_app;
grant select, insert, update, delete on w_ic_mb_initi_info to w_acc_tk_app;
grant select on w_ic_mb_initi_info to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_mb_pdu_order_form to w_acc_cm_app;
grant select, insert, update, delete on w_ic_mb_pdu_order_form to w_acc_tk_app;
grant select on w_ic_mb_pdu_order_form to w_acc_tk_dev;
--授权序列号
grant select on w_seq_w_ic_es_file_audit to w_acc_cm_app;
grant select on w_seq_w_ic_es_file_audit to w_acc_tk_app;
grant select on w_seq_w_ic_es_file_error to w_acc_cm_app;
grant select on w_seq_w_ic_es_file_error to w_acc_tk_app;
grant select on w_seq_w_ic_es_info_file to w_acc_cm_app;
grant select on w_seq_w_ic_es_info_file to w_acc_tk_app;
grant select on w_seq_w_ic_es_order_num_change to w_acc_cm_app;
grant select on w_seq_w_ic_es_order_num_change to w_acc_tk_app;
grant select on W_SEQ_W_IC_PDU_PDU_BILL_DTL to w_acc_cm_app;
grant select on W_SEQ_W_IC_PDU_PDU_BILL_DTL to w_acc_tk_app;
--授权存储过程
grant execute on W_UP_IC_ES_GEN_PRODUCE_BILL to w_acc_cm_app;
grant execute on W_UP_IC_ES_GEN_PRODUCE_BILL to w_acc_tk_app;
grant execute on w_up_ic_out_getbillnoformal to w_acc_cm_app;
grant execute on w_up_ic_out_getbillnoformal to w_acc_tk_app;
--授权tk表
grant select, insert, update, delete on w_ic_mb_pdu_order_form to w_acc_cm_app;
grant select, insert, update, delete on w_ic_mb_pdu_order_form to w_acc_tk_app;
grant select on w_ic_mb_pdu_order_form to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_PDU_PRODUCE_BILL to w_acc_cm_app;
grant select, insert, update, delete on W_IC_PDU_PRODUCE_BILL to w_acc_tk_app;
grant select on W_IC_PDU_PRODUCE_BILL to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_PDU_ORDER_FORM to w_acc_cm_app;
grant select, insert, update, delete on W_IC_PDU_ORDER_FORM to w_acc_tk_app;
grant select on W_IC_PDU_ORDER_FORM to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_PDU_PLAN_ORDER_MAPPING to w_acc_cm_app;
grant select, insert, update, delete on W_IC_PDU_PLAN_ORDER_MAPPING to w_acc_tk_app;
grant select on W_IC_PDU_PLAN_ORDER_MAPPING to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_OUT_DATE_PLAN to w_acc_cm_app;
grant select, insert, update, delete on W_IC_OUT_DATE_PLAN to w_acc_tk_app;
grant select on W_IC_OUT_DATE_PLAN to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_out_bill_detail to w_acc_cm_app;
grant select, insert, update, delete on w_ic_out_bill_detail to w_acc_tk_app;
grant select on w_ic_out_bill_detail to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_COD_STATION_CONTRAST to w_acc_cm_app;
grant select, insert, update, delete on W_IC_COD_STATION_CONTRAST to w_acc_tk_app;
grant select on W_IC_COD_STATION_CONTRAST to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_prm_bill_current_date to w_acc_cm_app;
grant select, insert, update, delete on w_ic_prm_bill_current_date to w_acc_tk_app;
grant select on w_ic_prm_bill_current_date to w_acc_tk_dev;
grant select, insert, update, delete on w_ic_prm_bill_current_flow to w_acc_cm_app;
grant select, insert, update, delete on w_ic_prm_bill_current_flow to w_acc_tk_app;
grant select on w_ic_prm_bill_current_flow to w_acc_tk_dev;
grant select, insert, update, delete on W_IC_LOG_BILL_CURRENT_FLOW_TMP to w_acc_cm_app;
grant select, insert, update, delete on W_IC_LOG_BILL_CURRENT_FLOW_TMP to w_acc_tk_app;
grant select on W_IC_LOG_BILL_CURRENT_FLOW_TMP to w_acc_tk_dev;
grant select, insert, update, delete on W_T#IC_PDU_PLAN_ORDER_MAPPING to w_acc_cm_app;
grant select, insert, update, delete on W_T#IC_PDU_PLAN_ORDER_MAPPING to w_acc_tk_app;
grant select on W_T#IC_PDU_PLAN_ORDER_MAPPING to w_acc_tk_dev;
grant select, insert, update, delete on W_T#IC_PDU_ORDER_FORM to w_acc_cm_app;
grant select, insert, update, delete on W_T#IC_PDU_ORDER_FORM to w_acc_tk_app;
grant select on W_T#IC_PDU_ORDER_FORM to w_acc_tk_dev;
grant select, insert, update, delete on W_T#IC_PDU_PRODUCE_BILL_DETAIL to w_acc_cm_app;
grant select, insert, update, delete on W_T#IC_PDU_PRODUCE_BILL_DETAIL to w_acc_tk_app;
grant select on W_T#IC_PDU_PRODUCE_BILL_DETAIL to w_acc_tk_dev;
grant select, insert, update, delete on W_T#IC_PDU_RESULT_DETAIL to w_acc_cm_app;
grant select, insert, update, delete on W_T#IC_PDU_RESULT_DETAIL to w_acc_tk_app;
grant select on W_T#IC_PDU_RESULT_DETAIL to w_acc_tk_dev;


--w_ic_err_card_repeat_detail;

/*
truncate table W_IC_PDU_PRODUCE_BILL;
truncate table W_IC_PDU_ORDER_FORM;
truncate table W_IC_PDU_PLAN_ORDER_MAPPING;
truncate table W_IC_OUT_DATE_PLAN;
truncate table W_ic_out_bill_detail;
truncate table W_IC_COD_STATION_CONTRAST;
truncate table W_ic_prm_bill_current_date;
truncate table W_ic_prm_bill_current_flow;
truncate table W_IC_LOG_BILL_CURRENT_FLOW_TMP;
delete W_T#IC_PDU_PLAN_ORDER_MAPPING;
delete W_T#IC_PDU_ORDER_FORM
delete W_T#IC_PDU_PRODUCE_BILL_DETAIL
delete W_T#IC_PDU_RESULT_DETAIL

grant select on w_ic_es_again_info to W_ACC_TK_RPT;
grant select on w_ic_es_again_info_buf to W_ACC_TK_RPT;
grant select on w_ic_es_again_info_svt to W_ACC_TK_RPT;
grant select on w_ic_es_bcp_config to W_ACC_TK_RPT;
grant select on w_ic_es_cfg_sys to W_ACC_TK_RPT;
grant select on w_ic_es_file_audit to W_ACC_TK_RPT;
grant select on w_ic_es_file_error to W_ACC_TK_RPT;
grant select on w_ic_es_hunch_info to W_ACC_TK_RPT;
grant select on w_ic_es_hunch_info_buf to W_ACC_TK_RPT;
grant select on w_ic_es_info_file to W_ACC_TK_RPT;
grant select on w_ic_es_initi_info to W_ACC_TK_RPT;
grant select on w_ic_es_initi_info_buf to W_ACC_TK_RPT;
grant select on w_ic_es_legal_devtype to W_ACC_TK_RPT;
grant select on w_ic_es_logout_info to W_ACC_TK_RPT;
grant select on w_ic_es_logout_info_buf to W_ACC_TK_RPT;
grant select on w_ic_es_order_num_change to W_ACC_TK_RPT;
grant select on w_ic_es_pdu_repeat_logic to W_ACC_TK_RPT;
grant select on w_ic_es_role to W_ACC_TK_RPT;
grant select on w_ic_es_status to W_ACC_TK_RPT;
*/
