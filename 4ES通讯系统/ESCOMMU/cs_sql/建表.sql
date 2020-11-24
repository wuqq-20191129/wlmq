-- Create table
create table W_IC_ES_BCP_CONFIG
(
  server   VARCHAR2(50) not null,
  db       VARCHAR2(50) not null,
  account  VARCHAR2(20) not null,
  password VARCHAR2(100) not null,
  enc_flag VARCHAR2(1) default '0' not null,
  remark   VARCHAR2(100)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_BCP_CONFIG to W_ACC_TK_APP;
grant select on W_IC_ES_BCP_CONFIG to W_ACC_TK_DEV;
grant select on W_IC_ES_BCP_CONFIG to W_ACC_TK_RPT;


-- Create table
create table W_IC_ES_CFG_SYS
(
  sender_code  VARCHAR2(10),
  city_code    VARCHAR2(10),
  busi_code    VARCHAR2(10),
  card_version VARCHAR2(10),
  app_version  VARCHAR2(10),
  key_version  VARCHAR2(10)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_CFG_SYS to W_ACC_TK_APP;
grant select on W_IC_ES_CFG_SYS to W_ACC_TK_DEV;
grant select on W_IC_ES_CFG_SYS to W_ACC_TK_RPT;

-- Create table
create table W_IC_ES_STATUS
(
  device_id   VARCHAR2(6) not null,
  operator_id VARCHAR2(10) not null,
  status_time VARCHAR2(20) not null,
  status      VARCHAR2(4) not null,
  remark      VARCHAR2(30)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_IC_ES_STATUS
  add primary key (DEVICE_ID, STATUS_TIME, STATUS);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_STATUS to W_ACC_TK_APP;
grant select on W_IC_ES_STATUS to W_ACC_TK_DEV;
grant select on W_IC_ES_STATUS to W_ACC_TK_RPT;


-- Create table
create table W_IC_ES_INFO_FILE
(
  water_no    NUMBER(18),
  device_id   VARCHAR2(6) not null,
  file_name   VARCHAR2(30) not null,
  status      VARCHAR2(1) not null,
  info_time   DATE not null,
  update_time DATE,
  operator    VARCHAR2(10),
  remark      VARCHAR2(100)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_INFO_FILE to W_ACC_TK_APP;
grant select on W_IC_ES_INFO_FILE to W_ACC_TK_DEV;
grant select on W_IC_ES_INFO_FILE to W_ACC_TK_RPT;

-- Create table
create table W_IC_ES_FILE_AUDIT
(
  water_no      NUMBER(18),
  device_id     VARCHAR2(6) not null,
  file_name     VARCHAR2(30) not null,
  status        VARCHAR2(1) not null,
  info_time     DATE not null,
  info_operator VARCHAR2(10)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_FILE_AUDIT to W_ACC_TK_APP;
grant select on W_IC_ES_FILE_AUDIT to W_ACC_TK_DEV;
grant select on W_IC_ES_FILE_AUDIT to W_ACC_TK_RPT;

-- Create table
create table W_IC_ES_LEGAL_DEVTYPE
(
  dev_type_id CHAR(2)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_LEGAL_DEVTYPE to W_ACC_TK_APP;
grant select on W_IC_ES_LEGAL_DEVTYPE to W_ACC_TK_DEV;

-- Create table
create table W_IC_ES_ROLE
(
  sys_group_id VARCHAR2(4) not null,
  group_level  VARCHAR2(2) not null,
  remark       VARCHAR2(100)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_IC_ES_ROLE
  add primary key (SYS_GROUP_ID);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_ROLE to W_ACC_TK_APP;
grant select on W_IC_ES_ROLE to W_ACC_TK_DEV;
grant select on W_IC_ES_ROLE to W_ACC_TK_RPT;


-- Create table
create table W_IC_ES_ORDER_NUM_CHANGE
(
  water_no           NUMBER(18) not null,
  file_name          VARCHAR2(30) not null,
  order_no           VARCHAR2(14) not null,
  fini_pronum_reset  INTEGER not null,
  fini_pronum_before INTEGER not null,
  reset_time         DATE not null,
  remark             VARCHAR2(200)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table W_IC_ES_ORDER_NUM_CHANGE
  add primary key (WATER_NO);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_TK_APP;
grant select on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_TK_DEV;
grant select on W_IC_ES_ORDER_NUM_CHANGE to W_ACC_TK_RPT;


-- Create table
create table W_IC_ES_PDU_REPEAT_LOGIC
(
  logic_id          CHAR(20) not null,
  card_main_type    CHAR(2),
  card_sub_type     CHAR(2),
  req_no            CHAR(10),
  phy_id            CHAR(20) not null,
  print_id          CHAR(20) not null,
  manu_time         DATE,
  card_money        NUMBER(12,2),
  peri_avadate      DATE,
  kdc_version       CHAR(2),
  hdl_time          DATE,
  order_no          CHAR(14) not null,
  status_flag       CHAR(2),
  card_type         CHAR(3) default '001',
  card_ava_days     VARCHAR2(10) default '000',
  exit_line_code    VARCHAR2(2) default '00',
  exit_station_code VARCHAR2(2) default '00',
  modal             VARCHAR2(3) default '000'
);
grant select, insert, update, delete on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_TK_APP;
grant select on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_TK_DEV;
grant select on W_IC_ES_PDU_REPEAT_LOGIC to W_ACC_TK_RPT;

-- Create table
create table W_IC_ES_FILE_ERROR
(
  water_no      NUMBER(18),
  device_id     VARCHAR2(6) not null,
  file_name     VARCHAR2(30) not null,
  error_code    VARCHAR2(2) not null,
  gen_time      DATE not null,
  info_flag     VARCHAR2(1) not null,
  info_time     DATE,
  info_operator VARCHAR2(10),
  remark        VARCHAR2(100)
);
grant select, insert, update, delete on W_IC_ES_FILE_ERROR to W_ACC_TK_APP;
grant select on W_IC_ES_FILE_ERROR to W_ACC_TK_DEV;
grant select on W_IC_ES_FILE_ERROR to W_ACC_TK_RPT;


-- Create sequence 
create sequence W_SEQ_W_IC_ES_INFO_FILE
minvalue 1
maxvalue 9999999999999999999999999999
start with 38021
increment by 1
cache 10;

-- Create sequence 
create sequence W_SEQ_W_IC_ES_FILE_AUDIT
minvalue 1
maxvalue 9999999999999999999999999999
start with 5281
increment by 1
cache 10;

-- Create sequence 
create sequence W_SEQ_W_IC_ES_FILE_ERROR
minvalue 1
maxvalue 9999999999999999999999999999
start with 2701
increment by 1
cache 10;

-- Create sequence 
create sequence W_SEQ_W_IC_ES_ORDER_NUM_CHANGE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
