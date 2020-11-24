-- Create table
create global temporary table T#W_ST_RESULTS_NON_RTN
(
  card_logical_id    VARCHAR2(20),
  deposit_fee        NUMBER(7,2),
  card_balance_fee   NUMBER(12,2),
  actual_return_bala NUMBER(12,2),
  penalty            NUMBER(7,2),
  penalty_reason     VARCHAR2(12),
  audt               VARCHAR2(1),
  hdl_flag           VARCHAR2(1),
  apply_datetime     DATE
)
on commit preserve rows;

-- Create table
create table W_ST_FLOW_NON_RTN
(
  water_no     NUMBER(18) not null,
  id           INTEGER,
  apply_bill   VARCHAR2(25),
  apply_action VARCHAR2(1),
  return_flag  VARCHAR2(1),
  return_time  DATE,
  processing   VARCHAR2(1)
);
-- Add comments to the columns 
comment on column W_ST_FLOW_NON_RTN.water_no
  is '��ˮ��';
comment on column W_ST_FLOW_NON_RTN.id
  is 'id��';
comment on column W_ST_FLOW_NON_RTN.apply_bill
  is '���뵥��';
comment on column W_ST_FLOW_NON_RTN.apply_action
  is '���붯��';
comment on column W_ST_FLOW_NON_RTN.return_flag
  is '�˿��־';
comment on column W_ST_FLOW_NON_RTN.return_time
  is '�˿�ʱ��';
comment on column W_ST_FLOW_NON_RTN.processing
  is '����';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_ST_FLOW_NON_RTN
  add constraint PK_W_ST_FLOW_NON_RTN primary key (WATER_NO);


-- Create sequence 
create sequence W_SEQ_W_ST_FLOW_NON_RTN
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;


-- Create table
create global temporary table T#W_ST_FLAG_NON_RTN
(
  return_flag VARCHAR2(1)
)
on commit delete rows;


-- Create table
create table W_ST_CM_LOG_REPORT_LOSS
(
  water_no     NUMBER(18) not null,
  apply_bill   VARCHAR2(25),
  apply_action CHAR(1),
  return_flag  CHAR(2),
  return_time  DATE
);
-- Add comments to the table 
comment on table W_ST_CM_LOG_REPORT_LOSS
  is '��������ʧ/���/�����������̱�';
-- Add comments to the columns 
comment on column W_ST_CM_LOG_REPORT_LOSS.water_no
  is '��ˮ��';
comment on column W_ST_CM_LOG_REPORT_LOSS.apply_bill
  is 'ƾ֤ID';
comment on column W_ST_CM_LOG_REPORT_LOSS.apply_action
  is '����ʽ(1:��ѯ;2:����ɹ�֪ͨ)';
comment on column W_ST_CM_LOG_REPORT_LOSS.return_flag
  is '���ؽ��(00:��������;01:֤��������;02:֤���ѹ���;03:��������;04:�ظ�����;05:������,δ�ƿ�;06:�����벹��;07:�������˿�;10:�޹�ʧ,���ɽ��)';
comment on column W_ST_CM_LOG_REPORT_LOSS.return_time
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_ST_CM_LOG_REPORT_LOSS
  add constraint PK_W_ST_CM_LOG_REPORT_LOSS primary key (WATER_NO);


-- Create sequence w_seq_w_st_cm_log_report_loss
create sequence W_SEQ_W_ST_CM_LOG_REPORT_LOSS
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;


-- Create table
create table W_ST_LIST_REPORT_LOSS
(
  water_no         NUMBER(18) not null,
  apply_bill       VARCHAR2(25),
  create_time      DATE,
  busniss_type     CHAR(1),
  identify_type    CHAR(1),
  identity_id      VARCHAR2(18),
  line_id          CHAR(2),
  station_id       CHAR(2),
  dev_type_id      CHAR(2),
  device_id        CHAR(3),
  apply_name       VARCHAR2(20),
  apply_sex        CHAR(1),
  expired_date     CHAR(8),
  tel_no           VARCHAR2(16),
  fax              VARCHAR2(16),
  address          VARCHAR2(200),
  operator_id      VARCHAR2(10),
  apply_datetime   DATE,
  shift_id         CHAR(2),
  card_app_flag    CHAR(1),
  card_main_id     CHAR(2),
  card_sub_id      CHAR(2),
  card_logical_id  VARCHAR2(20),
  card_physical_id VARCHAR2(20),
  card_print_id    VARCHAR2(20),
  balance_water_no CHAR(10)
);
-- Add comments to the table 
comment on table W_ST_LIST_REPORT_LOSS
  is '��������ʧ״̬��';
-- Add comments to the columns 
comment on column W_ST_LIST_REPORT_LOSS.water_no
  is '��ˮ��';
comment on column W_ST_LIST_REPORT_LOSS.apply_bill
  is 'ƾ֤ID';
comment on column W_ST_LIST_REPORT_LOSS.create_time
  is '����ʱ��';
comment on column W_ST_LIST_REPORT_LOSS.busniss_type
  is 'ҵ������';
comment on column W_ST_LIST_REPORT_LOSS.identify_type
  is '֤������(1:���֤;2:ѧ��֤;3:����֤;4:���˿�;5:Ա����;9:����)';
comment on column W_ST_LIST_REPORT_LOSS.identity_id
  is '֤������';
comment on column W_ST_LIST_REPORT_LOSS.line_id
  is '��·';
comment on column W_ST_LIST_REPORT_LOSS.station_id
  is '��վ';
comment on column W_ST_LIST_REPORT_LOSS.dev_type_id
  is '�豸����';
comment on column W_ST_LIST_REPORT_LOSS.device_id
  is '�豸ID';
comment on column W_ST_LIST_REPORT_LOSS.apply_name
  is '����';
comment on column W_ST_LIST_REPORT_LOSS.apply_sex
  is '�Ա�';
comment on column W_ST_LIST_REPORT_LOSS.expired_date
  is '��Ч����';
comment on column W_ST_LIST_REPORT_LOSS.tel_no
  is '�绰';
comment on column W_ST_LIST_REPORT_LOSS.fax
  is '����';
comment on column W_ST_LIST_REPORT_LOSS.address
  is '��ַ';
comment on column W_ST_LIST_REPORT_LOSS.operator_id
  is '����Ա';
comment on column W_ST_LIST_REPORT_LOSS.apply_datetime
  is '����ʱ��';
comment on column W_ST_LIST_REPORT_LOSS.shift_id
  is '���';
comment on column W_ST_LIST_REPORT_LOSS.card_app_flag
  is '��Ӧ�ñ�־';
comment on column W_ST_LIST_REPORT_LOSS.card_main_id
  is 'Ʊ��������';
comment on column W_ST_LIST_REPORT_LOSS.card_sub_id
  is 'Ʊ��������';
comment on column W_ST_LIST_REPORT_LOSS.card_logical_id
  is '�߼�����';
comment on column W_ST_LIST_REPORT_LOSS.card_physical_id
  is '������';
comment on column W_ST_LIST_REPORT_LOSS.card_print_id
  is '��ӡ��';
comment on column W_ST_LIST_REPORT_LOSS.balance_water_no
  is '������ˮ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_ST_LIST_REPORT_LOSS
  add constraint PK_ST_LIST_REPORT_LOSS primary key (WATER_NO);
  
  
-- Create table
create table W_ST_LIST_REPORT_LOSS_PEND
(
  water_no         NUMBER(18) not null,
  apply_bill       VARCHAR2(25),
  create_time      DATE,
  busniss_type     CHAR(1),
  identify_type    CHAR(1),
  identity_id      VARCHAR2(18),
  line_id          CHAR(2),
  station_id       CHAR(2),
  dev_type_id      CHAR(2),
  device_id        CHAR(3),
  apply_name       VARCHAR2(20),
  apply_sex        CHAR(1),
  expired_date     CHAR(8),
  tel_no           VARCHAR2(16),
  fax              VARCHAR2(16),
  address          VARCHAR2(200),
  operator_id      VARCHAR2(10),
  apply_datetime   DATE,
  shift_id         CHAR(2),
  card_app_flag    CHAR(1),
  card_main_id     CHAR(2),
  card_sub_id      CHAR(2),
  card_logical_id  VARCHAR2(20),
  card_physical_id VARCHAR2(20),
  card_print_id    VARCHAR2(20),
  balance_water_no CHAR(10)
);
-- Add comments to the table 
comment on table W_ST_LIST_REPORT_LOSS_PEND
  is '��������ʧ/���/��������������';

-- Create table
create table W_ST_LIST_SIGN_CARD_PEND
(
  water_no          NUMBER(18),
  line_id           CHAR(2),
  station_id        CHAR(2),
  dev_type_id       CHAR(2),
  device_id         CHAR(3),
  apply_name        CHAR(8),
  apply_sex         CHAR(1),
  identity_type     CHAR(1),
  identity_id       CHAR(18),
  expired_date      DATE,
  tel_no            CHAR(16),
  fax               CHAR(16),
  address           VARCHAR2(200),
  operator_id       CHAR(6),
  apply_datetime    DATE,
  shift_id          CHAR(2),
  card_app_flag     CHAR(1),
  balance_water_no  CHAR(10),
  file_name         VARCHAR2(30),
  check_flag        CHAR(1),
  card_main_id      CHAR(2),
  card_sub_id       CHAR(2),
  app_business_type CHAR(1) default '1'
);
-- Add comments to the table 
comment on table W_ST_LIST_SIGN_CARD_PEND
  is '����������������';
  
-- Create table
create table W_ST_ID_NON_RTN
(
  id INTEGER not null
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_ST_ID_NON_RTN
  add constraint PK_W_ST_ID_NON_RETURN primary key (ID);


