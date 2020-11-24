-- Create table
create table W_OL_QRCODE_TKCODE
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  acc_seq        NUMBER(18),
  qrcode         VARCHAR2(64),
  return_code    VARCHAR2(2),
  err_code       VARCHAR2(2),
  order_no       VARCHAR2(14),
  phone_no       VARCHAR2(11),
  sale_fee       NUMBER(18),
  sale_times     NUMBER(18),
  deal_fee       NUMBER(18),
  valid_time     VARCHAR2(14),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRCODE_TKCODE
  is '��ά��ȡƱ��';
-- Add comments to the columns 
comment on column W_OL_QRCODE_TKCODE.message_id
  is '��Ϣ����';
comment on column W_OL_QRCODE_TKCODE.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRCODE_TKCODE.termina_no
  is '�ն˱��';
comment on column W_OL_QRCODE_TKCODE.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_QRCODE_TKCODE.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRCODE_TKCODE.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRCODE_TKCODE.qrcode
  is '��ά��';
comment on column W_OL_QRCODE_TKCODE.return_code
  is '��Ӧ��: 00:�ɹ�����,����:�����ɹ�';
comment on column W_OL_QRCODE_TKCODE.err_code
  is '�������';
comment on column W_OL_QRCODE_TKCODE.order_no
  is '������';
comment on column W_OL_QRCODE_TKCODE.phone_no
  is '�ֻ���';
comment on column W_OL_QRCODE_TKCODE.sale_fee
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRCODE_TKCODE.sale_times
  is '���۵���Ʊ����';
comment on column W_OL_QRCODE_TKCODE.deal_fee
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRCODE_TKCODE.valid_time
  is '��ά����Чʱ��';
comment on column W_OL_QRCODE_TKCODE.insert_date
  is '����ʱ��';
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRCODE_TKCODE to W_ACC_OL_APP;
grant select on W_OL_QRCODE_TKCODE to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRCODE_TKCODE to W_ACC_ST_APP;


-- Create sequence 
create sequence W_SEQ_W_OL_QRCODE_TKCODE
minvalue 1
maxvalue 2147483647
start with 1
increment by 1
cache 10;

grant select on W_SEQ_W_OL_QRCODE_TKCODE to W_ACC_OL_APP;



-- Create table
create table W_OL_QRCODE_TKSTATUS
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  acc_seq        NUMBER(18),
  tk_status      VARCHAR2(2),
  order_no       VARCHAR2(14),
  sale_times     NUMBER(18),
  take_times     NUMBER(18),
  sale_fee       NUMBER(18),
  valid_time     VARCHAR2(14),
  return_code    VARCHAR2(2),
  err_code       VARCHAR2(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRCODE_TKSTATUS
  is '��ά��ȡƱ״̬';
-- Add comments to the columns 
comment on column W_OL_QRCODE_TKSTATUS.message_id
  is '��Ϣ����';
comment on column W_OL_QRCODE_TKSTATUS.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRCODE_TKSTATUS.termina_no
  is '�ն˱��';
comment on column W_OL_QRCODE_TKSTATUS.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_QRCODE_TKSTATUS.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRCODE_TKSTATUS.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRCODE_TKSTATUS.tk_status
  is '����״̬:00������δִ�У�01������������ɣ�02������ȡ����80��δ��Ʊ�򲻴��ڣ�81:��������ɣ���ȡƱ����82����ά���ѹ���Ч��83����Ʊ��ȡƱʼ��վ��һ��';
comment on column W_OL_QRCODE_TKSTATUS.order_no
  is '������';
comment on column W_OL_QRCODE_TKSTATUS.take_times
  is '��ȡ����Ʊ����';
comment on column W_OL_QRCODE_TKSTATUS.sale_times
  is '���۵���Ʊ����';
comment on column W_OL_QRCODE_TKSTATUS.sale_fee
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRCODE_TKSTATUS.valid_time
  is '��ά����Чʱ��';
comment on column W_OL_QRCODE_TKSTATUS.insert_date
  is '����ʱ��';
comment on column W_OL_QRCODE_TKSTATUS.return_code
  is '��Ӧ��: 00:�ɹ�����,����:�����ɹ�';
comment on column W_OL_QRCODE_TKSTATUS.err_code
  is '�������';
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRCODE_TKSTATUS to W_ACC_OL_APP;
grant select on W_OL_QRCODE_TKSTATUS to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRCODE_TKSTATUS to W_ACC_ST_APP;


-- Create sequence 
create sequence W_SEQ_W_OL_QRCODE_TKSTATUS
minvalue 1
maxvalue 2147483647
start with 1
increment by 1
cache 10;

grant select on W_SEQ_W_OL_QRCODE_TKSTATUS to W_ACC_OL_APP;



-- Create table
create table W_OL_QRCODE_CANCEL
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  acc_seq        NUMBER(18),
  tk_status      VARCHAR2(2),
  order_no       VARCHAR2(14),
  return_code    VARCHAR2(2),
  err_code       VARCHAR2(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRCODE_CANCEL
  is '��ά��ȡ��';
-- Add comments to the columns 
comment on column W_OL_QRCODE_CANCEL.message_id
  is '��Ϣ����';
comment on column W_OL_QRCODE_CANCEL.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRCODE_CANCEL.termina_no
  is '�ն˱��';
comment on column W_OL_QRCODE_CANCEL.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_QRCODE_CANCEL.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRCODE_CANCEL.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRCODE_CANCEL.tk_status
  is '����״̬:00������δִ�У�01������������ɣ�02������ȡ����80��δ��Ʊ�򲻴��ڣ�81:��������ɣ���ȡƱ����82����ά���ѹ���Ч��83����Ʊ��ȡƱʼ��վ��һ��';
comment on column W_OL_QRCODE_CANCEL.order_no
  is '������';
comment on column W_OL_QRCODE_CANCEL.insert_date
  is '����ʱ��';
comment on column W_OL_QRCODE_CANCEL.return_code
  is '��Ӧ��: 00:�ɹ�����,����:�����ɹ�';
comment on column W_OL_QRCODE_CANCEL.err_code
  is '�������';
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRCODE_CANCEL to W_ACC_OL_APP;
grant select on W_OL_QRCODE_CANCEL to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRCODE_CANCEL to W_ACC_ST_APP;


-- Create sequence 
create sequence W_SEQ_W_OL_QRCODE_CANCEL
minvalue 1
maxvalue 2147483647
start with 1
increment by 1
cache 10;

grant select on W_SEQ_W_OL_QRCODE_CANCEL to W_ACC_OL_APP;
