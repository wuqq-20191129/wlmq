create table W_OL_TRD_MAX_HDLTIME
(
  msg_id       VARCHAR2(10) not null,
  max_hdl_time INTEGER not null,
  remark       VARCHAR2(50) not null
);
alter table W_OL_TRD_MAX_HDLTIME
  add constraint PK_W_OL_TRD_MAX_HDLTIME primary key (MSG_ID);
create table W_OL_TRD_MONITOR
(
  thread_id      VARCHAR2(50) not null,
  thread_name    VARCHAR2(50),
  thread_status  VARCHAR2(2),
  msg_id         VARCHAR2(10),
  msg_name       VARCHAR2(50),
  hdl_time_start VARCHAR2(20),
  hdl_end_time   VARCHAR2(20),
  remark         VARCHAR2(50)
);
alter table W_OL_TRD_MONITOR
  add constraint PK_W_OL_TRD_MONITOR primary key (THREAD_ID);
create table W_OL_TRD_MONITOR_HIS
(
  water_no       NUMBER(18) not null,
  reason_id      VARCHAR2(1) not null,
  dump_date      DATE not null,
  thread_id      VARCHAR2(50) not null,
  thread_name    VARCHAR2(50),
  thread_status  VARCHAR2(2),
  msg_id         VARCHAR2(10),
  msg_name       VARCHAR2(50),
  hdl_time_start VARCHAR2(20),
  hdl_end_time   VARCHAR2(20),
  remark         VARCHAR2(50)
);
alter table W_OL_TRD_MONITOR_HIS
  add constraint PK_W_OL_TRD_MONITOR_HIS primary key (WATER_NO);
create table W_OL_TRD_MSG_DEL
(
  water_no   NUMBER(18) not null,
  del_date   DATE not null,
  thread_id  VARCHAR2(50) not null,
  queue_type VARCHAR2(1) not null,
  msg_id     VARCHAR2(2) not null,
  message    BLOB not null,
  remark     VARCHAR2(50)
);
alter table W_OL_TRD_MSG_DEL
  add constraint PK_W_OL_TRD_MSG_DEL primary key (WATER_NO);
create table W_OL_TRD_MSG_HANDUP_NUM
(
  water_no     NUMBER(18) not null,
  msg_id       VARCHAR2(2) not null,
  hand_up_num  INTEGER not null,
  hand_up_date DATE not null,
  remark       VARCHAR2(50) not null
);
alter table W_OL_TRD_MSG_HANDUP_NUM
  add constraint PK_W_OL_TRD_MSG_HANDUP_NUM primary key (WATER_NO);
create table W_OL_TRD_RESET
(
  reason_id                VARCHAR2(1),
  dump_date                VARCHAR2(20),
  thread_id                VARCHAR2(50),
  thread_name              VARCHAR2(50),
  thread_status            VARCHAR2(2),
  thread_queue_pri_num     INTEGER,
  thread_queue_ord_num     INTEGER,
  thread_id_new            VARCHAR2(50),
  thread_name_new          VARCHAR2(50),
  thread_status_new        VARCHAR2(2),
  thread_queue_pri_num_new INTEGER,
  thread_queue_ord_num_new INTEGER,
  msg_id                   VARCHAR2(10),
  msg_name                 VARCHAR2(50),
  hdl_time_start           VARCHAR2(20),
  hdl_time_end             VARCHAR2(20),
  remark                   VARCHAR2(50),
  message                  VARCHAR2(50)
);
create table W_OL_CHG_ACTIVATION
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  random_num     VARCHAR2(18),
  mac            VARCHAR2(16),
  return_code    VARCHAR2(2),
  err_code       VARCHAR2(2),
  insert_date    DATE,
  update_date    DATE,
  sys_ref_no     NUMBER(18),
  msg_gen_time1  VARCHAR2(14),
  msg_gen_time2  VARCHAR2(14)
);
comment on column W_OL_CHG_ACTIVATION.insert_date
  is '��������ʱ��';
comment on column W_OL_CHG_ACTIVATION.update_date
  is '������Ӧʱ��';
comment on column W_OL_CHG_ACTIVATION.msg_gen_time1
  is '������Ϣ�е���Ϣ����ʱ��';
comment on column W_OL_CHG_ACTIVATION.msg_gen_time2
  is '��Ӧ��Ϣ����Ϣ����ʱ��';
alter table W_OL_CHG_ACTIVATION
  add constraint PK_W_OL_CHG_ACTIVATION primary key (WATER_NO);
create table W_OL_CHG_PLUS
(
  water_no         NUMBER(18) not null,
  message_id       VARCHAR2(2),
  termina_no       VARCHAR2(9),
  sam_logical_id   VARCHAR2(16),
  transation_seq   NUMBER(18),
  branches_code    VARCHAR2(16),
  pub_main_code    VARCHAR2(4),
  pub_sub_code     VARCHAR2(4),
  card_type        VARCHAR2(4),
  tk_logic_no      VARCHAR2(20),
  tk_phy_no        VARCHAR2(20),
  is_test_tk       VARCHAR2(1),
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
  mac2             VARCHAR2(8),
  sys_date         VARCHAR2(14),
  return_code      VARCHAR2(2),
  err_code         VARCHAR2(2),
  write_rslt       VARCHAR2(1),
  status           VARCHAR2(1),
  insert_date      DATE,
  update_date      DATE,
  sys_ref_no       NUMBER(18),
  msg_gen_time1    VARCHAR2(14),
  msg_gen_time2    VARCHAR2(14)
);
comment on column W_OL_CHG_PLUS.card_type
  is '�����ʹ��룫�����ʹ���';
comment on column W_OL_CHG_PLUS.is_test_tk
  is '0����ʽƱ��  1������Ʊ��';
comment on column W_OL_CHG_PLUS.buss_type
  is '�̶���д��14��';
comment on column W_OL_CHG_PLUS.value_type
  is '0����ֵ���ͣ�1����2��������3��������';
comment on column W_OL_CHG_PLUS.write_rslt
  is '0��д���ɹ���1��д��ʧ�ܣ�2��δ֪״̬��';
comment on column W_OL_CHG_PLUS.status
  is '0:���룻1����Ӧ��';
comment on column W_OL_CHG_PLUS.insert_date
  is '��ֵ���󡢳�ֵȷ�ϵ��������ʱ��';
comment on column W_OL_CHG_PLUS.update_date
  is '��ֵ������Ӧ����ֵȷ����Ӧ�����ݸ���ʱ��';
comment on column W_OL_CHG_PLUS.msg_gen_time1
  is '������Ϣ�е���Ϣ����ʱ�䣬YYYYMMDD HH24MISS';
comment on column W_OL_CHG_PLUS.msg_gen_time2
  is '��Ӧ��Ϣ����Ϣ����ʱ�䣬YYYYMMDD HH24MISS';
alter table W_OL_CHG_PLUS
  add constraint PK_W_OL_CHG_PLUS primary key (WATER_NO);
create table W_OL_CHG_SUB
(
  water_no         NUMBER(18) not null,
  message_id       VARCHAR2(2),
  termina_no       VARCHAR2(9),
  sam_logical_id   VARCHAR2(16),
  transation_seq   NUMBER(18),
  branches_code    VARCHAR2(16),
  pub_main_code    VARCHAR2(4),
  pub_sub_code     VARCHAR2(4),
  card_type        VARCHAR2(4),
  tk_logic_no      VARCHAR2(20),
  tk_phy_no        VARCHAR2(20),
  is_test_tk       VARCHAR2(1),
  onl_tran_times   NUMBER(18),
  offl_tran_times  NUMBER(18),
  buss_type        VARCHAR2(2),
  value_type       VARCHAR2(1),
  charge_fee       NUMBER(18),
  balance          NUMBER(18),
  last_tran_termno VARCHAR2(16),
  last_trantime    VARCHAR2(14),
  operator_id      VARCHAR2(6),
  tac              VARCHAR2(8),
  sys_date         VARCHAR2(14),
  return_code      VARCHAR2(2),
  err_code         VARCHAR2(2),
  write_rslt       VARCHAR2(1),
  status           VARCHAR2(1),
  insert_date      DATE,
  update_date      DATE,
  sys_ref_no       NUMBER(18),
  msg_gen_time1    VARCHAR2(14),
  msg_gen_time2    VARCHAR2(14)
);
comment on column W_OL_CHG_SUB.status
  is '0:���룻1����Ӧ��';
comment on column W_OL_CHG_SUB.insert_date
  is '��ֵ�������󡢳�ֵ����ȷ�ϵ��������ʱ��';
comment on column W_OL_CHG_SUB.update_date
  is '��ֵ������Ӧ����ֵ����ȷ����Ӧ�����ݸ���ʱ��';
comment on column W_OL_CHG_SUB.msg_gen_time1
  is '������Ϣ�е���Ϣ����ʱ�䣬YYYYMMDD HH24MISS';
comment on column W_OL_CHG_SUB.msg_gen_time2
  is '��Ӧ��Ϣ����Ϣ����ʱ�䣬YYYYMMDD HH24MISS';
alter table W_OL_CHG_SUB
  add constraint PK_W_OL_CHG_SUB primary key (WATER_NO);
create table W_OL_LOG_COMMU
(
  id           NUMBER(18) not null,
  message_id   CHAR(4),
  message_name VARCHAR2(30),
  message_from VARCHAR2(20),
  start_time   VARCHAR2(20),
  end_time     VARCHAR2(20),
  use_time     VARCHAR2(10),
  result       VARCHAR2(1),
  hdl_thread   VARCHAR2(20),
  sys_level    VARCHAR2(3),
  remark       VARCHAR2(100),
  insert_date  DATE
);
comment on table W_OL_LOG_COMMU
  is 'ͨ����־';
comment on column W_OL_LOG_COMMU.result
  is '0���ɹ���1��ʧ�ܣ�2������';
alter table W_OL_LOG_COMMU
  add constraint PK_W_OL_LOG_COMMU primary key (ID);
create table W_OL_LOG_CONNECT
(
  id               NUMBER(18) not null,
  connect_datetime DATE,
  connect_ip       VARCHAR2(15),
  connect_result   CHAR(1),
  remark           VARCHAR2(100)
);
comment on table W_OL_LOG_CONNECT
  is '������־��';
comment on column W_OL_LOG_CONNECT.connect_result
  is '0�����ӳɹ���1������ʧ�ܣ�2:���ӹر�';
alter table W_OL_LOG_CONNECT
  add constraint PK_W_OL_LOG_CONNECT primary key (ID);
create table W_OL_LOG_RECV_SEND
(
  id           NUMBER(18) not null,
  datetime_rec DATE,
  ip           VARCHAR2(15),
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(2)
);
comment on table W_OL_LOG_RECV_SEND
  is '��¼���յ��ն˵����ݣ��Լ����͸��ն˵�����';
comment on column W_OL_LOG_RECV_SEND.ip
  is '��Ϣ���ͷ���IP��ַ';
comment on column W_OL_LOG_RECV_SEND.type
  is '0:������Ϣ��1����Ӧ��Ϣ';
comment on column W_OL_LOG_RECV_SEND.result
  is '0���ɹ�
1��ʧ��';
alter table W_OL_LOG_RECV_SEND
  add constraint PK_W_OL_LOG_RECV_SEND primary key (ID);
create table W_OL_LOG_SYS_ERROR
(
  id          NUMBER(18) not null,
  ip          VARCHAR2(15),
  excp_id     VARCHAR2(4),
  excp_type   VARCHAR2(4),
  class_name  VARCHAR2(100),
  excp_desc   VARCHAR2(300),
  insert_date DATE
);
comment on table W_OL_LOG_SYS_ERROR
  is '��¼ϵͳ�����е��쳣��Ϣ';
comment on column W_OL_LOG_SYS_ERROR.ip
  is '��Ϣ���ͷ���IP��ַ';
comment on column W_OL_LOG_SYS_ERROR.excp_id
  is '�쳣��Ϣ��Ӧ��id��ʶ';
comment on column W_OL_LOG_SYS_ERROR.excp_type
  is '1��������Ϣ������
2�������쳣
3����Ϣ�����쳣
4�����ݿ��쳣
5�����ܻ��쳣';
comment on column W_OL_LOG_SYS_ERROR.excp_desc
  is '��ϸ���쳣��Ϣ';
alter table W_OL_LOG_SYS_ERROR
  add constraint PK_W_OL_LOG_SYS_ERROR primary key (ID);
create table W_OL_CHG_LOG_TEST
(
  insert_time DATE,
  msg         VARCHAR2(100)
);
-- Add comments to the table 
comment on table W_OL_CHG_LOG_TEST
  is '��ֵ���������¼��';
create table W_OL_PUB_FLAG
(
  type        VARCHAR2(50),
  code        VARCHAR2(50),
  code_text   VARCHAR2(50),
  description VARCHAR2(100)
);
comment on table W_OL_PUB_FLAG
  is '���߳�ֵ���ñ�';
comment on column W_OL_PUB_FLAG.type
  is '����';
comment on column W_OL_PUB_FLAG.code
  is '����';
comment on column W_OL_PUB_FLAG.code_text
  is 'ֵ';
comment on column W_OL_PUB_FLAG.description
  is '��������';
create table W_OL_SYS_VERSION
(
  version_no  VARCHAR2(10) not null,
  operator_id VARCHAR2(10),
  valid_date  CHAR(10),
  del_desc    VARCHAR2(255),
  update_desc VARCHAR2(255),
  add_desc    VARCHAR2(255),
  note        VARCHAR2(255)
);
alter table W_OL_SYS_VERSION
  add constraint PK_W_OL_SYS_VERSION primary key (VERSION_NO);
comment on table W_OL_SYS_VERSION
  is 'ϵͳ�汾��¼��'; 
  
-- Create table
create table W_OL_QRCODE_AFC
(
  water_no         NUMBER(18) not null,
  message_id       VARCHAR2(2),--��Ϣ����
  msg_gen_time     VARCHAR2(14),--��Ϣ����ʱ��
  termina_no       VARCHAR2(9),--�ն˱��
  sam_logical_id   VARCHAR2(16),--sam���߼���
  termina_seq      NUMBER(18),--�ն˴�����ˮ
  acc_seq          NUMBER(18),--���Ĵ�����ˮ
  qrcode           varchar2(64),--��ά��
  return_code      VARCHAR2(2),--��Ӧ��
  order_no         varchar2(14),--������
  phone_no         varchar2(11),--�ֻ���
  sale_fee         NUMBER(18),--���۵���Ʊ����
  sale_times       NUMBER(18),--���۵���Ʊ����
  deal_fee         NUMBER(18),--���۵���Ʊ�ܼ�
  result_code      varchar2(2),--����ִ�н��
  insert_date      DATE--����ʱ��
);
-- Add comments to the columns 
comment on column W_OL_QRCODE_AFC.message_id
  is '��Ϣ����';
comment on column W_OL_QRCODE_AFC.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRCODE_AFC.termina_no
  is '�ն˱��';
comment on column W_OL_QRCODE_AFC.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_QRCODE_AFC.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRCODE_AFC.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRCODE_AFC.qrcode
  is '��ά��';
comment on column W_OL_QRCODE_AFC.return_code
  is '��Ӧ��:00:����δִ��01:�����������80:δ��Ʊ�򲻴���81:���������(��ȡƱ)82:��ά���ѹ���Ч��83:��Ʊ��ȡƱʼ��վ��һ�µ���Ӧ���00��01ʱ�������š��ֻ��š����۵���Ʊ�۵��ֶ�ֵ��Ĭ��(0)����';
comment on column W_OL_QRCODE_AFC.order_no
  is '������:8λ����+6λ��ˮ';
comment on column W_OL_QRCODE_AFC.phone_no
  is '�ֻ���';
  comment on column W_OL_QRCODE_AFC.sale_fee
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRCODE_AFC.sale_times
  is '���۵���Ʊ����';
comment on column W_OL_QRCODE_AFC.deal_fee
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRCODE_AFC.result_code
  is '����ִ�н��:00:���������ɹ�ִ��01:�������ֳɹ�ִ��10:��Ʊ11:д��ʧ��12:�豸����99:��������';
comment on column W_OL_QRCODE_AFC.insert_date
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
--alter table W_OL_QRCODE_AFC
--  add constraint PK_W_OL_CHG_QRCODE primary key (WATER_NO);

-- Create table
create table W_OL_QRCODE_ORDER
(
  water_no         NUMBER(18) not null,
  order_no         VARCHAR2(14),
  phone_no         VARCHAR2(11),
  sale_fee         NUMBER(18),
  sale_times       NUMBER(18),
  deal_fee         NUMBER(18),
  status           CHAR(1),
  update_date      DATE,
  insert_date      DATE,
  start_station    CHAR(4),
  end_station      CHAR(4),
  sale_fee_total   NUMBER(18),
  sale_times_total NUMBER(18),
  deal_fee_total   NUMBER(18),
  ticket_status    CHAR(2),
  qrcode           VARCHAR2(64),
  valid_time       DATE,
  tkcode           VARCHAR2(32),
  lock_dev         VARCHAR2(9)
);
-- Add comments to the table 
comment on table W_OL_QRCODE_ORDER
  is '��ά�붩����';
-- Add comments to the columns 
comment on column W_OL_QRCODE_ORDER.order_no
  is '������:8λ����+6λ��ˮ';
comment on column W_OL_QRCODE_ORDER.phone_no
  is '�ֻ���';
comment on column W_OL_QRCODE_ORDER.sale_fee
  is '�ѷ��۵���Ʊ����(��)';
comment on column W_OL_QRCODE_ORDER.sale_times
  is '�ѷ��۵���Ʊ����';
comment on column W_OL_QRCODE_ORDER.deal_fee
  is '�ѷ��۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRCODE_ORDER.status
  is '0:��֧��1:δ֧��';
comment on column W_OL_QRCODE_ORDER.update_date
  is '����ʱ��';
comment on column W_OL_QRCODE_ORDER.insert_date
  is '����ʱ��';
comment on column W_OL_QRCODE_ORDER.start_station
  is 'ʼ��վ(��·+��վ)';
comment on column W_OL_QRCODE_ORDER.end_station
  is '�յ�վ(��·+��վ)';
comment on column W_OL_QRCODE_ORDER.sale_fee_total
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRCODE_ORDER.sale_times_total
  is '���۵���Ʊ����';
comment on column W_OL_QRCODE_ORDER.deal_fee_total
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRCODE_ORDER.ticket_status
  is '00:����δִ��;01:�����������;02:����ȡ��;80:δ��Ʊ�򲻴���;81:���������(��ȡƱ);82:��ά���ѹ���Ч��;83:��Ʊ��ȡƱʼ��վ��һ��;84:��������';
comment on column W_OL_QRCODE_ORDER.qrcode
  is '��ά��';
comment on column W_OL_QRCODE_ORDER.valid_time
  is '��ά����Чʱ��';
comment on column W_OL_QRCODE_ORDER.tkcode
  is 'ȡƱ��(��ά�����ǰ)';
comment on column W_OL_QRCODE_ORDER.lock_dev
  is '�����ն˱��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QRCODE_ORDER
  add constraint PK_W_OL_QRCODE_ORDER primary key (WATER_NO);
  
  
-- Create table
create table W_OL_QRPAY_ORDER
(
  water_no         NUMBER(18) not null,
  order_no         VARCHAR2(14),
  sale_fee         NUMBER(18),
  sale_times       NUMBER(18),
  deal_fee         NUMBER(18),
  status           CHAR(1),
  order_date       DATE,
  insert_date      DATE,
  card_type        CHAR(4),
  qrpay_id         CHAR(10),
  qrpay_data       CHAR(34),
  phone_no         VARCHAR2(11),
  pay_date         DATE,
  pay_status       CHAR(2),
  pay_channel_type CHAR(2),
  pay_channel_code CHAR(4),
  sale_fee_total   NUMBER(18),
  sale_times_total NUMBER(18),
  deal_fee_total   NUMBER(18),
  acc_seq          NUMBER(18),
  order_ip         VARCHAR2(15),
  card_type_total  CHAR(4)
);
-- Add comments to the table 
comment on table W_OL_QRPAY_ORDER
  is '֧����ά�붩����';
-- Add comments to the columns 
comment on column W_OL_QRPAY_ORDER.order_no
  is '������:8λ����+6λ��ˮ';
comment on column W_OL_QRPAY_ORDER.sale_fee
  is '��Ʊ����Ʊ����(��)';
comment on column W_OL_QRPAY_ORDER.sale_times
  is '��Ʊ�۵���Ʊ����';
comment on column W_OL_QRPAY_ORDER.deal_fee
  is '��Ʊ�۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRPAY_ORDER.status
  is '0:δ֧��1:��֧��2:����ȡ��';
comment on column W_OL_QRPAY_ORDER.order_date
  is '��������ʱ��';
comment on column W_OL_QRPAY_ORDER.insert_date
  is '����ʱ��';
comment on column W_OL_QRPAY_ORDER.card_type
  is '��ƱƱ������(������+������)';
comment on column W_OL_QRPAY_ORDER.qrpay_id
  is '֧����ʶ';
comment on column W_OL_QRPAY_ORDER.qrpay_data
  is '֧����ά����Ϣ';
comment on column W_OL_QRPAY_ORDER.phone_no
  is '֧���ֻ���';
comment on column W_OL_QRPAY_ORDER.pay_date
  is '֧��ʱ��';
comment on column W_OL_QRPAY_ORDER.pay_status
  is '֧����� 00:֧���ɹ�;01:����;02:�������˻�;10:֧��ͨ��ͨѶ�쳣;99:�����쳣';
comment on column W_OL_QRPAY_ORDER.pay_channel_type
  is '֧���������� 01:����;02:����;03:΢��֧��;04:֧����֧��;09:����������֧��99:����';
comment on column W_OL_QRPAY_ORDER.pay_channel_code
  is '֧����������';
comment on column W_OL_QRPAY_ORDER.sale_fee_total
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRPAY_ORDER.sale_times_total
  is '���۵���Ʊ����';
comment on column W_OL_QRPAY_ORDER.deal_fee_total
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRPAY_ORDER.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRPAY_ORDER.order_ip
  is '���ɶ����ն�IP';
comment on column W_OL_QRPAY_ORDER.card_type_total
  is '����Ʊ������';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QRPAY_ORDER
  add constraint PK_W_OL_QRPAY_ORDER primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRPAY_ORDER to W_ACC_OL_APP;
grant select on W_OL_QRPAY_ORDER to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRPAY_ORDER to W_ACC_ST_APP;


-- Create table
create table W_OL_QRPAY_CREATE
(
  water_no       NUMBER(18) not null,
  message_id     VARCHAR2(2),
  msg_gen_time   VARCHAR2(14),
  termina_no     VARCHAR2(9),
  sam_logical_id VARCHAR2(16),
  termina_seq    NUMBER(18),
  acc_seq        NUMBER(18),
  order_no       VARCHAR2(14),
  order_date     DATE,
  card_type      CHAR(4),
  sale_fee       NUMBER(18),
  sale_times     NUMBER(18),
  deal_fee       NUMBER(18),
  qrpay_id       char(10),
  qrpay_data     char(34),
  return_code    VARCHAR2(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRPAY_CREATE
  is '֧����ά�붩���ϴ���';
-- Add comments to the columns 
comment on column W_OL_QRPAY_CREATE.message_id
  is '��Ϣ����';
comment on column W_OL_QRPAY_CREATE.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRPAY_CREATE.termina_no
  is '�ն˱��';
comment on column W_OL_QRPAY_CREATE.sam_logical_id
  is 'sam���߼���';
comment on column W_OL_QRPAY_CREATE.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRPAY_CREATE.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRPAY_CREATE.order_no
  is '������';
comment on column W_OL_QRPAY_CREATE.order_date
  is '��������ʱ��';  
comment on column W_OL_QRPAY_CREATE.card_type
  is '����Ʊ������(������+������)';
comment on column W_OL_QRPAY_CREATE.sale_fee
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRPAY_CREATE.sale_times
  is '���۵���Ʊ����';
comment on column W_OL_QRPAY_CREATE.deal_fee
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRPAY_CREATE.qrpay_id
  is '֧����ʶ';
comment on column W_OL_QRPAY_CREATE.qrpay_data
  is '֧����ά����Ϣ';
comment on column W_OL_QRPAY_CREATE.return_code
  is '��Ӧ��: 00:������������01:SAM��������02:�ն˱�Ų��Ϸ�03:�����ű��벻�Ϸ�04:�豸�������ظ�10:����ϵͳ�쳣99:����';
comment on column W_OL_QRPAY_CREATE.insert_date
  is '����ʱ��';
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRPAY_CREATE to W_ACC_OL_APP;
grant select on W_OL_QRPAY_CREATE to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRPAY_CREATE to W_ACC_ST_APP;


-- Create table
create table W_OL_QRPAY_APP
(
  water_no        NUMBER(18) not null,
  message_id      VARCHAR2(2),
  msg_gen_time    VARCHAR2(14),
  termina_no      VARCHAR2(9),
  hce_seq         NUMBER(18),
  acc_seq         NUMBER(18),
  phone_no        VARCHAR2(11),
  imsi            VARCHAR2(15),
  imei            VARCHAR2(15),
  app_code        CHAR(2),
  qrpay_id         char(10),
  qrpay_data       char(34),
  pay_date         DATE,
  pay_status       CHAR(2),
  pay_channel_type    CHAR(2),
  pay_channel_code    char(4),
  return_code    VARCHAR2(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRPAY_APP
  is '֧����ά��app֧�����';
-- Add comments to the columns 
comment on column W_OL_QRPAY_APP.message_id
  is '��Ϣ����';
comment on column W_OL_QRPAY_APP.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRPAY_APP.termina_no
  is '�ն˱��';
comment on column W_OL_QRPAY_APP.hce_seq
  is 'HCE������ˮ';
comment on column W_OL_QRPAY_APP.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRPAY_APP.phone_no
  is '�ֻ���';
comment on column W_OL_QRPAY_APP.imsi
  is '�ֻ��û���ʶ';
comment on column W_OL_QRPAY_APP.imei
  is '�ֻ��豸��ʶ';
comment on column W_OL_QRPAY_APP.app_code
  is 'app����';
comment on column W_OL_QRPAY_APP.qrpay_id
  is '֧����ʶ';
comment on column W_OL_QRPAY_APP.qrpay_data
  is '֧����ά����Ϣ';
comment on column W_OL_QRPAY_APP.pay_date
  is '֧��ʱ��';
comment on column W_OL_QRPAY_APP.pay_status
  is '֧����� 00:֧���ɹ�;01:����;02:�������˻�;10:֧��ͨ��ͨѶ�쳣;99:�����쳣';
comment on column W_OL_QRPAY_APP.pay_channel_type
  is '֧���������� 01:����;02:����;03:΢��֧��;04:֧����֧��;09:����������֧��99:����';
comment on column W_OL_QRPAY_APP.pay_channel_code
  is '֧����������';
comment on column W_OL_QRPAY_APP.return_code
  is '��Ӧ�� 00:����;01:ϵͳ��������г����쳣02:�������˿�,�ѳ�Ʊ;03:��������ʧ��';
comment on column W_OL_QRPAY_APP.insert_date
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QRPAY_APP
  add constraint PK_W_OL_QRPAY_APP primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRPAY_APP to W_ACC_OL_APP;
grant select on W_OL_QRPAY_APP to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRPAY_APP to W_ACC_ST_APP;


-- Create table
create table W_OL_QRPAY_DOWN
(
  water_no        NUMBER(18) not null,
  message_id      VARCHAR2(2),
  msg_gen_time    VARCHAR2(14),
  termina_no      VARCHAR2(9),
  termina_seq         NUMBER(18),
  order_no       VARCHAR2(14),
  acc_seq         NUMBER(18),
  qrpay_id         char(10),
  qrpay_data       char(34),
  pay_date         DATE,
  pay_status       CHAR(2),
  pay_channel_type    CHAR(2),
  pay_channel_code    char(4),
  deal_time         DATE,
  card_type      CHAR(4),
  sale_fee       NUMBER(18),
  sale_times     NUMBER(18),
  deal_fee       NUMBER(18),
  return_code    VARCHAR2(2),
  insert_date    DATE
);
-- Add comments to the table 
comment on table W_OL_QRPAY_DOWN
  is '֧����ά��֧������·�';
-- Add comments to the columns 
comment on column W_OL_QRPAY_DOWN.message_id
  is '��Ϣ����';
comment on column W_OL_QRPAY_DOWN.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRPAY_DOWN.termina_no
  is '�ն˱��';
comment on column W_OL_QRPAY_DOWN.termina_seq
  is '�ն˴�����ˮ';
comment on column W_OL_QRPAY_DOWN.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRPAY_DOWN.order_no
  is '������';
comment on column W_OL_QRPAY_DOWN.qrpay_id
  is '֧����ʶ';
comment on column W_OL_QRPAY_DOWN.qrpay_data
  is '֧����ά����Ϣ';
comment on column W_OL_QRPAY_DOWN.pay_date
  is '֧��ʱ��';
comment on column W_OL_QRPAY_DOWN.pay_status
  is '֧����� 00:֧���ɹ�;01:����;02:�������˻�;10:֧��ͨ��ͨѶ�쳣;99:�����쳣';
comment on column W_OL_QRPAY_DOWN.pay_channel_type
  is '֧���������� 01:����;02:����;03:΢��֧��;04:֧����֧��;09:����������֧��99:����';
comment on column W_OL_QRPAY_DOWN.pay_channel_code
  is '֧����������';
comment on column W_OL_QRPAY_DOWN.deal_time
  is 'acc����ʱ��';
comment on column W_OL_QRPAY_DOWN.card_type
  is '����Ʊ������(������+������)';
comment on column W_OL_QRPAY_DOWN.sale_fee
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRPAY_DOWN.sale_times
  is '���۵���Ʊ����';
comment on column W_OL_QRPAY_DOWN.deal_fee
  is '���۵���Ʊ�ܼ�(��)';
comment on column W_OL_QRPAY_DOWN.return_code
  is '��Ӧ�� 00:����01:��Ʊ��������02:û���������֧��10:ϵͳ��������г����쳣';
comment on column W_OL_QRPAY_DOWN.insert_date
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QRPAY_DOWN
  add constraint PK_W_OL_QRPAY_DOWN primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRPAY_DOWN to W_ACC_OL_APP;
grant select on W_OL_QRPAY_DOWN to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRPAY_DOWN to W_ACC_ST_APP;


create table W_OL_QRPAY_ORSTATUS
(
  water_no         NUMBER(18) not null,
  message_id       VARCHAR2(2),
  msg_gen_time     VARCHAR2(14),
  termina_no       VARCHAR2(9),
  hce_seq          NUMBER(18),
  acc_seq          NUMBER(18),
  phone_no         VARCHAR2(11),
  imsi             VARCHAR2(15),
  imei             VARCHAR2(15),
  app_code         CHAR(2),
  qrpay_id         CHAR(10),
  qrpay_data       CHAR(34),
  order_no         VARCHAR2(14),
  order_date       DATE,
  card_type        CHAR(4),
  sale_fee         NUMBER(18),
  sale_times       NUMBER(18),
  deal_fee         NUMBER(18),
  status           CHAR(2),
  insert_date      DATE,
  card_type_total  CHAR(4),
  sale_fee_total   NUMBER(18),
  sale_times_total NUMBER(18),
  deal_fee_total   NUMBER(18)
);
-- Add comments to the table 
comment on table W_OL_QRPAY_ORSTATUS
  is '֧����ά�붩����ѯ';
-- Add comments to the columns 
comment on column W_OL_QRPAY_ORSTATUS.message_id
  is '��Ϣ����';
comment on column W_OL_QRPAY_ORSTATUS.msg_gen_time
  is '��Ϣ����ʱ��';
comment on column W_OL_QRPAY_ORSTATUS.termina_no
  is '�ն˱��';
comment on column W_OL_QRPAY_ORSTATUS.hce_seq
  is 'HCE������ˮ';
comment on column W_OL_QRPAY_ORSTATUS.acc_seq
  is '���Ĵ�����ˮ';
comment on column W_OL_QRPAY_ORSTATUS.phone_no
  is '�ֻ���';
comment on column W_OL_QRPAY_ORSTATUS.imsi
  is '�ֻ��û���ʶ';
comment on column W_OL_QRPAY_ORSTATUS.imei
  is '�ֻ��豸��ʶ';
comment on column W_OL_QRPAY_ORSTATUS.app_code
  is 'app����';
comment on column W_OL_QRPAY_ORSTATUS.qrpay_id
  is '֧����ʶ';
comment on column W_OL_QRPAY_ORSTATUS.qrpay_data
  is '֧����ά����Ϣ';
comment on column W_OL_QRPAY_ORSTATUS.order_no
  is '������:8λ����+6λ��ˮ';
comment on column W_OL_QRPAY_ORSTATUS.order_date
  is '��������ʱ��';
comment on column W_OL_QRPAY_ORSTATUS.card_type
  is '��ƱƱ������(������+������)';
comment on column W_OL_QRPAY_ORSTATUS.sale_fee
  is '��Ʊ����Ʊ����(��)';
comment on column W_OL_QRPAY_ORSTATUS.sale_times
  is '��Ʊ����Ʊ����';
comment on column W_OL_QRPAY_ORSTATUS.deal_fee
  is '��Ʊ����Ʊ�ܼ�(��)';
comment on column W_OL_QRPAY_ORSTATUS.status
  is '00:����δ֧��01:������֧��02:����֧������뷢���ܼ۲�һ��03:����������04:������ȡ��';
comment on column W_OL_QRPAY_ORSTATUS.insert_date
  is '����ʱ��';
comment on column W_OL_QRPAY_ORSTATUS.card_type_total
  is '����Ʊ������(������+������)';
comment on column W_OL_QRPAY_ORSTATUS.sale_fee_total
  is '���۵���Ʊ����(��)';
comment on column W_OL_QRPAY_ORSTATUS.sale_times_total
  is '���۵���Ʊ����';
comment on column W_OL_QRPAY_ORSTATUS.deal_fee_total
  is '���۵���Ʊ�ܼ�(��)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QRPAY_ORSTATUS
  add constraint PK_W_OL_QRPAY_ORSTATUS primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QRPAY_ORSTATUS to W_ACC_OL_APP;
grant select on W_OL_QRPAY_ORSTATUS to W_ACC_OL_DEV;
grant select, insert, update, delete on W_OL_QRPAY_ORSTATUS to W_ACC_ST_APP;


-- Create table
create table W_OL_QRPAY_ID
(
  qrpay_id NUMBER(10) default 0 not null
);
-- Add comments to the table 
comment on table W_OL_QRPAY_ID
  is '֧����ά��֧����ʶ';
-- Add comments to the columns 
comment on column W_OL_QRPAY_ID.qrpay_id
  is '֧����ʶ';
grant select, insert, update, delete on W_OL_QRPAY_ID to W_ACC_OL_APP;
grant select on W_OL_QRPAY_ID to W_ACC_OL_DEV;



-- Create table
create table W_OL_QUE_MESSAGE
(
  message_id           NUMBER(18) not null,
  message_time         DATE,
  line_id              CHAR(2),
  station_id           CHAR(2),
  ip_address           VARCHAR2(15),
  message              BLOB,
  process_flag         CHAR(1),
  is_para_inform_msg   CHAR(1),
  para_inform_water_no INTEGER,
  message_type         VARCHAR2(2),
  message_type_sub     VARCHAR2(2),
  remark               VARCHAR2(50)
);
-- Add comments to the columns 
comment on column W_OL_QUE_MESSAGE.message_id
  is '��Ϣid';
comment on column W_OL_QUE_MESSAGE.message_time
  is '��Ϣʱ��';
comment on column W_OL_QUE_MESSAGE.line_id
  is '��·����';
comment on column W_OL_QUE_MESSAGE.station_id
  is '��վ����';
comment on column W_OL_QUE_MESSAGE.ip_address
  is 'ip��ַ';
comment on column W_OL_QUE_MESSAGE.message
  is '��Ϣ';
comment on column W_OL_QUE_MESSAGE.process_flag
  is '�����־0:δ����1:����';
comment on column W_OL_QUE_MESSAGE.is_para_inform_msg
  is '�Ƿ����֪ͨ��Ϣ';
comment on column W_OL_QUE_MESSAGE.para_inform_water_no
  is '����֪ͨ��ˮ��';
comment on column W_OL_QUE_MESSAGE.message_type
  is '��Ϣ����';
comment on column W_OL_QUE_MESSAGE.message_type_sub
  is '��Ϣ�����ͣ�01:����ļ���02�������ļ���03����Ʊ�����ļ���';
comment on column W_OL_QUE_MESSAGE.remark
  is '��ע';
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_OL_QUE_MESSAGE
  add constraint W_OL_QUE_MESSAGE_PK primary key (MESSAGE_ID);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_OL_QUE_MESSAGE to W_ACC_ST_APP;
grant select, insert, update, delete on W_OL_QUE_MESSAGE to W_ACC_OL_APP;
grant select on W_OL_QUE_MESSAGE to W_ACC_OL_dev;
