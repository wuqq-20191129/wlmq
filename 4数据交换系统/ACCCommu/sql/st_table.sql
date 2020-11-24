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
  is '流水号';
comment on column W_ST_FLOW_NON_RTN.id
  is 'id号';
comment on column W_ST_FLOW_NON_RTN.apply_bill
  is '申请单号';
comment on column W_ST_FLOW_NON_RTN.apply_action
  is '申请动作';
comment on column W_ST_FLOW_NON_RTN.return_flag
  is '退款标志';
comment on column W_ST_FLOW_NON_RTN.return_time
  is '退款时间';
comment on column W_ST_FLOW_NON_RTN.processing
  is '处理';
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
  is '记名卡挂失/解挂/补卡申请流程表';
-- Add comments to the columns 
comment on column W_ST_CM_LOG_REPORT_LOSS.water_no
  is '流水号';
comment on column W_ST_CM_LOG_REPORT_LOSS.apply_bill
  is '凭证ID';
comment on column W_ST_CM_LOG_REPORT_LOSS.apply_action
  is '处理方式(1:查询;2:办理成功通知)';
comment on column W_ST_CM_LOG_REPORT_LOSS.return_flag
  is '返回结果(00:允许申请;01:证件不存在;02:证件已过期;03:黑名单卡;04:重复申请;05:已申请,未制卡;06:已申请补卡;07:已申请退款;10:无挂失,不可解挂)';
comment on column W_ST_CM_LOG_REPORT_LOSS.return_time
  is '返回时间';
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
  is '记名卡挂失状态表';
-- Add comments to the columns 
comment on column W_ST_LIST_REPORT_LOSS.water_no
  is '流水号';
comment on column W_ST_LIST_REPORT_LOSS.apply_bill
  is '凭证ID';
comment on column W_ST_LIST_REPORT_LOSS.create_time
  is '创建时间';
comment on column W_ST_LIST_REPORT_LOSS.busniss_type
  is '业务类型';
comment on column W_ST_LIST_REPORT_LOSS.identify_type
  is '证件类型(1:身份证;2:学生证;3:军人证;4:老人卡;5:员工卡;9:其他)';
comment on column W_ST_LIST_REPORT_LOSS.identity_id
  is '证件号码';
comment on column W_ST_LIST_REPORT_LOSS.line_id
  is '线路';
comment on column W_ST_LIST_REPORT_LOSS.station_id
  is '车站';
comment on column W_ST_LIST_REPORT_LOSS.dev_type_id
  is '设备类型';
comment on column W_ST_LIST_REPORT_LOSS.device_id
  is '设备ID';
comment on column W_ST_LIST_REPORT_LOSS.apply_name
  is '姓名';
comment on column W_ST_LIST_REPORT_LOSS.apply_sex
  is '性别';
comment on column W_ST_LIST_REPORT_LOSS.expired_date
  is '有效日期';
comment on column W_ST_LIST_REPORT_LOSS.tel_no
  is '电话';
comment on column W_ST_LIST_REPORT_LOSS.fax
  is '传真';
comment on column W_ST_LIST_REPORT_LOSS.address
  is '地址';
comment on column W_ST_LIST_REPORT_LOSS.operator_id
  is '操作员';
comment on column W_ST_LIST_REPORT_LOSS.apply_datetime
  is '申请时间';
comment on column W_ST_LIST_REPORT_LOSS.shift_id
  is '班次';
comment on column W_ST_LIST_REPORT_LOSS.card_app_flag
  is '卡应用标志';
comment on column W_ST_LIST_REPORT_LOSS.card_main_id
  is '票卡主类型';
comment on column W_ST_LIST_REPORT_LOSS.card_sub_id
  is '票卡子类型';
comment on column W_ST_LIST_REPORT_LOSS.card_logical_id
  is '逻辑卡号';
comment on column W_ST_LIST_REPORT_LOSS.card_physical_id
  is '物理卡号';
comment on column W_ST_LIST_REPORT_LOSS.card_print_id
  is '打印号';
comment on column W_ST_LIST_REPORT_LOSS.balance_water_no
  is '清算流水号';
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
  is '记名卡挂失/解挂/补卡申请待处理表';

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
  is '记名卡申请待处理表';
  
-- Create table
create table W_ST_ID_NON_RTN
(
  id INTEGER not null
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_ST_ID_NON_RTN
  add constraint PK_W_ST_ID_NON_RETURN primary key (ID);


