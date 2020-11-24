--清表
truncate table w_cm_ec_connect_log;
truncate table w_cm_ec_ftp_log;
truncate table w_cm_ec_log;
truncate table w_cm_ec_log_level;
truncate table w_cm_ec_msg_priority;
truncate table w_cm_ec_operator_log;
truncate table w_cm_ec_recv_send_log;
--删除序列
drop sequence w_seq_w_cm_ec_connect_log;
drop sequence w_seq_w_cm_ec_ftp_log;
drop sequence w_seq_w_cm_ec_log;
drop sequence w_seq_w_cm_ec_recv_send_log;
--重建序列
create sequence W_SEQ_W_CM_EC_CONNECT_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_CM_EC_FTP_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_CM_EC_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_CM_EC_RECV_SEND_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
--授权
grant select, insert, update, delete on w_cm_ec_connect_log to w_acc_cm_app;
grant select on w_cm_ec_connect_log to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_ftp_log to w_acc_cm_app;
grant select on w_cm_ec_ftp_log to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_log to w_acc_cm_app;
grant select on w_cm_ec_log to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_log_level to w_acc_cm_app;
grant select on w_cm_ec_log_level to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_msg_priority to w_acc_cm_app;
grant select on w_cm_ec_msg_priority to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_operator_log to w_acc_cm_app;
grant select on w_cm_ec_operator_log to w_acc_cm_dev;
grant select, insert, update, delete on w_cm_ec_recv_send_log to w_acc_cm_app;
grant select on w_cm_ec_recv_send_log to w_acc_cm_dev;

grant select on W_SEQ_W_CM_EC_CONNECT_LOG to w_acc_cm_app;
grant select on W_SEQ_W_CM_EC_FTP_LOG to w_acc_cm_app;
grant select on W_SEQ_W_CM_EC_LOG to w_acc_cm_app;
grant select on W_SEQ_W_CM_EC_RECV_SEND_LOG to w_acc_cm_app;

grant select, insert, update, delete on w_cm_ec_connect_log to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_ftp_log to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_log to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_log_level to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_msg_priority to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_operator_log to w_acc_tk_app;
grant select, insert, update, delete on w_cm_ec_recv_send_log to w_acc_tk_app;

grant select on W_SEQ_W_CM_EC_CONNECT_LOG to w_acc_tk_app;
grant select on W_SEQ_W_CM_EC_FTP_LOG to w_acc_tk_app;
grant select on W_SEQ_W_CM_EC_LOG to w_acc_tk_app;
grant select on W_SEQ_W_CM_EC_RECV_SEND_LOG to w_acc_tk_app;
