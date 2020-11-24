-- Create sequence 
create sequence w_acc_ol.W_SEQ_W_OL_AIR_SALE
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;
-- Grant/Revoke object privileges 
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE to W_ACC_OL_APP;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE to W_ACC_OL_DEV;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE to W_ACC_ST_APP;

-- Create sequence 
create sequence w_acc_ol.W_SEQ_W_OL_AIR_SALE_CFM
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;
-- Grant/Revoke object privileges 
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE_CFM to W_ACC_OL_APP;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE_CFM to W_ACC_OL_DEV;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_SALE_CFM to W_ACC_ST_APP;

-- Create sequence 
create sequence w_acc_ol.W_SEQ_W_OL_AIR_CHARGE
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;
-- Grant/Revoke object privileges 
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE to W_ACC_OL_APP;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE to W_ACC_OL_DEV;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE to W_ACC_ST_APP;

-- Create sequence 
create sequence w_acc_ol.W_SEQ_W_OL_AIR_CHARGE_CFM
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;
-- Grant/Revoke object privileges 
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE_CFM to W_ACC_OL_APP;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE_CFM to W_ACC_OL_DEV;
grant select on w_acc_ol.W_SEQ_W_OL_AIR_CHARGE_CFM to W_ACC_ST_APP;

-- Create table
create table w_acc_ol.W_OL_AIR_SALE
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  sys_ref_no NUMBER(18),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  branches_code  VARCHAR2(16),
  card_type      VARCHAR2(4),
  phone_no       varchar2(11),
  imsi           varchar2(15),
  imei           varchar2(15),
  product_code   CHAR(4) default '0991',
  city_code      CHAR(4) default '8300',
  business_code  CHAR(4) default '0003',
  deal_day       char(8),
  deal_dev_code  varchar2(5) default '15802',
  card_ver       char(2) default '10',
  card_day       char(8),
  card_app_day   char(8),
  card_app_ver   char(2) default '10',
  card_logical_id        varchar2(20),
  card_phy_id            varchar2(20),
  expiry_date            char(8),
  face_value             number(18) default 0,
  deposit_fee            number(18) default 0,
  app_expiry_start       char(8)    default '00000000',
  app_expiry_day         varchar2(5)default '000',
  sale_act_flag   char(1),
  is_test_flag           char(1),
  charge_limit           number(18),
  limit_mode            CHAR(3),
  limit_entry_station   CHAR(4),
  limit_exit_station    CHAR(4),
  card_mac              varchar2(544),
  app_code CHAR(2),
  return_code           char(2),
  err_code              char(2),
  insert_date    DATE  
);
-- Add comments to the table 
comment on table w_acc_ol.W_OL_AIR_SALE
  is '�շ�����';

comment on column w_acc_ol.W_OL_AIR_SALE.app_code
  is 'app����';
comment on column w_acc_ol.W_OL_AIR_SALE.card_phy_id
  is 'Ʊ��������';
comment on column w_acc_ol.W_OL_AIR_SALE.expiry_date
  is 'Ʊ����Ч��';
comment on column w_acc_ol.W_OL_AIR_SALE.face_value
  is '��ֵ';
comment on column w_acc_ol.W_OL_AIR_SALE.deposit_fee
  is 'Ѻ��';
comment on column w_acc_ol.W_OL_AIR_SALE.app_expiry_start
  is '�˴�ƱӦ����Ч�ڿ�ʼʱ��';
comment on column w_acc_ol.W_OL_AIR_SALE.app_expiry_day
  is '�˴�Ʊʹ����Ч��';
comment on column w_acc_ol.W_OL_AIR_SALE.sale_act_flag
  is '���ۼ����־ 0�����1����';
comment on column w_acc_ol.W_OL_AIR_SALE.is_test_flag
  is '���Ա�� 0������1����';
comment on column w_acc_ol.W_OL_AIR_SALE.charge_limit
  is '��ֵ����';
comment on column w_acc_ol.W_OL_AIR_SALE.limit_mode
  is '����ģʽ';
comment on column w_acc_ol.W_OL_AIR_SALE.limit_entry_station
  is '���ƽ�բ��վ';
comment on column w_acc_ol.W_OL_AIR_SALE.limit_exit_station
  is '���Ƴ�բ��վ';
comment on column w_acc_ol.W_OL_AIR_SALE.card_mac
  is '����Կ';
comment on column w_acc_ol.W_OL_AIR_SALE.return_code
  is '��Ӧ��';
comment on column w_acc_ol.W_OL_AIR_SALE.err_code
  is '������';
comment on column w_acc_ol.W_OL_AIR_SALE.insert_date
  is '����ʱ��';

comment on column w_acc_ol.W_OL_AIR_SALE.card_ver
  is '���汾��';
comment on column w_acc_ol.W_OL_AIR_SALE.card_day
  is '����������';
comment on column w_acc_ol.W_OL_AIR_SALE.card_app_day
  is '��Ӧ����������';
comment on column w_acc_ol.W_OL_AIR_SALE.card_app_ver
  is '��Ӧ�ð汾';
comment on column w_acc_ol.W_OL_AIR_SALE.card_logical_id
  is 'Ʊ���߼�����';

comment on column w_acc_ol.W_OL_AIR_SALE.product_code
  is '����������';
comment on column w_acc_ol.W_OL_AIR_SALE.city_code
  is '���д���';
comment on column w_acc_ol.W_OL_AIR_SALE.business_code
  is '��ҵ����';
comment on column w_acc_ol.W_OL_AIR_SALE.deal_day
  is '����ʱ��';
comment on column w_acc_ol.W_OL_AIR_SALE.deal_dev_code
  is '�����豸����';
  
comment on column w_acc_ol.W_OL_AIR_SALE.message_id
  is '��Ϣ����';
comment on column w_acc_ol.W_OL_AIR_SALE.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column w_acc_ol.W_OL_AIR_SALE.sys_ref_no
  is 'ϵͳ���պ�';
comment on column w_acc_ol.W_OL_AIR_SALE.termina_no
  is '�ն˱��';
comment on column w_acc_ol.W_OL_AIR_SALE.sam_logical_id
  is 'sam���߼���';
comment on column w_acc_ol.W_OL_AIR_SALE.termina_seq
  is '�ն˴�����ˮ';
comment on column w_acc_ol.W_OL_AIR_SALE.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column w_acc_ol.W_OL_AIR_SALE.card_type
  is 'Ʊ������';
comment on column w_acc_ol.W_OL_AIR_SALE.phone_no
  is '�ֻ���';
comment on column w_acc_ol.W_OL_AIR_SALE.imsi
  is '�ֻ��û���ʶ';
comment on column w_acc_ol.W_OL_AIR_SALE.imei
  is '�ֻ��豸��ʶ';

-- Create/Recreate primary, unique and foreign key constraints 
alter table w_acc_ol.W_OL_AIR_SALE
  add constraint PK_W_OL_AIR_SALE primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_SALE to W_ACC_OL_APP;
grant select on w_acc_ol.W_OL_AIR_SALE to W_ACC_OL_DEV;
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_SALE to W_ACC_ST_APP;


-- Create table
create table w_acc_ol.W_OL_AIR_SALE_CFM
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  branches_code  VARCHAR2(16),
  iss_main_code   CHAR(4),
  iss_sub_code    CHAR(4),
  phone_no       varchar2(11),
  imsi           varchar2(15),
  imei           varchar2(15),
  card_type      VARCHAR2(4),
  card_logical_id        varchar2(20),
  card_phy_id            varchar2(20),
  is_test_flag           char(1),
  result_code            char(1),
  deal_time              char(14),
  sys_ref_no NUMBER(18),
  app_code CHAR(2),
  return_code           char(2),
  err_code              char(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table w_acc_ol.W_OL_AIR_SALE_CFM
  is '�շ�ȷ��';
comment on column  w_acc_ol.W_OL_AIR_SALE_CFM.app_code
  is 'app����';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.message_id
  is '��Ϣ����';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.termina_no
  is '�ն˱��';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.sam_logical_id
  is 'sam���߼���';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.termina_seq
  is '�ն˴�����ˮ';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.iss_main_code
  is '������������';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.iss_sub_code
  is '�������ӱ���';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.phone_no
  is '�ֻ���';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.imsi
  is '�ֻ��û���ʶ';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.imei
  is '�ֻ��豸��ʶ';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.card_type
  is 'Ʊ������';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.card_logical_id
  is 'Ʊ���߼�����';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.card_phy_id
  is 'Ʊ��������';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.is_test_flag
  is '���Ա�� 0������1����';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.result_code
  is '������� 0�ɹ� 1ʧ�� 2δ֪״̬';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.deal_time
  is '����ʱ��';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.sys_ref_no
  is 'ϵͳ���պ�';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.return_code
  is '��Ӧ��';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.err_code
  is '������';
comment on column w_acc_ol.W_OL_AIR_SALE_CFM.insert_date
  is '����ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table w_acc_ol.W_OL_AIR_SALE_CFM
  add constraint PK_W_OL_AIR_SALE_CFM primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_SALE_CFM to W_ACC_OL_APP;
grant select on w_acc_ol.W_OL_AIR_SALE_CFM to W_ACC_OL_DEV;
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_SALE_CFM to W_ACC_ST_APP;



-- Create table
create table w_acc_ol.W_OL_AIR_CHARGE
( 
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  branches_code  VARCHAR2(16),
  iss_main_code   CHAR(4),
  iss_sub_code    CHAR(4),
  card_type      VARCHAR2(4),
  imsi           varchar2(15),
  imei           varchar2(15),
  card_logical_id        varchar2(20),
  card_phy_id            varchar2(20),
  is_test_flag           char(1),
  onl_tran_times   NUMBER(18),
  offl_tran_times  NUMBER(18),
  buss_type        VARCHAR2(2),
  value_type       VARCHAR2(1),
  charge_fee       NUMBER(18),
  balance          NUMBER(18),
  mac1             VARCHAR2(8),
  tk_chge_seq      VARCHAR2(8),
  last_tran_termno VARCHAR2(16),
  last_tran_time   VARCHAR2(14),
  operator_id      VARCHAR2(6),
  phone_no       varchar2(11),
  paid_channel_type     VARCHAR2(2),
  paid_channel_code     VARCHAR2(4),
  mac2             VARCHAR2(8),
  deal_time              char(14),
  sys_ref_no NUMBER(18),
  return_code           char(2),
  err_code              char(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table w_acc_ol.W_OL_AIR_CHARGE
  is '�ճ�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.onl_tran_times
  is 'Ʊ���������׺�';
comment on column w_acc_ol.W_OL_AIR_CHARGE.offl_tran_times
  is 'Ʊ���ѻ����׺�';
comment on column w_acc_ol.W_OL_AIR_CHARGE.buss_type
  is 'ҵ������ 14';
comment on column w_acc_ol.W_OL_AIR_CHARGE.value_type
  is 'ֵ���� 0��ֵ1���2����3����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.charge_fee
  is '��ֵ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE.balance
  is 'Ʊ�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.mac1
  is 'mac1';
comment on column w_acc_ol.W_OL_AIR_CHARGE.tk_chge_seq
  is '��Ƭ��ֵ�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.last_tran_termno
  is '�ϴν����ն˱��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.last_tran_time
  is '�ϴν���ʱ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.operator_id
  is '����Ա';
comment on column w_acc_ol.W_OL_AIR_CHARGE.paid_channel_type
  is '��ֵ��������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.paid_channel_code
  is '��ֵ��������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.message_id
  is '��Ϣ����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.termina_no
  is '�ն˱��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.sam_logical_id
  is 'sam���߼���';
comment on column w_acc_ol.W_OL_AIR_CHARGE.termina_seq
  is '�ն˴�����ˮ';
comment on column w_acc_ol.W_OL_AIR_CHARGE.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column w_acc_ol.W_OL_AIR_CHARGE.iss_main_code
  is '������������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.iss_sub_code
  is '�������ӱ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE.imsi
  is '�ֻ��û���ʶ';
comment on column w_acc_ol.W_OL_AIR_CHARGE.imei
  is '�ֻ��豸��ʶ';
comment on column w_acc_ol.W_OL_AIR_CHARGE.card_type
  is 'Ʊ������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.card_logical_id
  is 'Ʊ���߼�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.card_phy_id
  is 'Ʊ��������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.is_test_flag
  is '���Ա�� 0������1����';
comment on column w_acc_ol.W_OL_AIR_CHARGE.phone_no
  is '�ֻ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE.mac2
  is '���ɹ�ʱȫ0';
comment on column w_acc_ol.W_OL_AIR_CHARGE.deal_time
  is '��ֵʱ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.sys_ref_no
  is 'ϵͳ���պ�';
comment on column w_acc_ol.W_OL_AIR_CHARGE.return_code
  is '��Ӧ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE.err_code
  is '������';
comment on column w_acc_ol.W_OL_AIR_CHARGE.insert_date
  is '����ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table w_acc_ol.W_OL_AIR_CHARGE
  add constraint PK_W_OL_AIR_CHARGE primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_CHARGE to W_ACC_OL_APP;
grant select on w_acc_ol.W_OL_AIR_CHARGE to W_ACC_OL_DEV;
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_CHARGE to W_ACC_ST_APP;




-- Create table
create table w_acc_ol.W_OL_AIR_CHARGE_CFM
( 
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  branches_code  VARCHAR2(16),
  iss_main_code   CHAR(4),
  iss_sub_code    CHAR(4),
  card_type      VARCHAR2(4),
  imsi           varchar2(15),
  imei           varchar2(15),
  card_logical_id        varchar2(20),
  card_phy_id            varchar2(20),
  is_test_flag           char(1),
  onl_tran_times   NUMBER(18),
  offl_tran_times  NUMBER(18),
  buss_type        VARCHAR2(2),
  value_type       VARCHAR2(1),
  charge_fee       NUMBER(18),
  balance          NUMBER(18),
  tac             VARCHAR2(8),
  phone_no       varchar2(11),
  operator_id      VARCHAR2(6),
  result_code      char(1),
  deal_time              char(14),
  sys_ref_no NUMBER(18),
  return_code           char(2),
  err_code              char(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table w_acc_ol.W_OL_AIR_CHARGE_CFM
  is '�ճ�ȷ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.onl_tran_times
  is 'Ʊ���������׺�';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.offl_tran_times
  is 'Ʊ���ѻ����׺�';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.buss_type
  is 'ҵ������ 14';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.value_type
  is 'ֵ���� 0��ֵ1���2����3����';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.charge_fee
  is '��ֵ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.balance
  is 'Ʊ�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.tac
  is 'tac';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.operator_id
  is '����Ա';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.message_id
  is '��Ϣ����';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.termina_no
  is '�ն˱��';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.sam_logical_id
  is 'sam���߼���';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.termina_seq
  is '�ն˴�����ˮ';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.iss_main_code
  is '������������';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.iss_sub_code
  is '�������ӱ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.imsi
  is '�ֻ��û���ʶ';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.imei
  is '�ֻ��豸��ʶ';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.card_type
  is 'Ʊ������';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.card_logical_id
  is 'Ʊ���߼�����';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.card_phy_id
  is 'Ʊ��������';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.is_test_flag
  is '���Ա�� 0������1����';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.phone_no
  is '�ֻ���';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.deal_time
  is '��ֵʱ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.result_code
  is 'д����� 0�ɹ� 1ʧ�� 2δ֪״̬';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.sys_ref_no
  is 'ϵͳ���պ�';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.return_code
  is '��Ӧ��';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.err_code
  is '������';
comment on column w_acc_ol.W_OL_AIR_CHARGE_CFM.insert_date
  is '����ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table w_acc_ol.W_OL_AIR_CHARGE_CFM
  add constraint PK_W_OL_AIR_CHARGE_CFM primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_CHARGE_CFM to W_ACC_OL_APP;
grant select on w_acc_ol.W_OL_AIR_CHARGE_CFM to W_ACC_OL_DEV;
grant select, insert, update, delete on w_acc_ol.W_OL_AIR_CHARGE_CFM to W_ACC_ST_APP;



-- Add comments to the table 
comment on table W_OL_CHG_PLUS
  is '��ֵ';
comment on table W_OL_QRCODE_AFC
  is '��ά��';
comment on table W_OL_CHG_ACTIVATION
  is '����';
comment on table W_OL_CHG_SUB
  is '��ֵ����';
comment on table W_OL_QRCODE_ORDER
  is '��ά�붩����';
