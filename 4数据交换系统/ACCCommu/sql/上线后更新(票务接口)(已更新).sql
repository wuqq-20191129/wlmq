alter table w_acc_tk.W_IC_INF_INCOMEDEP_HANDIN add file_name VARCHAR2(30);
comment on column w_acc_tk.W_IC_INF_INCOMEDEP_HANDIN.file_name  is '文件名';
alter table w_acc_tk.W_ic_inf_station_handin add file_name VARCHAR2(30);
comment on column w_acc_tk.W_ic_inf_station_handin.file_name  is '文件名';
alter table w_acc_tk.W_ic_inf_station_sale add file_name VARCHAR2(30);
comment on column w_acc_tk.W_ic_inf_station_sale.file_name  is '文件名';

update w_acc_st.w_st_cfg_sys_base set CFG_VALUE='5000' where RECORD_FLAG='0' and CFG_KEY='busftp.delay.interval';
commit;
