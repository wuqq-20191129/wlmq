insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'sys.squadday', '0200', 'ϵͳ��Ӫ��');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.mobile.control', '0', '�ֻ�֧��ҵ������ơ�1������ 0�������á�');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.mobile.interval', '60000', '�ֻ�֧��ƽ̨��Ϣ�·�ʱ����');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.np.control', '0', '������֧��ҵ������ơ�1������ 0�������á�');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK, ROWID)
values ('2017120101', '0', 'sys.non_return_day', '7', '�Ǽ�ʱ�˿�ȴ���', null);
commit;


insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.moc.max', '100000', '��ͨ��һ��ͨ����������¼��');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('banklist.moc.max', '100000', '��ͨ��һ��ͨ������');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.metro.max', '30000', '��������������¼��');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.oct.max', '20000', '����һ��ͨ����������¼��');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.section.max', '20', '������������¼��');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('commu.cache.interval', '60000', 'ͨѶ���泣������ʱ��');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('pattern.reg.mtr', '^000041000000.[0-9]*$', '�����߼�����������ʽ');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('pattern.reg', '^[0-9]*$', '�߼�����������ʽ');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.length', '20', '�߼������ַ�����');
commit;

insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TST', 0, '20180101', '�۴������ļ�');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('THD', 0, '20180101', '��վ�Ͻ������ļ�');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TBH', 0, '20180101', '�������Ͻ������ļ�');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TDS', 0, '20180101', '��Ʊ�����ļ�');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('PSD', 0, '20180101', 'Ԥ��Ʊ�����ļ�');
commit;
