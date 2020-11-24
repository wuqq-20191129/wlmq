-- Create table
create table w_acc_tk.W_IC_HCE_CARD_INFO
(
  water_no            NUMBER(18) not null,
  card_type           VARCHAR2(4),
  phone_no            VARCHAR2(11),
  imsi                VARCHAR2(15),
  imei                VARCHAR2(15),
  card_logical_id     VARCHAR2(20),
  card_phy_id         VARCHAR2(20),
  logical_seq         NUMBER(18),
  deal_day            CHAR(8),
  face_value          NUMBER(18) default 0,
  deposit_fee         NUMBER(18) default 0, 
  act_flag            CHAR(1),
  reuse_flag          CHAR(1),
  is_test_flag        CHAR(1),
  insert_date         DATE
);
-- Add comments to the table 
comment on table w_acc_tk.W_IC_HCE_CARD_INFO
  is 'HCEƱ���߼�����ʹ�ñ�';
-- Add comments to the columns 
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.card_type
  is 'Ʊ������';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.phone_no
  is '�ֻ���';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.imsi
  is '�ֻ��û���ʶ';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.imei
  is '�ֻ��豸��ʶ';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.deal_day
  is '����ʱ��';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.card_logical_id
  is 'Ʊ���߼�����';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.card_phy_id
  is 'Ʊ��������';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.logical_seq
  is '�߼��������';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.face_value
  is '��ֵ';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.deposit_fee
  is 'Ѻ��';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.act_flag
  is '״̬ 1���룬2ȷ��';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.reuse_flag
  is '�����ñ�־ 1������';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.is_test_flag
  is '���Ա�� 0������1����';
comment on column w_acc_tk.W_IC_HCE_CARD_INFO.insert_date
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table w_acc_tk.W_IC_HCE_CARD_INFO
  add constraint PK_W_IC_HCE_CARD_INFO primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_acc_tk.W_IC_HCE_CARD_INFO to W_ACC_OL_APP;
grant select on w_acc_tk.W_IC_HCE_CARD_INFO to W_ACC_OL_DEV;
grant select, insert, update, delete on w_acc_tk.W_IC_HCE_CARD_INFO to W_ACC_ST_APP;
grant select, insert, update, delete on w_acc_tk.W_IC_HCE_CARD_INFO to W_ACC_TK_APP;


-- Create sequence 
create sequence w_acc_tk.W_SEQ_W_IC_HCE_CARD_INFO
minvalue 1
maxvalue 2147483648
start with 1
increment by 1
cache 10;

-- Grant/Revoke object privileges 
grant select on w_acc_tk.W_SEQ_W_IC_HCE_CARD_INFO to W_ACC_OL_APP;
grant select on w_acc_tk.W_SEQ_W_IC_HCE_CARD_INFO to W_ACC_ST_APP;
grant select on w_acc_tk.W_SEQ_W_IC_HCE_CARD_INFO to W_ACC_TK_APP;



--w_acc_tk:
-- Add/modify columns 
alter table w_acc_tk.W_IC_BC_LOGIC_NO add seq_no number default 0;
-- Add comments to the columns 
comment on column w_acc_tk.W_IC_BC_LOGIC_NO.seq_no
  is '���к�';
-- Grant/Revoke object privileges 
grant select,update on w_acc_tk.W_IC_BC_LOGIC_NO to W_ACC_OL_APP;
grant select on w_acc_tk.W_IC_BC_LOGIC_NO to W_ACC_ST_APP;



comment on column w_acc_tk.W_IC_BC_LOGIC_NO.record_flag
  is '����״̬
0:������Ч
1:���ݳ���
2:����ɾ��
3:����δ���';

