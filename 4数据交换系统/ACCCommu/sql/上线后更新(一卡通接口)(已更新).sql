select * from w_acc_cm.w_cm_log_busdata_file



comment on table w_acc_cm.W_CM_LOG_BUSDATA_FILE is 'һ��ͨ���ؼ�¼';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.balance_water_no is '������ˮ��';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.file_type is '�ļ�����';
comment on column w_acc_cm.W_CM_LOG_BUSDATA_FILE.insert_time is '����ʱ��';

insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017091601', '0', 'busftp.delay.interval', '30000', 'ftp��ʱȡ�ļ�ʱ�䡣');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017091601', '0', 'busftp.interval', '300000', 'һ��ͨftp���ʱ�䡣');
commit;
