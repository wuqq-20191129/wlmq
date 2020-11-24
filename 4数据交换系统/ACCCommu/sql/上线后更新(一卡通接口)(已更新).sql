select * from w_acc_cm.w_cm_log_busdata_file



comment on table w_acc_cm.W_CM_LOG_BUSDATA_FILE is '一卡通下载记录';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.balance_water_no is '清算流水号';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.file_type is '文件类型';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.insert_time is '插入时间';

insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017091601', '0', 'busftp.delay.interval', '30000', 'ftp延时取文件时间。');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017091601', '0', 'busftp.interval', '300000', '一卡通ftp间隔时间。');
commit;
