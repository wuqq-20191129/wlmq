-- Add/modify columns 
alter table W_IC_INF_PRETICKET_SALE add tk_file_name VARCHAR2(18);
-- Add comments to the columns 
comment on column W_IC_INF_PRETICKET_SALE.tk_file_name is '原文件名';
comment on column W_IC_INF_PRETICKET_SALE.file_flag is '是否已生成交易文件0:否,1:是,2:逻辑卡号段不一致,3:逻辑卡号段间数量不正确';
