-- Add/modify columns 
alter table W_IC_INF_PRETICKET_SALE add tk_file_name VARCHAR2(18);
-- Add comments to the columns 
comment on column W_IC_INF_PRETICKET_SALE.tk_file_name is 'ԭ�ļ���';
comment on column W_IC_INF_PRETICKET_SALE.file_flag is '�Ƿ������ɽ����ļ�0:��,1:��,2:�߼����Ŷβ�һ��,3:�߼����Ŷμ���������ȷ';
