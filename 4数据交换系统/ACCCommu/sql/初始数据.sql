insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'sys.squadday', '0200', '系统运营日');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.mobile.control', '0', '手机支付业务处理控制。1：启用 0：不启用。');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.mobile.interval', '60000', '手机支付平台消息下发时间间隔');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK)
values ('2017120101', '0', 'business.np.control', '0', '互联网支付业务处理控制。1：启用 0：不启用。');
insert into w_acc_st.w_st_cfg_sys_base (VERSION_NO, RECORD_FLAG, CFG_KEY, CFG_VALUE, REMARK, ROWID)
values ('2017120101', '0', 'sys.non_return_day', '7', '非即时退款等待日', null);
commit;


insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.moc.max', '100000', '交通部一卡通黑名单最大记录数');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('banklist.moc.max', '100000', '交通部一卡通白名单');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.metro.max', '30000', '地铁黑名单最大记录数');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.oct.max', '20000', '公交一卡通黑名单最大记录数');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.section.max', '20', '黑名单段最大记录数');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('commu.cache.interval', '60000', '通讯缓存常量更新时间');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('pattern.reg.mtr', '^000041000000.[0-9]*$', '地铁逻辑卡号正则表达式');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('pattern.reg', '^[0-9]*$', '逻辑卡号正则表达式');
insert into w_acc_cm.W_CM_CFG_SYS (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('blacklist.length', '20', '逻辑卡号字符长度');
commit;

insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TST', 0, '20180101', '售存数据文件');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('THD', 0, '20180101', '车站上交数据文件');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TBH', 0, '20180101', '收益组上交数据文件');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('TDS', 0, '20180101', '配票数据文件');
insert into W_ACC_CM.W_CM_SEQ_TK_FILE (FILE_TYPE, SEQ, ALTER_DAY, REMARK)
values ('PSD', 0, '20180101', '预制票数据文件');
commit;
