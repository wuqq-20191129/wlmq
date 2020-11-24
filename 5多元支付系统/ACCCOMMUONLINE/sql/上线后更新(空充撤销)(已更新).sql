create sequence W_SEQ_W_OL_AIR_CHARGE_CANCEL
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;
create sequence W_SEQ_W_OL_AIR_CHARGE_CL_CFM
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;

grant select on W_SEQ_W_OL_AIR_CHARGE_CL_CFM to W_ACC_OL_APP;
grant select on W_SEQ_W_OL_AIR_CHARGE_CL_CFM to W_ACC_ST_APP;
grant select on W_SEQ_W_OL_AIR_CHARGE_CANCEL to W_ACC_OL_APP;
grant select on W_SEQ_W_OL_AIR_CHARGE_CANCEL to W_ACC_ST_APP;


-- Create table
create table W_OL_AIR_CHARGE_CANCEL
(
  water_no          NUMBER(18) not null,
  message_id        VARCHAR2(2),
  msg_gen_time      VARCHAR2(14),
  termina_no        VARCHAR2(9),
  sam_logical_id    VARCHAR2(16),
  termina_seq       NUMBER(18),
  branches_code     VARCHAR2(16),
  iss_main_code     CHAR(4),
  iss_sub_code      CHAR(4),
  card_type         VARCHAR2(4),
  imsi              VARCHAR2(15),
  imei              VARCHAR2(15),
  card_logical_id   VARCHAR2(20),
  card_phy_id       VARCHAR2(20),
  is_test_flag      CHAR(1),
  onl_tran_times    NUMBER(18),
  offl_tran_times   NUMBER(18),
  buss_type         VARCHAR2(2),
  value_type        VARCHAR2(1),
  charge_fee        NUMBER(18),
  balance           NUMBER(18),
  mac1              VARCHAR2(8),
  tk_chge_seq       VARCHAR2(8),
  last_tran_termno  VARCHAR2(16),
  last_tran_time    VARCHAR2(14),
  operator_id       VARCHAR2(6),
  phone_no          VARCHAR2(11),
  paid_channel_type VARCHAR2(2),
  paid_channel_code VARCHAR2(4),
  mac2              VARCHAR2(8),
  deal_time         CHAR(14),
  sys_ref_no        NUMBER(18),
  return_code       CHAR(2),
  err_code          CHAR(2),
  insert_date       DATE,
  sys_ref_no_chr    NUMBER(18)
);
comment on table W_OL_AIR_CHARGE_CANCEL
  is '�ճ䳷������';
comment on column W_OL_AIR_CHARGE_CANCEL.message_id
  is '��Ϣ����';
comment on column W_OL_AIR_CHARGE_CANCEL.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL.termina_no
  is '�ն˱��';
comment on column W_OL_AIR_CHARGE_CANCEL.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_AIR_CHARGE_CANCEL.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_AIR_CHARGE_CANCEL.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column W_OL_AIR_CHARGE_CANCEL.iss_main_code
  is '������������';
comment on column W_OL_AIR_CHARGE_CANCEL.iss_sub_code
  is '�������ӱ���';
comment on column W_OL_AIR_CHARGE_CANCEL.card_type
  is 'Ʊ������';
comment on column W_OL_AIR_CHARGE_CANCEL.imsi
  is '�ֻ��û���ʶ';
comment on column W_OL_AIR_CHARGE_CANCEL.imei
  is '�ֻ��豸��ʶ';
comment on column W_OL_AIR_CHARGE_CANCEL.card_logical_id
  is 'Ʊ���߼�����';
comment on column W_OL_AIR_CHARGE_CANCEL.card_phy_id
  is 'Ʊ��������';
comment on column W_OL_AIR_CHARGE_CANCEL.is_test_flag
  is '���Ա�� 0������1����';
comment on column W_OL_AIR_CHARGE_CANCEL.onl_tran_times
  is 'Ʊ���������׺�';
comment on column W_OL_AIR_CHARGE_CANCEL.offl_tran_times
  is 'Ʊ���ѻ����׺�';
comment on column W_OL_AIR_CHARGE_CANCEL.buss_type
  is 'ҵ������ 14';
comment on column W_OL_AIR_CHARGE_CANCEL.value_type
  is 'ֵ���� 0��ֵ1���2����3����';
comment on column W_OL_AIR_CHARGE_CANCEL.charge_fee
  is '�������';
comment on column W_OL_AIR_CHARGE_CANCEL.balance
  is 'Ʊ�����';
comment on column W_OL_AIR_CHARGE_CANCEL.mac1
  is 'mac1';
comment on column W_OL_AIR_CHARGE_CANCEL.tk_chge_seq
  is '��Ƭ��ֵ�����';
comment on column W_OL_AIR_CHARGE_CANCEL.last_tran_termno
  is '�ϴν����ն˱��';
comment on column W_OL_AIR_CHARGE_CANCEL.last_tran_time
  is '�ϴν���ʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL.operator_id
  is '����Ա';
comment on column W_OL_AIR_CHARGE_CANCEL.phone_no
  is '�ֻ���';
comment on column W_OL_AIR_CHARGE_CANCEL.paid_channel_type
  is '������������';
comment on column W_OL_AIR_CHARGE_CANCEL.paid_channel_code
  is '������������';
comment on column W_OL_AIR_CHARGE_CANCEL.mac2
  is '���ɹ�ʱȫ0';
comment on column W_OL_AIR_CHARGE_CANCEL.deal_time
  is '��ֵʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL.sys_ref_no
  is 'ϵͳ���պ�';
comment on column W_OL_AIR_CHARGE_CANCEL.return_code
  is '��Ӧ��';
comment on column W_OL_AIR_CHARGE_CANCEL.err_code
  is '������';
comment on column W_OL_AIR_CHARGE_CANCEL.insert_date
  is '����ʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL.sys_ref_no_chr
  is '�ճ�ϵͳ���պ�';
alter table W_OL_AIR_CHARGE_CANCEL
  add constraint PK_W_OL_AIR_CHARGE_CANCEL primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_AIR_CHARGE_CANCEL to W_ACC_OL_APP;
grant select on W_OL_AIR_CHARGE_CANCEL to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_AIR_CHARGE_CANCEL to W_ACC_ST_APP;


-- Create table
create table W_OL_AIR_CHARGE_CANCEL_CFM
(
  water_no        NUMBER(18) not null,
  message_id      VARCHAR2(2),
  msg_gen_time    VARCHAR2(14),
  termina_no      VARCHAR2(9),
  sam_logical_id  VARCHAR2(16),
  termina_seq     NUMBER(18),
  branches_code   VARCHAR2(16),
  iss_main_code   CHAR(4),
  iss_sub_code    CHAR(4),
  card_type       VARCHAR2(4),
  imsi            VARCHAR2(15),
  imei            VARCHAR2(15),
  card_logical_id VARCHAR2(20),
  card_phy_id     VARCHAR2(20),
  is_test_flag    CHAR(1),
  onl_tran_times  NUMBER(18),
  offl_tran_times NUMBER(18),
  buss_type       VARCHAR2(2),
  value_type      VARCHAR2(1),
  charge_fee      NUMBER(18),
  balance         NUMBER(18),
  tac             VARCHAR2(8),
  phone_no        VARCHAR2(11),
  operator_id     VARCHAR2(6),
  result_code     CHAR(1),
  deal_time       CHAR(14),
  sys_ref_no      NUMBER(18),
  return_code     CHAR(2),
  err_code        CHAR(2),
  insert_date     DATE,
  sys_ref_no_chr  NUMBER(18)
);
comment on table W_OL_AIR_CHARGE_CANCEL_CFM
  is '�ճ䳷������ȷ��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.message_id
  is '��Ϣ����';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.termina_no
  is '�ն˱��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.branches_code
  is '������� Ĭ��ֵ��ȫ0';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.iss_main_code
  is '������������';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.iss_sub_code
  is '�������ӱ���';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.card_type
  is 'Ʊ������';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.imsi
  is '�ֻ��û���ʶ';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.imei
  is '�ֻ��豸��ʶ';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.card_logical_id
  is 'Ʊ���߼�����';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.card_phy_id
  is 'Ʊ��������';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.is_test_flag
  is '���Ա�� 0������1����';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.onl_tran_times
  is 'Ʊ���������׺�';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.offl_tran_times
  is 'Ʊ���ѻ����׺�';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.buss_type
  is 'ҵ������ 14';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.value_type
  is 'ֵ���� 0��ֵ1���2����3����';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.charge_fee
  is '�������';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.balance
  is 'Ʊ�����';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.tac
  is 'tac';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.phone_no
  is '�ֻ���';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.operator_id
  is '����Ա';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.result_code
  is 'д����� 0�ɹ� 1ʧ�� 2δ֪״̬';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.deal_time
  is '��ֵʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.sys_ref_no
  is 'ϵͳ���պ�';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.return_code
  is '��Ӧ��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.err_code
  is '������';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.insert_date
  is '����ʱ��';
comment on column W_OL_AIR_CHARGE_CANCEL_CFM.sys_ref_no_chr
  is '�ճ�ϵͳ���պ�';
alter table W_OL_AIR_CHARGE_CANCEL_CFM
  add constraint PK_W_OL_AIR_CHARGE_CANCEL_CFM primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_AIR_CHARGE_CANCEL_CFM to W_ACC_OL_APP;
grant select on W_OL_AIR_CHARGE_CANCEL_CFM to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_AIR_CHARGE_CANCEL_CFM to W_ACC_ST_APP;

