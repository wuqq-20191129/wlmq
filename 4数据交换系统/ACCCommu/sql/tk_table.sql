-- Create table
create table W_IC_INF_PRETICKET_SALE
(
  dept_id            CHAR(4) not null,
  tickettype_id      CHAR(4) not null,
  value              INTEGER not null,
  quantity           INTEGER not null,
  logical_begin      varchar2(20),
  logical_end        varchar2(20),
  sale_time          DATE not null,
  operator_id        varchar2(6),
  report_date        DATE not null,
  file_flag          CHAR(1) default '0' not null,
  file_name          VARCHAR2(18)
);
-- Add comments to the table 
comment on table W_IC_INF_PRETICKET_SALE
  is 'Ԥ��Ʊ��������';
-- Add comments to the columns 
comment on column W_IC_INF_PRETICKET_SALE.dept_id
  is '��վ����';
comment on column W_IC_INF_PRETICKET_SALE.quantity
  is '����';
comment on column W_IC_INF_PRETICKET_SALE.report_date
  is '��������';
comment on column W_IC_INF_PRETICKET_SALE.sale_time
  is '����ʱ��';
comment on column W_IC_INF_PRETICKET_SALE.tickettype_id
  is 'Ʊ������';
comment on column W_IC_INF_PRETICKET_SALE.value
  is '��ֵ';
comment on column W_IC_INF_PRETICKET_SALE.logical_begin
  is '��ʼ�߼�����';
comment on column W_IC_INF_PRETICKET_SALE.logical_end
  is '�����߼�����';
comment on column W_IC_INF_PRETICKET_SALE.operator_id
  is '����Ա';
comment on column W_IC_INF_PRETICKET_SALE.file_flag
  is '�Ƿ������ɽ����ļ�0:��,1:��';
comment on column W_IC_INF_PRETICKET_SALE.file_name
  is '�����ļ���';
grant select, insert, update, delete on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_CM_APP;
grant select, insert, update, delete on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_TK_APP;
grant select on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_TK_DEV;
