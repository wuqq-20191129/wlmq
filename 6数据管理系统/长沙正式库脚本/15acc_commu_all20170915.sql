-----------------------------------------------
-- Export file for user ACC_COMMU            --
-- Created by ACC-001 on 2017/9/15, 17:53:14 --
-----------------------------------------------

set define off
spool 15acc_commu_all20170915.log

prompt
prompt Creating table CM_CFG_CLEAR_TABLE
prompt =================================
prompt
create table ACC_COMMU.CM_CFG_CLEAR_TABLE
(
  origin_table_name VARCHAR2(30) not null,
  ab_name           VARCHAR2(30) not null,
  keep_days         INTEGER,
  divide_recd_count INTEGER,
  clear_flag        INTEGER,
  create_sql        VARCHAR2(2000),
  table_columns     VARCHAR2(400),
  date_type         VARCHAR2(25),
  db_name           VARCHAR2(30)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.CM_CFG_CLEAR_TABLE
  is '�������������ñ�';
alter table ACC_COMMU.CM_CFG_CLEAR_TABLE
  add constraint PK_CM_CFG_CLEAR_TABLE primary key (ORIGIN_TABLE_NAME)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_CFG_STATUS_MAPPING
prompt ====================================
prompt
create table ACC_COMMU.CM_CFG_STATUS_MAPPING
(
  status_id        CHAR(3) not null,
  status_value     CHAR(1) not null,
  acc_status_value CHAR(1),
  status_name      VARCHAR2(50),
  acc_status_name  VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_CFG_STATUS_MAPPING
  add constraint CM_CFG_STATUS_MAPPING_PK primary key (STATUS_ID, STATUS_VALUE)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_CFG_SYS
prompt =========================
prompt
create table ACC_COMMU.CM_CFG_SYS
(
  config_name  VARCHAR2(100) not null,
  config_value VARCHAR2(550) not null,
  remark       VARCHAR2(250)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_CFG_SYS
  add constraint CM_CFG_SYS_PK primary key (CONFIG_NAME)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_DEV_CURRENT_STATUS
prompt ====================================
prompt
create table ACC_COMMU.CM_DEV_CURRENT_STATUS
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  dev_type_id      VARCHAR2(2) not null,
  device_id        VARCHAR2(4) not null,
  status_id        VARCHAR2(4),
  status_value     VARCHAR2(3),
  status_datetime  DATE,
  acc_status_value VARCHAR2(1),
  oper_id          VARCHAR2(6)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_DEV_CURRENT_STATUS
  add constraint PK_CM_DEV_CURRENT_STATUS primary key (LINE_ID, STATION_ID, DEV_TYPE_ID, DEVICE_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_DEV_ERROR_STATUS
prompt ==================================
prompt
create table ACC_COMMU.CM_DEV_ERROR_STATUS
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  dev_type_id      VARCHAR2(2) not null,
  device_id        VARCHAR2(4) not null,
  status_id        VARCHAR2(4) not null,
  status_value     VARCHAR2(3) not null,
  status_datetime  DATE,
  acc_status_value VARCHAR2(1),
  oper_id          VARCHAR2(6)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_DEV_EVENT
prompt ===========================
prompt
create table ACC_COMMU.CM_DEV_EVENT
(
  event_datetime DATE,
  line_id        VARCHAR2(2),
  station_id     VARCHAR2(2),
  dev_type_id    VARCHAR2(2),
  device_id      VARCHAR2(4),
  event_id       VARCHAR2(2),
  event_argument VARCHAR2(32)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_DEV_PARA_VER_CUR
prompt ==================================
prompt
create table ACC_COMMU.CM_DEV_PARA_VER_CUR
(
  line_id      CHAR(2) not null,
  station_id   CHAR(2) not null,
  dev_type_id  CHAR(2) not null,
  device_id    CHAR(3) not null,
  parm_type_id CHAR(4) not null,
  record_flag  CHAR(1) not null,
  version_no   CHAR(10) not null,
  report_date  DATE not null,
  remark       VARCHAR2(50)
)
tablespace TBS_ST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_DEV_PARA_VER_CUR
  add constraint CM_DEV_PARA_VER_CUR_PK primary key (LINE_ID, STATION_ID, DEV_TYPE_ID, DEVICE_ID, PARM_TYPE_ID, RECORD_FLAG)
  using index 
  tablespace TBS_ST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_DEV_PARA_VER_HIS
prompt ==================================
prompt
create table ACC_COMMU.CM_DEV_PARA_VER_HIS
(
  water_no     NUMBER(18) not null,
  line_id      CHAR(2) not null,
  station_id   CHAR(2) not null,
  dev_type_id  CHAR(2) not null,
  device_id    CHAR(3) not null,
  parm_type_id CHAR(4) not null,
  record_flag  CHAR(1) not null,
  version_no   CHAR(10) not null,
  report_date  DATE not null,
  remark       VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_DEV_PARA_VER_HIS
  add constraint CM_DEV_PARA_VER_HIS_PK primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_EC_CONNECT_LOG
prompt ================================
prompt
create table ACC_COMMU.CM_EC_CONNECT_LOG
(
  water_no         NUMBER(8) not null,
  connect_datetime DATE not null,
  connect_ip       VARCHAR2(15) not null,
  connect_result   CHAR(1),
  remark           VARCHAR2(100)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_EC_CONNECT_LOG
  add primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_EC_FTP_LOG
prompt ============================
prompt
create table ACC_COMMU.CM_EC_FTP_LOG
(
  water_no     NUMBER(8) not null,
  datetime_ftp DATE not null,
  ip           VARCHAR2(15) not null,
  filename     VARCHAR2(50),
  start_time   DATE,
  spend_time   NUMBER(8),
  result       CHAR(1),
  remark       VARCHAR2(512)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_EC_FTP_LOG
  add primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_EC_LOG
prompt ========================
prompt
create table ACC_COMMU.CM_EC_LOG
(
  water_no     NUMBER(18) not null,
  message_id   VARCHAR2(3),
  message_name VARCHAR2(50),
  message_from VARCHAR2(50),
  start_time   DATE,
  end_time     DATE,
  use_time     INTEGER,
  result       CHAR(1),
  hdl_thread   VARCHAR2(50),
  sys_level    CHAR(1),
  remark       VARCHAR2(512)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_EC_LOG
  add primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_EC_LOG_LEVEL
prompt ==============================
prompt
create table ACC_COMMU.CM_EC_LOG_LEVEL
(
  sys_code    CHAR(1) not null,
  sys_level   CHAR(1) not null,
  set_time    DATE not null,
  operator    CHAR(6) not null,
  version_no  CHAR(10) not null,
  record_flag CHAR(1) not null
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_EC_LOG_LEVEL
  add primary key (SYS_CODE, VERSION_NO, RECORD_FLAG)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_EC_MSG_PRIORITY
prompt =================================
prompt
create table ACC_COMMU.CM_EC_MSG_PRIORITY
(
  type   VARCHAR2(1) not null,
  msg_id VARCHAR2(3) not null,
  remark VARCHAR2(100)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;

prompt
prompt Creating table CM_EC_OPERATOR_LOG
prompt =================================
prompt
create table ACC_COMMU.CM_EC_OPERATOR_LOG
(
  oper_id    VARCHAR2(10) not null,
  dev_id     VARCHAR2(8) not null,
  login_time DATE,
  exit_time  DATE,
  memo       CHAR(30)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_EC_RECV_SEND_LOG
prompt ==================================
prompt
create table ACC_COMMU.CM_EC_RECV_SEND_LOG
(
  water_no     NUMBER(10) not null,
  datetime_rec DATE not null,
  ip           VARCHAR2(15) not null,
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(1)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_EC_RECV_SEND_LOG
  add primary key (WATER_NO, DATETIME_REC, IP)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_ERR_PRM_BLACK_DOWN
prompt ====================================
prompt
create table ACC_COMMU.CM_ERR_PRM_BLACK_DOWN
(
  card_logical_id VARCHAR2(20) not null,
  card_type       CHAR(1),
  remark          VARCHAR2(256),
  create_datetime DATE
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.CM_ERR_PRM_BLACK_DOWN
  is '�����������·������¼��';
comment on column ACC_COMMU.CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:������,2:������';

prompt
prompt Creating table CM_ERR_PRM_BLACK_SEC_DOWN
prompt ========================================
prompt
create table ACC_COMMU.CM_ERR_PRM_BLACK_SEC_DOWN
(
  begin_logical_id VARCHAR2(20) not null,
  end_logical_id   VARCHAR2(20) not null,
  card_type        CHAR(1),
  remark           VARCHAR2(256),
  create_datetime  DATE
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
comment on table ACC_COMMU.CM_ERR_PRM_BLACK_SEC_DOWN
  is '�������β����·������¼��';
comment on column ACC_COMMU.CM_ERR_PRM_BLACK_SEC_DOWN.card_type
  is '1:������,2:������';

prompt
prompt Creating table CM_FILE_RECV
prompt ===========================
prompt
create table ACC_COMMU.CM_FILE_RECV
(
  water_no     INTEGER not null,
  file_name    VARCHAR2(30),
  file_type    VARCHAR2(3),
  new_filename VARCHAR2(40),
  file_path    VARCHAR2(40),
  his_path     VARCHAR2(10),
  err_path     VARCHAR2(10),
  status       INTEGER,
  remark       VARCHAR2(50),
  flag         VARCHAR2(2),
  insert_time  VARCHAR2(19),
  update_time  VARCHAR2(19),
  handle_path  VARCHAR2(10)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column ACC_COMMU.CM_FILE_RECV.file_type
  is '�ļ�����';
comment on column ACC_COMMU.CM_FILE_RECV.file_path
  is '�����ļ�Ŀ¼';
comment on column ACC_COMMU.CM_FILE_RECV.his_path
  is '��ʷ�ļ�Ŀ¼';
comment on column ACC_COMMU.CM_FILE_RECV.err_path
  is '�����ļ�Ŀ¼';
comment on column ACC_COMMU.CM_FILE_RECV.status
  is '0��δ����
1�����ڴ���
2���ļ���ʽ������
3���ļ���������쳣
4:   �Ѵ���
5:    �ļ�������';
comment on column ACC_COMMU.CM_FILE_RECV.flag
  is 'st:����
tk:Ʊ��
cm:ͨ��';
comment on column ACC_COMMU.CM_FILE_RECV.handle_path
  is '���ڴ�����ļ�Ŀ¼';
alter table ACC_COMMU.CM_FILE_RECV
  add constraint PK_CM_FILE_RECV primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_IDX_HISTORY
prompt =============================
prompt
create table ACC_COMMU.CM_IDX_HISTORY
(
  his_table         VARCHAR2(40) not null,
  origin_table_name VARCHAR2(30),
  begin_date        VARCHAR2(20),
  end_date          VARCHAR2(20),
  recd_count        INTEGER,
  recd_type         VARCHAR2(3)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.CM_IDX_HISTORY
  is '�м�ͳ�Ʊ�������ʷ������';
alter table ACC_COMMU.CM_IDX_HISTORY
  add constraint PK_CM_IDX_HISTORY primary key (HIS_TABLE)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_BUSDATA_FILE
prompt ==================================
prompt
create table ACC_COMMU.CM_LOG_BUSDATA_FILE
(
  balance_water_no CHAR(10) not null,
  file_type        VARCHAR2(6) not null,
  insert_time      DATE
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_LOG_BUSDATA_FILE
  add constraint PK_CM_LOG_BUSDATA_FILE primary key (BALANCE_WATER_NO, FILE_TYPE)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_CLEAR_TABLE
prompt =================================
prompt
create table ACC_COMMU.CM_LOG_CLEAR_TABLE
(
  origin_table_name    VARCHAR2(30),
  dest_table_name      VARCHAR2(30),
  begin_clear_datetime CHAR(19),
  end_clear_datetime   CHAR(19),
  spent_time           VARCHAR2(8),
  clear_recd_count     NUMBER,
  err_discribe         VARCHAR2(1000),
  sql_label            VARCHAR2(1500)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.CM_LOG_CLEAR_TABLE
  is '����������������־��';

prompt
prompt Creating table CM_LOG_COMMU
prompt ===========================
prompt
create table ACC_COMMU.CM_LOG_COMMU
(
  water_no     NUMBER(18) not null,
  message_id   VARCHAR2(3),
  message_name VARCHAR2(50),
  message_from VARCHAR2(50),
  start_time   DATE,
  end_time     DATE,
  use_time     INTEGER,
  result       CHAR(1),
  hdl_thread   VARCHAR2(250),
  sys_level    CHAR(1),
  remark       VARCHAR2(200)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 6
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_LOG_COMMU
  add constraint CM_LOG_COMMU_PK primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_CONNECT
prompt =============================
prompt
create table ACC_COMMU.CM_LOG_CONNECT
(
  water_no         NUMBER(8) not null,
  connect_datetime DATE not null,
  connect_ip       VARCHAR2(15) not null,
  connect_result   CHAR(1),
  remark           VARCHAR2(100)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_LOG_CONNECT
  add constraint PK_CM_LOG_CONNECT primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_FTP
prompt =========================
prompt
create table ACC_COMMU.CM_LOG_FTP
(
  water_no     NUMBER(8) not null,
  datetime_ftp DATE not null,
  ip           VARCHAR2(15) not null,
  filename     VARCHAR2(50),
  start_time   DATE,
  spend_time   NUMBER(8),
  result       CHAR(1),
  remark       VARCHAR2(512)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_RECV_SEND
prompt ===============================
prompt
create table ACC_COMMU.CM_LOG_RECV_SEND
(
  water_no     NUMBER(10) not null,
  datetime_rec DATE not null,
  ip           VARCHAR2(15) not null,
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(1)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_LOG_RECV_SEND
  add constraint PK_CM_LOG_RECV_SEND primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_LOG_RECV_SEND000001
prompt =====================================
prompt
create table ACC_COMMU.CM_LOG_RECV_SEND000001
(
  water_no     NUMBER(10) not null,
  datetime_rec DATE not null,
  ip           VARCHAR2(15) not null,
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(1)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_PRM_DW_AUTO_CONFIG
prompt ====================================
prompt
create table ACC_COMMU.CM_PRM_DW_AUTO_CONFIG
(
  parm_type_id  CHAR(4) not null,
  cfg_year      VARCHAR2(100) not null,
  cfg_month     VARCHAR2(100) not null,
  cfg_date      VARCHAR2(100) not null,
  cfg_hour      VARCHAR2(100) not null,
  cfg_minute    VARCHAR2(100) not null,
  download_flag VARCHAR2(1) not null,
  remark        VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_PRM_DW_AUTO_CONFIG
  add constraint CM_PRM_DW_AUTO_CONFIG_PK primary key (PARM_TYPE_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_PRM_DW_AUTO_CONFIG_NUM
prompt ========================================
prompt
create table ACC_COMMU.CM_PRM_DW_AUTO_CONFIG_NUM
(
  parm_type_id     CHAR(4) not null,
  min_download_num INTEGER not null,
  remark           VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_PRM_DW_AUTO_CONFIG_NUM
  add constraint CM_PRM_DW_AUTO_CONFIG_NUM_PK primary key (PARM_TYPE_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_PRM_DW_AUTO_LOG
prompt =================================
prompt
create table ACC_COMMU.CM_PRM_DW_AUTO_LOG
(
  water_no            INTEGER not null,
  download_date       DATE not null,
  parm_type_id        CHAR(4) not null,
  is_gen_dw_water_no  VARCHAR2(1),
  is_gen_dw_parm_info VARCHAR2(1),
  is_gen_dw_lcc_info  VARCHAR2(1),
  start_time          DATE,
  end_time            DATE,
  remark              VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_PRM_DW_AUTO_LOG
  add constraint CM_PRM_DW_AUTO_LOG_PK primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_PRM_DW_AUTO_LOG_WATERNO
prompt =========================================
prompt
create table ACC_COMMU.CM_PRM_DW_AUTO_LOG_WATERNO
(
  water_no INTEGER not null
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;

prompt
prompt Creating table CM_QUE_MESSAGE
prompt =============================
prompt
create table ACC_COMMU.CM_QUE_MESSAGE
(
  message_id           NUMBER(18) not null,
  message_time         DATE,
  line_id              CHAR(2),
  station_id           CHAR(2),
  ip_address           VARCHAR2(15),
  message              BLOB,
  process_flag         CHAR(1),
  is_para_inform_msg   CHAR(1),
  para_inform_water_no INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_QUE_MESSAGE
  add constraint CM_QUE_MESSAGE_PK primary key (MESSAGE_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_SEQ_TK_FILE
prompt =============================
prompt
create table ACC_COMMU.CM_SEQ_TK_FILE
(
  file_type VARCHAR2(10),
  seq       INTEGER,
  alter_day CHAR(8),
  remark    VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.CM_SEQ_TK_FILE
  is 'Ʊ��ͨѶ�ӿ��ļ���ż�¼��';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.file_type
  is '�ļ�����';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.seq
  is '��ǰ���';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.alter_day
  is '�޸�����';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.remark
  is '��ע';

prompt
prompt Creating table CM_SYS_VERSION
prompt =============================
prompt
create table ACC_COMMU.CM_SYS_VERSION
(
  version_no  VARCHAR2(10) not null,
  operator_id VARCHAR2(10),
  valid_date  CHAR(10),
  del_desc    VARCHAR2(255),
  update_desc VARCHAR2(255),
  add_desc    VARCHAR2(255),
  note        VARCHAR2(255)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_SYS_VERSION
  add constraint PK_CM_SYS_VERSION_1 primary key (VERSION_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TK_FILE_RECV
prompt ==============================
prompt
create table ACC_COMMU.CM_TK_FILE_RECV
(
  water_no  INTEGER not null,
  file_name VARCHAR2(30),
  file_path VARCHAR2(30),
  ״̬        INTEGER,
  remark    VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column ACC_COMMU.CM_TK_FILE_RECV.״̬
  is '0��δ����
1�����ڴ���
2���ļ���ʽ������
3���Ѵ���';
alter table ACC_COMMU.CM_TK_FILE_RECV
  add constraint PK_CM_TK_FILE_RECV primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_TRAFFIC
prompt =========================
prompt
create table ACC_COMMU.CM_TRAFFIC
(
  message_sequ     VARCHAR2(22) not null,
  line_id          VARCHAR2(2),
  station_id       VARCHAR2(2),
  traffic_datetime DATE,
  traffic_type     VARCHAR2(1),
  invalid_ticket   INTEGER,
  refuse_ticket    INTEGER,
  blacklist_ticket INTEGER,
  total_traffic    INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRAFFIC
  add constraint PK_CM_TRAFFIC primary key (MESSAGE_SEQU)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRAFFIC_AFC_MIN
prompt =================================
prompt
create table ACC_COMMU.CM_TRAFFIC_AFC_MIN
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRAFFIC_AFC_MIN
  add constraint PK_CM_TRAFFIC_AFC_MIN primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRAFFIC_AFC_MIN_TOTAL
prompt =======================================
prompt
create table ACC_COMMU.CM_TRAFFIC_AFC_MIN_TOTAL
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRAFFIC_AFC_MIN_TOTAL
  add constraint PK_CM_TRAFFIC_AFC_MIN_TOTAL primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRAFFIC_DETAIL
prompt ================================
prompt
create table ACC_COMMU.CM_TRAFFIC_DETAIL
(
  message_sequ     CHAR(22) not null,
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  card_main_code   VARCHAR2(2) not null,
  card_sub_code    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic_datetime DATE not null,
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRAFFIC_DETAIL
  add constraint PK_CM_TRAFFIC_DETAIL primary key (MESSAGE_SEQU, LINE_ID, STATION_ID, CARD_MAIN_CODE, CARD_SUB_CODE, FLAG, TRAFFIC_DATETIME)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRAFFIC_TVM
prompt =============================
prompt
create table ACC_COMMU.CM_TRAFFIC_TVM
(
  message_sequ     CHAR(22) not null,
  traffic_datetime DATE,
  line_id          CHAR(2),
  station_id       CHAR(2),
  card_main_code   CHAR(2),
  card_sub_code    CHAR(2),
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRAFFIC_TVM
  add constraint PK_CM_TRAFFIC_TVM primary key (MESSAGE_SEQU)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRD_MAX_HDLTIME
prompt =================================
prompt
create table ACC_COMMU.CM_TRD_MAX_HDLTIME
(
  msg_id       VARCHAR2(10) not null,
  max_hdl_time INTEGER not null,
  remark       VARCHAR2(50) not null
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRD_MAX_HDLTIME
  add constraint CM_TRD_MAX_HDLTIME_PK primary key (MSG_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRD_MONITOR
prompt =============================
prompt
create table ACC_COMMU.CM_TRD_MONITOR
(
  thread_id      VARCHAR2(50) not null,
  thread_name    VARCHAR2(50),
  thread_status  VARCHAR2(2),
  msg_id         VARCHAR2(10),
  msg_name       VARCHAR2(50),
  hdl_time_start VARCHAR2(20),
  hdl_end_time   VARCHAR2(20),
  remark         VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRD_MONITOR
  add constraint CM_TRD_MONITOR_PK primary key (THREAD_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRD_MONITOR_HIS
prompt =================================
prompt
create table ACC_COMMU.CM_TRD_MONITOR_HIS
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
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRD_MONITOR_HIS
  add constraint CM_TRD_MONITOR_HIS_PK primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRD_MSG_DEL
prompt =============================
prompt
create table ACC_COMMU.CM_TRD_MSG_DEL
(
  water_no   NUMBER(18) not null,
  del_date   DATE not null,
  thread_id  VARCHAR2(50) not null,
  queue_type VARCHAR2(1) not null,
  msg_id     VARCHAR2(2) not null,
  message    BLOB not null,
  remark     VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.CM_TRD_MSG_DEL
  add constraint CM_TRD_MSG_DEL primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table CM_TRD_MSG_HANDUP_NUM
prompt ====================================
prompt
create table ACC_COMMU.CM_TRD_MSG_HANDUP_NUM
(
  water_no     NUMBER(18) not null,
  msg_id       VARCHAR2(2) not null,
  hand_up_num  INTEGER not null,
  hand_up_date DATE not null,
  remark       VARCHAR2(50) not null
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRD_MSG_HANDUP_NUM
  add constraint CM_TRD_MSG_HANDUP_NUM_PK primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRD_RESET
prompt ===========================
prompt
create table ACC_COMMU.CM_TRD_RESET
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
  msg_name                 VARCHAR2(100),
  hdl_time_start           VARCHAR2(20),
  hdl_time_end             VARCHAR2(20),
  remark                   VARCHAR2(100),
  message                  VARCHAR2(200)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRF_MIN000001
prompt ===============================
prompt
create table ACC_COMMU.CM_TRF_MIN000001
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRF_MIN000001
  add constraint PK_CM_TRF_MIN000001 primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CM_TRF_MIN002
prompt ============================
prompt
create table ACC_COMMU.CM_TRF_MIN002
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ACC_COMMU.CM_TRF_MIN002
  add constraint PK_CM_TRF_MIN002 primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FM_CONFIG
prompt ========================
prompt
create table ACC_COMMU.FM_CONFIG
(
  config_name  VARCHAR2(100) not null,
  config_value VARCHAR2(100) not null,
  remark       VARCHAR2(200)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.FM_CONFIG
  is '״̬��������ϵͳ����ʱ�����ñ�';
comment on column ACC_COMMU.FM_CONFIG.config_name
  is '������';
comment on column ACC_COMMU.FM_CONFIG.config_value
  is '����ֵ';
comment on column ACC_COMMU.FM_CONFIG.remark
  is '��ע';

prompt
prompt Creating table FM_DEV_MONITOR
prompt =============================
prompt
create table ACC_COMMU.FM_DEV_MONITOR
(
  node_id         CHAR(8) not null,
  description     VARCHAR2(100),
  pos_x           INTEGER not null,
  pos_y           INTEGER not null,
  start_x         INTEGER not null,
  start_y         INTEGER not null,
  end_x           INTEGER not null,
  end_y           INTEGER not null,
  node_type       CHAR(2) not null,
  device_id       VARCHAR2(4),
  dev_type_id     CHAR(2),
  station_id      VARCHAR2(2) not null,
  line_id         VARCHAR2(2) not null,
  image_url       VARCHAR2(50),
  fontsize        CHAR(1),
  id_x            INTEGER,
  id_y            INTEGER,
  image_direction CHAR(2)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.FM_DEV_MONITOR
  is '״̬��������ϵͳ��վͼƬ��';
comment on column ACC_COMMU.FM_DEV_MONITOR.pos_x
  is 'x����';
comment on column ACC_COMMU.FM_DEV_MONITOR.pos_y
  is 'y����';
comment on column ACC_COMMU.FM_DEV_MONITOR.start_x
  is '������ʼx����';
comment on column ACC_COMMU.FM_DEV_MONITOR.start_y
  is '������ʼy����';
comment on column ACC_COMMU.FM_DEV_MONITOR.end_x
  is '��������x����';
comment on column ACC_COMMU.FM_DEV_MONITOR.end_y
  is '��������y����';
comment on column ACC_COMMU.FM_DEV_MONITOR.node_type
  is '���� 01��̬ͼ,02��̬ͼ,03����,04����';
comment on column ACC_COMMU.FM_DEV_MONITOR.device_id
  is '�豸ID';
comment on column ACC_COMMU.FM_DEV_MONITOR.dev_type_id
  is '�豸����';
comment on column ACC_COMMU.FM_DEV_MONITOR.station_id
  is '��վ';
comment on column ACC_COMMU.FM_DEV_MONITOR.line_id
  is '��·';
comment on column ACC_COMMU.FM_DEV_MONITOR.image_url
  is 'ͼƬ·��';
comment on column ACC_COMMU.FM_DEV_MONITOR.fontsize
  is '���ִ�С';
alter table ACC_COMMU.FM_DEV_MONITOR
  add primary key (NODE_ID)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FM_DEV_STATUS_IMAGE
prompt ==================================
prompt
create table ACC_COMMU.FM_DEV_STATUS_IMAGE
(
  dev_type_id     CHAR(2) not null,
  status          CHAR(2) not null,
  image_direction CHAR(2) not null,
  image_url       VARCHAR2(100),
  description     VARCHAR2(100)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.FM_DEV_STATUS_IMAGE
  is '״̬��������ϵͳͼƬ״̬���ձ�';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.dev_type_id
  is '�豸����';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.status
  is '״̬';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.image_direction
  is 'ͼƬ����';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.image_url
  is 'ͼƬ·��';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.description
  is '˵��';
alter table ACC_COMMU.FM_DEV_STATUS_IMAGE
  add constraint PRIMARY_KEY_FM_DSI primary key (DEV_TYPE_ID, STATUS, IMAGE_DIRECTION)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FM_PASSWORD
prompt ==========================
prompt
create table ACC_COMMU.FM_PASSWORD
(
  exit_pass VARCHAR2(100) not null
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table ACC_COMMU.FM_PASSWORD
  is '״̬��������ϵͳ�˳�����';
comment on column ACC_COMMU.FM_PASSWORD.exit_pass
  is '�˳�����';

prompt
prompt Creating table ST_FLOW_NON_RTN
prompt ==============================
prompt
create table ACC_COMMU.ST_FLOW_NON_RTN
(
  water_no     NUMBER(18) not null,
  id           INTEGER,
  apply_bill   VARCHAR2(25),
  apply_action VARCHAR2(1),
  return_flag  VARCHAR2(1),
  return_time  DATE,
  processing   VARCHAR2(1)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
alter table ACC_COMMU.ST_FLOW_NON_RTN
  add constraint PK_ST_FLOW_NON_RETURN primary key (WATER_NO)
  using index 
  tablespace ACC_COMMU
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table T#FM_TRAFFIC_ON_OUT
prompt ==================================
prompt
create global temporary table ACC_COMMU.T#FM_TRAFFIC_ON_OUT
(
  line_id     CHAR(2) not null,
  station_id  CHAR(100) not null,
  traffic_in  INTEGER,
  traffic_out INTEGER
)
on commit preserve rows;
comment on table ACC_COMMU.T#FM_TRAFFIC_ON_OUT
  is '״̬������� ����ͳ����ʱ��';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.line_id
  is '��·';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.station_id
  is '��վ';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.traffic_in
  is '��վ������';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.traffic_out
  is '��վ������';

prompt
prompt Creating table T#ST_RESULTS_NON_RTN
prompt ===================================
prompt
create global temporary table ACC_COMMU.T#ST_RESULTS_NON_RTN
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

prompt
prompt Creating sequence SEQ_CM_DEV_PARA_VER_HIS
prompt =========================================
prompt
create sequence ACC_COMMU.SEQ_CM_DEV_PARA_VER_HIS
minvalue 1
maxvalue 9999999999999999999999999999
start with 77698631
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_EC_CONNECT_LOG
prompt =======================================
prompt
create sequence ACC_COMMU.SEQ_CM_EC_CONNECT_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 43251
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_EC_FTP_LOG
prompt ===================================
prompt
create sequence ACC_COMMU.SEQ_CM_EC_FTP_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 35981
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_EC_LOG
prompt ===============================
prompt
create sequence ACC_COMMU.SEQ_CM_EC_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 304171
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_EC_RECV_SEND_LOG
prompt =========================================
prompt
create sequence ACC_COMMU.SEQ_CM_EC_RECV_SEND_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 301121
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_FILE_RECV
prompt ==================================
prompt
create sequence ACC_COMMU.SEQ_CM_FILE_RECV
minvalue 1
maxvalue 9999999999999999999999999999
start with 3322101
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_LOG_COMMU
prompt ==================================
prompt
create sequence ACC_COMMU.SEQ_CM_LOG_COMMU
minvalue 1
maxvalue 9999999999999999999999999999
start with 93943051
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_LOG_CONNECT
prompt ====================================
prompt
create sequence ACC_COMMU.SEQ_CM_LOG_CONNECT
minvalue 1
maxvalue 9999999999999999999999999999
start with 5674481
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CM_LOG_FTP
prompt ================================
prompt
create sequence ACC_COMMU.SEQ_CM_LOG_FTP
minvalue 1
maxvalue 9999999999999999999999999999
start with 3474421
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CM_LOG_RECV_SEND
prompt ======================================
prompt
create sequence ACC_COMMU.SEQ_CM_LOG_RECV_SEND
minvalue 1
maxvalue 9999999999999999999999999999
start with 88348581
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CM_QUE_MESSAGE
prompt ====================================
prompt
create sequence ACC_COMMU.SEQ_CM_QUE_MESSAGE
minvalue 1
maxvalue 9999999999999999999999999999
start with 103181
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CM_TK_FILE_RECV
prompt =====================================
prompt
create sequence ACC_COMMU.SEQ_CM_TK_FILE_RECV
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_TK_INTERFACE
prompt =====================================
prompt
create sequence ACC_COMMU.SEQ_CM_TK_INTERFACE
minvalue 1
maxvalue 999
start with 71
increment by 1
cache 10
cycle;

prompt
prompt Creating sequence SEQ_CM_TRD_MONITOR_HIS
prompt ========================================
prompt
create sequence ACC_COMMU.SEQ_CM_TRD_MONITOR_HIS
minvalue 1
maxvalue 9999999999999999999999999999
start with 551
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_TRD_MSG_DEL
prompt ====================================
prompt
create sequence ACC_COMMU.SEQ_CM_TRD_MSG_DEL
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_CM_TRD_MSG_HANDUP_NUM
prompt ===========================================
prompt
create sequence ACC_COMMU.SEQ_CM_TRD_MSG_HANDUP_NUM
minvalue 1
maxvalue 9999999999999999999999999999
start with 151
increment by 1
cache 10;

prompt
prompt Creating type STR_SPLIT
prompt =======================
prompt
CREATE OR REPLACE TYPE ACC_COMMU."STR_SPLIT"                                          IS TABLE OF VARCHAR2 (4000)
/

prompt
prompt Creating function RECOVE_PROCE
prompt ==============================
prompt
create or replace function acc_commu.recove_proce(del_time  in varchar2, proc_name in varchar2)
  return varchar2 is
  /** * *  function name :�洢���̻�function ɾ����ָ�����  * *  del_time ����ɾ��ʱ�� * *  proc_name :��ɾ����������  * *  return :�����ؽ��������� * */
  obj_num  number;
  str_proc varchar2(2000);
  str_end  varchar2(2000) := '';
  str_sql  varchar2(2000);
begin
  select obj#
    into obj_num
    from obj$ as of timestamp to_timestamp(del_time, 'yyyy-mm-dd hh24:mi:ss')
   where name = upper(proc_name);
  for i in (select rowid rid, source
              from source$ as of timestamp to_timestamp(del_time, 'yyyy-mm-dd hh24:mi:ss')
             where obj# = obj_num
             order by line) loop
    select source
      into str_proc
      from source$ as of timestamp to_timestamp(del_time, 'yyyy-mm-dd hh24:mi:ss')
     where obj# = obj_num
       and rowid = i.rid
     order by line;
    str_end := str_end || str_proc;
  end loop;
  str_sql := 'create or replace ' || str_end;
  return str_sql;
exception
  when others then
    dbms_output.put_line(sqlcode || sqlerrm);
    return null;
end recove_proce;
/

prompt
prompt Creating function UP_CM_HISTORY_NUM
prompt ===================================
prompt
create or replace function acc_commu.up_cm_history_num(in_num number) --����ĵ�ǰ���
  --�� �� ���� up_cm_history_num
  --�������������ݴ�������к�ֵ������һ�¸����к�ֵ�����紫��1������002������22����023������123����124
  --��   ��: �������к�ֵ
  --��   ����3λ�����к�ֵ
  --����ֵ    ��
  --�����ߣ�  �Ž���
  --�������ڣ�20131030

 return varchar2 is
  v_temp_num number;
begin
  v_temp_num := in_num + 1;

  if in_num < 0 then
    return '001';
  elsif in_num < 10 then
    return '00' || v_temp_num;
  elsif in_num < 100 then
    return '0' || v_temp_num;
  elsif in_num < 999 then
    return '' || v_temp_num;
  else
    return '001';
  end if;

end up_cm_history_num;
/

prompt
prompt Creating function UP_DM_CM_SPLITSTR
prompt ===================================
prompt
CREATE OR REPLACE FUNCTION ACC_COMMU.up_dm_cm_splitstr(p_string    IN VARCHAR2,
                                              p_delimiter IN VARCHAR2)
  RETURN str_split
  PIPELINED AS
  v_length NUMBER := LENGTH(p_string);
  v_start  NUMBER := 1;
  v_index  NUMBER;
BEGIN
  WHILE (v_start <= v_length) LOOP
    v_index := INSTR(p_string, p_delimiter, v_start);

    IF v_index = 0 THEN
      PIPE ROW(SUBSTR(p_string, v_start));
      v_start := v_length + 1;
    ELSE
      PIPE ROW(SUBSTR(p_string, v_start, v_index - v_start));
      v_start := v_index + 1;
    END IF;
  END LOOP;

  RETURN;
END up_dm_cm_splitstr;
/

prompt
prompt Creating function UP_DM_CM_TABLENO
prompt ==================================
prompt
create or replace function acc_commu.up_dm_cm_tableno(in_num number) --����ĵ�ǰ���
  --�� �� ���� up_dms_tk_tableno
  --�������������ݴ�������к�ֵ������һ�¸����к�ֵ�����紫��1������0002������22����0023������123����0124
  --��   ��: �������к�ֵ
  --��   ����4λ�����к�ֵ
  --����ֵ    ��
  --�����ߣ�  �Ž���
  --�������ڣ�20131021

 return varchar2 is
  v_temp_num number;
begin
  v_temp_num := in_num + 1;

  if in_num < 0 then
    return '001';
  elsif in_num < 10 then
    return '00' || v_temp_num;
  elsif in_num < 100 then
    return '0' || v_temp_num;
  elsif in_num < 999 then
    return '' || v_temp_num;
  else
    return '001';
  end if;

end up_dm_cm_tableno;
/

prompt
prompt Creating procedure UP_DM_CM_MV_LOG
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_mv_log(origin_table_name    in varchar2,
                                            dest_table_name      in varchar2,
                                            begin_clear_datetime in date,
                                            end_clear_datetime   in date,
                                            spent_time           in NUMBER,
                                            clear_recd_count     in NUMBER,
                                            err_discribe         in varchar2,
                                            v_sql                in varchar2) as

  ---------------------------------------------------------------------------------
  --��������  up_dm_cm_mv_log
  --������������¼Ǩ������ʱ����־
  --������� origin_table_name  ԭ����
  --������� dest_table_name   �ֱ����
  --������� begin_clear_datetime  ������ʼʱ��
  --������� end_clear_datetimein  ��������ʱ��
  --������� spent_time  ��������ʱ��
  --������� clear_recd_count  �����¼��
  --������� err_discribe  ��������
  --�����ߣ��Ž���
  --�������ڣ�20131028
  -------------------------------------------------------------------------------
begin
  insert into acc_commu.cm_log_clear_table
    (origin_table_name,
     dest_table_name,
     begin_clear_datetime,
     end_clear_datetime,
     spent_time,
     clear_recd_count,
     err_discribe,
     sql_label)
  values
    (origin_table_name,
     dest_table_name,
     to_char(begin_clear_datetime, 'yyyy-mm-dd hh24:mi:ss'),
     to_char(end_clear_datetime, 'yyyy-mm-dd hh24:mi:ss'),
     spent_time,
     clear_recd_count,
     err_discribe,
     v_sql);
  commit;
end up_dm_cm_mv_log;
/

prompt
prompt Creating procedure UP_DM_CM_HIS_CLEAR
prompt =====================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_clear(out_retresult OUT INTEGER, --���ؽ��
                                               out_msg       OUT VARCHAR2 --������Ϣ
                                               )
---------------------------------------------------------------------------------
  --��������  up_dm_cm_his_clear
  --�������������ݽ���ϵͳ--��־����
  --�������  ��out_retresult ģ�鷵��ֵ
  --����ֵ    ��0�������ɹ�������������ʧ�ܣ�
  --�����ߣ��Ž���
  --�������ڣ�20131123
  --�޸����ڣ�20150514 clear_flag ��Ϊ0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
  --cm_traffic_afc_min���traffic_datetime�ֶ�ΪVARCHAR2(12)���ͣ���Ҫ���������Date�����ֶ� ���ִ���
  -------------------------------------------------------------------------------
 AS
  v_sql             varchar2(2048);
  v_count           integer; --��ʱͳ�Ʊ���
  v_begin_time      date; --����ʼʱ��
  v_clear_time      date; --����ʼʱ��
  origin_table_name varchar2(30); --�м�����
  -- table_columns     varchar2(1000); --�м����ֶ�
  v_cost_seconds int; --ͳ��ʱ��
  v_msg          varchar2(255); --ע��

  v_clear_time_str  varchar2(40); --����ʼʱ��
  v_date_str     VARCHAR2(20);  --20150529 modify by mqf

BEGIN
  begin
    out_msg       := '��������';
    out_retresult := sqlcode;
    --v_begin_time  := sysdate - 30;
  v_date_str := to_char(sysdate - 30,'yyyy-mm-dd hh24:mi:ss');
    --v_sql         := ' delete from acc_commu.cm_log_clear_table where  begin_clear_datetime<''' ||
    --                 v_begin_time || '''';
  v_sql         := ' delete from acc_commu.cm_log_clear_table where begin_clear_datetime< ''' ||
                   v_date_str || ''''; --20150529 modify by mqf

    execute immediate v_sql;

    declare
      cursor scc_table is
        select t.origin_table_name tmp_origin_table,
               t.keep_days         tmp_keep_days,
               t.clear_flag        tmp_clear_flag,
               -- t.table_columns     tmp_table_columns,
               t.date_type tmp_date_type
          from acc_commu.cm_cfg_clear_table t
         where t.clear_flag = 0; --20150514 modify by mqf clear_flag 1��Ϊ0�� 0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
    begin

      for i_cur in scc_table loop

        origin_table_name := trim(i_cur.tmp_origin_table);
        --table_columns     := trim(i_cur.tmp_table_columns);
        v_begin_time := sysdate;

    --20150525 modify by mqf cm_traffic_afc_min���traffic_datetime�ֶ�ΪVARCHAR2(12)���ͣ���Ҫ���������Date�����ֶ� ���ִ���
    if origin_table_name = 'cm_traffic_afc_min' then
      v_clear_time_str := to_char(sysdate - i_cur.tmp_keep_days, 'yyyymmdd') || '2359';
    else
      v_clear_time := sysdate - i_cur.tmp_keep_days;
    end if;
        --20150525 modify by mqf
    if origin_table_name = 'cm_traffic_afc_min' then
      v_sql        := 'select count(*)  from acc_commu.' || origin_table_name ||
                        ' where ' || i_cur.tmp_date_type || ' <''' ||
                        v_clear_time_str || '''';
    else
      v_sql        := 'select count(*)  from acc_commu.' || origin_table_name ||
              ' where ' || i_cur.tmp_date_type || ' <''' ||
              v_clear_time || '''';
    end if;
        execute immediate v_sql
          into v_count;

        if v_count = 0 then
          continue;
        end if;

        begin
          --20150525 modify by mqf
      if origin_table_name = 'cm_traffic_afc_min' then
        v_sql := ' delete from acc_commu.' || origin_table_name || ' where ' ||
             i_cur.tmp_date_type || ' <''' || v_clear_time_str || '''';
          else
        v_sql := ' delete from acc_commu.' || origin_table_name || ' where ' ||
               i_cur.tmp_date_type || ' <''' || v_clear_time || '''';
          end if;
          execute immediate v_sql;
        EXCEPTION
          when others then
            begin
              rollback;
              out_retresult  := SQLCODE;
              out_msg        := origin_table_name || 'ɾ�����ݴ���,�������:' ||
                                SQLERRM || ',�����ڵ�[' ||
                                dbms_utility.format_error_backtrace() || ']��';
              v_cost_seconds := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);
              acc_commu.up_dm_cm_mv_log(origin_table_name,
                              '',
                              v_begin_time,
                              sysdate,
                              v_cost_seconds,
                              0,
                              out_msg,
                              v_sql);
              return;
            end;

        end;
        --��¼������־
        acc_commu.up_dm_cm_mv_log(origin_table_name,
                        '',
                        v_begin_time,
                        sysdate,
                        v_cost_seconds,
                        v_count,
                        out_msg,
                        v_sql);

      end loop;

    end;

  end;
  commit;
exception
  when OTHERS THEN
    begin
      ROLLBACK;
      out_msg       := v_msg || '����' || sqlerrm || ',�����ڵ�[' ||
                       dbms_utility.format_error_backtrace() || ']��';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_clear;
/

prompt
prompt Creating procedure UP_DM_CM_HIS_CREATE
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_create(out_retResult OUT INTEGER, --���ؽ��
                                                          out_msg       OUT VARCHAR2 --������Ϣ
                                                          )
---------------------------------------------------------------------------------
  --��������  up_dm_cm_his_create
  --�����������������ݽ���ϵͳ����־�����ʷ�ֱ�
  --�������  ��out_retResult ģ�鷵��ֵ out_msg ������Ϣ
  --����ֵ    ��0�������ɹ�������������ʧ�ܣ�
  --�����ߣ�  �Ž���
  --�������ڣ�201301105
  --�޸ģ�20150511
  --clear_flag ��Ϊ0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
  -------------------------------------------------------------------------------
 AS

  v_msg VARCHAR(50);
  --v_sql              varchar(5000);
  --v_new_begin date; --�µĿ�ʼʱ��
  v_new_begin varchar2(40); --�µĽ���ʱ��
  --v_new_end      varchar2(40); --�µĽ���ʱ��
  v_begin_time   date;
  v_min_date     varchar2(40); --��Сʱ��
  v_count        number(18); --��ʱͳ�Ʊ���
  v_count2       number(18); --��ʱͳ�Ʊ���
  v_flag         number(1); --����001�ı�־��0������
  v_time         varchar2(20); --��ʱʱ�����
  v_temp_sql     varchar2(4000); --��ʱsql���
  v_table_sql    varchar2(4000); --������ʱsql���
  v_temp_num     varchar2(6); --�ֱ����к�
  new_table_name varchar2(40); --�ֱ����
BEGIN
  begin
    declare
      cursor scc_table is
        select t.origin_table_name tmp_origin_table,
               t.create_sql        tmp_create_sql,
               t.keep_days         tmp_keep_days,
               t.ab_name           tmp_ab_name,
               t.date_type         tmp_date_type
          from acc_commu.cm_cfg_clear_table t
         where t.clear_flag = 1; --20150511 clear_flag ��Ϊ 1, 0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
    begin
      for i_cur in scc_table loop
        begin
          v_flag := 2;
          --���ԭʼ��û�����ݣ��򲻽����ֱ�
          v_temp_sql := 'select count(*) from  acc_commu.' || i_cur.tmp_origin_table;
          execute immediate v_temp_sql
            into v_count;
          if v_count = 0 then
            continue;
          end if;
        end;
        begin
          select count(*)
            into v_count
            from acc_commu.cm_idx_history
           where his_table like i_cur.tmp_ab_name || '_%';
        end;
        begin
          --��Ϊ��Щԭ����̫�����������ɵķֱ�����Զ���ı����������˳���
          new_table_name := i_cur.tmp_ab_name || '000001';

          v_temp_sql := 'select count(*) from user_objects where object_name = upper (''' ||
                        new_table_name || ''')';
          execute immediate v_temp_sql
            into v_count2;
        end;
        begin
          if v_count = 0 then
            --������û�ж�Ӧ�ֱ�ļ�¼
            if v_count2 > 0 then
              --�ֱ����������
              v_temp_sql := 'drop table acc_commu.' || new_table_name;
              execute immediate v_temp_sql; --ɾ��0001�ֱ��

            end if;
            v_flag := 0;

          else
            ----�������ж�Ӧ�ֱ�ļ�¼
            if v_count2 = 0 then
              --�ֱ�����������
              v_flag := 0;
            end if;
          end if;
        end;
        if v_flag = 0 then
          --�Ƚ����ٳ�ʼ��
          begin
            --��001��
            begin
              --��ȡ�������
              --�滻���� �滻���� �滻����
              select regexp_replace(i_cur.tmp_create_sql,
                                    '%s',
                                    new_table_name)
                into v_temp_sql
                from dual;

              --ÿ����������к��н�������������Ϣ�����ԣ��ָ�
              declare
                cursor sql_cur is
                  select *
                    from table(acc_commu.up_dm_cm_splitstr(v_temp_sql, ';'));
              begin
                for t_cur in sql_cur loop
                  v_table_sql := t_cur.column_value;

                  if trim(v_table_sql) is not null and v_table_sql <> ' ' then
                    execute immediate v_table_sql; --����0001�ֱ���صĲ���
                  end if;
                end loop;
              EXCEPTION
                when others then
                  begin
                    rollback;
                    out_retresult := SQLCODE;
                    out_msg       := new_table_name || '������ṹ����:' ||
                                     SQLERRM || ',�����ڵ�[' ||
                                     dbms_utility.format_error_backtrace() || ']��';
                    v_count2      := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);
                    acc_commu.up_dm_cm_mv_log(i_cur.tmp_origin_table,
                                              new_table_name,

                                              v_begin_time,
                                              sysdate,
                                              v_count2,
                                              0,
                                              out_msg,
                                              v_table_sql);
                    continue;
                  end;

              end;
            end;

      --20150512 add by mqf ������־
      acc_commu.up_dm_cm_mv_log(i_cur.tmp_origin_table,
                                              new_table_name,

                                              sysdate,
                                              sysdate,
                                              0,
                                              0,
                                              '�ɹ�����' || new_table_name || '��',
                                              'create table ' || new_table_name || '...');

            --������С�������ˮ�š���С��Ӫ�գ������Ӫ��
            v_time := to_char(sysdate - i_cur.tmp_keep_days, 'yyyymmdd') ||
                      '0000';

            v_temp_sql := 'select min(' || i_cur.tmp_date_type || ')' ||
                          ' from acc_commu.' || i_cur.tmp_origin_table || ' t' ||
                          ' where ' || i_cur.tmp_date_type || ' <= ''' ||
                          v_time || '''';

            execute immediate v_temp_sql
              into v_min_date;
            v_new_begin := substr(v_min_date, 0, 8) || '0000';
            --v_new_end   := substr(v_min_date, 0, 8) || '2359';

            select count(*)
              into v_count
              from acc_commu.cm_idx_history
             where his_table = new_table_name;
            if v_count = 0 then
              insert into acc_commu.cm_idx_history
                (his_table, origin_table_name, begin_date, recd_count)
              values
                (new_table_name,
                 i_cur.tmp_origin_table,
                 v_new_begin,
                 --v_new_end,
                 0);
            end if;

          end;
        end if;
      end loop;
    end;
  end;

  ---------------����Ϊ������001��

  begin
    declare
      cursor cu_table is
        select regexp_substr(a.his_table, '.{1,6}$') tmp_i_list_no,
               a.recd_count tmp_recd_count,
               to_date(substr(a.end_date, 0, 8), 'yyyymmdd') + 1 tmp_max_date,
               a.origin_table_name tmp_origin_table,
               b.divide_recd_count tmp_divide_recd,
               b.create_sql tmp_create_sql,
               b.ab_name tmp_ab_name
          from acc_commu.cm_idx_history a, acc_commu.cm_cfg_clear_table b
         where a.origin_table_name = b.origin_table_name
           and b.clear_flag = 1 --20150511 clear_flag ��Ϊ 1, 0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
           and a.his_table in (select max(his_table)
                                 from acc_commu.cm_idx_history
                                group by origin_table_name);
    begin
      for my_cur in cu_table loop

        begin
          begin
            if my_cur.tmp_recd_count >= my_cur.tmp_divide_recd then
              v_new_begin := substr(to_char(my_cur.tmp_max_date, 'yyyymmdd'),
                                    0,
                                    8) || '0000';
              --v_new_end   := substr(my_cur.tmp_max_date, 0, 8) || '2359';
              --�ﵽ���ñ��е�����¼������Ҫ����
              --��һ�ֱ�����к�
              v_temp_num     := acc_commu.up_dm_cm_tableno(my_cur.tmp_i_list_no);
              new_table_name := my_cur.tmp_ab_name || v_temp_num;
              begin
                v_temp_sql := 'select count(*) from user_objects where object_name = upper (''' ||
                              new_table_name || ''')';
                execute immediate v_temp_sql
                  into v_count2;
                if v_count2 > 0 then
                  --ɾ���ñ�
                  v_temp_sql := 'drop table  ' || new_table_name;
                  execute immediate v_temp_sql;
                end if;
              end;
              --��ȡ�������
              --�滻���� �滻���� �滻����
              select REGEXP_REPLACE(my_cur.tmp_create_sql,
                                    '%s',
                                    new_table_name)
                into v_temp_sql
                from dual;

              --ÿ����������к��н�������������Ϣ�����ԣ��ָ�
              declare
                cursor sql_cur is
                  select *
                    from table(acc_commu.up_dm_cm_splitstr(v_temp_sql, ';'));
              begin
                for t_cur in sql_cur loop
                  v_begin_time := sysdate;
                  v_table_sql  := t_cur.column_value;
                  if trim(v_table_sql) is not null and v_table_sql <> ' ' then
                    begin
                      execute immediate v_table_sql; --����001�ֱ���صĲ���
                    EXCEPTION
                      when others then
                        begin
                          rollback;
                          out_retresult := SQLCODE;
                          out_msg       := new_table_name || '������ṹ����:' ||
                                           SQLERRM || ',�����ڵ�[' ||
                                           dbms_utility.format_error_backtrace() || ']��';
                          v_count2      := ROUND(TO_NUMBER(sysdate -
                                                           v_begin_time) * 24 * 60 * 60);
                          acc_commu.up_dm_cm_mv_log(my_cur.tmp_origin_table,
                                          new_table_name,

                                          v_begin_time,
                                          sysdate,
                                          v_count2,
                                          0,
                                          out_msg,
                                          v_table_sql);
                          continue;
                        end;
                    end;
                  end if;
                end loop;
              end;

        --20150512 add by mqf ������־
        acc_commu.up_dm_cm_mv_log(my_cur.tmp_origin_table,
                        new_table_name,

                        sysdate,
                        sysdate,
                        0,
                        0,
                        '�ɹ�����' || new_table_name || '��',
                        'create table ' || new_table_name || '...');

              begin

                begin
                  select count(*)
                    into v_count
                    from acc_commu.cm_idx_history
                   where his_table = new_table_name;
                  if v_count = 0 then
                    insert into acc_commu.cm_idx_history
                      (his_table,
                       origin_table_name,
                       begin_date,
                       recd_count)
                    values
                      (new_table_name,
                       my_cur.tmp_origin_table,
                       v_new_begin,
                       0);

                  end if;

                end;
              end; --if�ϵ�begin

            end if;
          end;

        end; --loop�еĵ�2��begin
      end loop;
    end; -- for֮ǰ��begin
    out_msg       := '����ɹ�';
    out_retResult := sqlcode;
    commit;
  end; --declareǰ��begin

exception
  when OTHERS THEN
    begin
      ROLLBACK;
      out_msg       := v_msg || '����' || sqlerrm || ',�����ڵ�[' ||
                       dbms_utility.format_error_backtrace() || ']��';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_create;
/

prompt
prompt Creating procedure UP_DM_CM_HIS_MV
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_mv(out_retresult OUT INTEGER, --���ؽ��
                                                      out_msg       OUT VARCHAR2 --������Ϣ
                                                      )
---------------------------------------------------------------------------------
  --��������  up_dm_cm_his_mv
  --�������������ݽ���ϵͳ--���ݱ�����ʷ��
  --�������  ��out_retresult ģ�鷵��ֵ
  --����ֵ    ��0�������ɹ�������������ʧ�ܣ�
  --�����ߣ��Ž���
  --�������ڣ�20131104
  --�޸����ڣ� 20150424 by mqf
  --�޸����ݣ� �����ڱ�������¼�����ڷֱ�����¼����ֹͣǨ�����ݣ��½��ֱ���Ǩ�����ݡ�
             --�����������ʱ���ѻ������ļ�¼���뵽��һ���ֱ������
       --clear_flag ��Ϊ0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
  --�޸����ڣ�20160912 modify by mqf
  --�޸����ݣ�1.�ر��α�
  -------------------------------------------------------------------------------
 AS
  v_sql       varchar2(2048);
  v_whereSql  varchar2(2048);
  v_max_table varchar2(40); --������ʷ��
  c_begin     varchar2(40); --��ǰ���Ӧ�Ŀ�ʼʱ��
  c_end       varchar2(40); --��ǰ���Ӧ�Ľ���ʱ��
  --v_new_begin       date;
  --v_new_end         date;
  v_time            varchar2(10);
  v_count           integer; --��ʱͳ�Ʊ���
  v_table_columns   varchar2(2048); --���ֶ�
  v_begin_time      date; --����ʼʱ��
  origin_table_name varchar2(30); --�м�����
  table_columns     varchar2(1000); --�м����ֶ�
  v_cost_seconds    int; --ͳ��ʱ��
  v_msg             varchar2(255); --ע��

  --20140422 add by mqf
  divide_recd_count integer; --���ñ�����¼��
  v_recd_count  integer; --�������Ѹ��¼�¼��
  v_rowcount  integer; --���¼�¼��
  v_date_type_value varchar2(40); --����
  TYPE MYCURSOR IS REF CURSOR;  --�����α�����
  cur_date_type MYCURSOR;       --�����α�
  v_date_begin     varchar2(40); --�����Ŀ�ʼʱ��
  v_date_end     varchar2(40); --�����Ľ���ʱ��
  v_date_whereSql  varchar2(2048);


BEGIN
  begin
    out_msg       := '�ɹ�Ǩ������';
    out_retresult := sqlcode;
    declare
      cursor scc_table is
        select t.origin_table_name tmp_origin_table,
               t.keep_days         tmp_keep_days,
               t.clear_flag        tmp_clear_flag,
               t.table_columns     tmp_table_columns,
               t.date_type         tmp_date_type,
         t.divide_recd_count tmp_divide_recd_count
          from acc_commu.cm_cfg_clear_table t
         where t.clear_flag = 1; --20150511 clear_flag ��Ϊ 1, 0��ֻɾ�����ݣ�1��Ǩ�����ݵ��ֱ�
    begin

      for i_cur in scc_table loop

        origin_table_name := trim(i_cur.tmp_origin_table);
        table_columns     := trim(i_cur.tmp_table_columns);
    --20150422 add by mqf
    divide_recd_count := trim(i_cur.tmp_divide_recd_count);
        v_begin_time      := sysdate;
        --�����־Ϊ1ʱ�������м������ʷ��
        --�����ñ��� clear_table_config ��station_entry_rpt �� clear_flag ����Ϊ-1

        --����
        v_sql := 'select max(his_table) from acc_commu.cm_idx_history where origin_table_name = ''' ||
                 origin_table_name || '''';
        execute immediate v_sql
          into v_max_table;
    if (v_max_table is null) or (v_max_table = '') then --20150511 add by mqf
            continue;
        end if;
        v_table_columns := table_columns;

        --��������ʷ��������
        select begin_date,recd_count
          into c_begin,v_recd_count
          from acc_commu.cm_idx_history
         where his_table = v_max_table;
        --v_new_begin := to_date(c_begin, 'yyyy-mm-dd hh24:mi:ss');

    --��������Ǩ�Ƽ�¼���ڻ���ڷֱ�����¼����������һ��ѭ�� 20150520 modify by mqf
    if v_recd_count>=divide_recd_count then
      continue;
    end if;

        v_time := to_char(v_begin_time - i_cur.tmp_keep_days, 'yyyymmdd');
        --���µ���������
        c_end := v_time || '2359';

        --v_new_end := to_date(c_end, 'yyyy-mm-dd hh24:mi:ss');
        v_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
                      i_cur.tmp_date_type || ' <=''' || c_end || ''' and ' ||
                      i_cur.tmp_date_type || '>=''' || c_begin || '''';
        begin
          --����st_idx_rpt_history����Ϣ
          v_sql := 'select count(*) ' || v_whereSql;
          execute immediate v_sql
            into v_count;
          if v_count = 0 then
            continue;
          end if;
        end;
        --��������ʷ��������
        v_sql := 'lock table acc_commu.' || v_max_table || ' in exclusive mode';
        execute immediate v_sql;

    --20150422 add by mqf �����ڱ����������������ʱ���ѻ������ļ�¼���뵽��һ���ֱ��С�
    v_sql := 'select distinct substr(' || i_cur.tmp_date_type  || ',0,8) ' ||
         v_whereSql || ' order by substr(' || i_cur.tmp_date_type || ',0,8) ';
    --���¼�¼����ʼΪ0
    v_rowcount := 0;

    open cur_date_type for v_sql;
        loop
      fetch cur_date_type into v_date_type_value;
        exit when cur_date_type%notfound;

      --�����ֱ�����¼����������ѭ��
        if (v_recd_count+v_rowcount>=divide_recd_count) then
          exit;
        end if;

      --�����ڵ�������
      begin
        v_date_begin := substr(v_date_type_value,0,8) || '0000';
        v_date_end := substr(v_date_type_value,0,8) || '2359';
        v_date_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
                      i_cur.tmp_date_type || ' <=''' || v_date_end || ''' and ' ||
                      i_cur.tmp_date_type || '>=''' || v_date_begin || '''';

        v_sql := ' insert into acc_commu.' || v_max_table || ' select ' ||
             v_table_columns || v_date_whereSql;
        execute immediate v_sql;
        --�ۼӸ��¼�¼��
        v_rowcount := v_rowcount + sql%rowcount;



      EXCEPTION
        when others then
        begin
          rollback;
          out_retresult  := SQLCODE;
          out_msg        := origin_table_name || '������ʷ�����,�������:' ||
                  SQLERRM || ',�����ڵ�[' ||
                  dbms_utility.format_error_backtrace() || ']��';
          v_cost_seconds := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);

          acc_commu.up_dm_cm_mv_log(origin_table_name,
                      v_max_table,
                      v_begin_time,
                      sysdate,
                      v_cost_seconds,
                      0,
                      out_msg,
                      v_sql);
          return;
        end;
      end;

    end loop;
  close cur_date_type; --20160912 add by mqf �ر��α�


    --20150422 add by mqf v_whereSql����ɾ����¼
    v_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
            i_cur.tmp_date_type || ' <=''' || v_date_end || ''' and ' ||
            i_cur.tmp_date_type || '>=''' || c_begin || '''';

    c_end := v_date_end;
    v_count := v_rowcount;


    --д����־��¼��clear_table_log
    out_msg := '��' || origin_table_name || '���е���' || v_max_table ||
           '��ʷ������ݳɹ�����¼����' || v_count;
    --20150422 add by mqf
    v_cost_seconds := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);
    acc_commu.up_dm_cm_mv_log(origin_table_name,
                  v_max_table,
                  v_begin_time,
                  sysdate,
                  v_cost_seconds,
                  v_count,
                  out_msg,
                  v_sql); --20140424 modify by mqf out_msg��Ϊv_sql

    if v_count>0 then --20150511 add by mqf ��¼����0�Ÿ���������ɾ��Դ������
      begin

          update acc_commu.cm_idx_history
           set recd_count = recd_count + v_count, end_date = c_end
           where his_table = v_max_table;

      end;

      --ɾ���Ѿ�������ʷ�������
      v_begin_time := sysdate;
      begin
        v_sql := ' delete  ' || v_whereSql;
        execute immediate v_sql;

      end;
      out_msg        := 'ɾ��' || origin_table_name || '���е���' ||
                v_max_table || '��ʷ������ݳɹ�';
      v_cost_seconds := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);
      acc_commu.up_dm_cm_mv_log(origin_table_name,
                    v_max_table,
                    v_begin_time,
                    sysdate,
                    v_cost_seconds,
                    v_count,
                    out_msg,
                    v_sql);
        end if;

      end loop;

    end;

  end;
  commit; --20150424 add by mqf
exception
  when OTHERS THEN
    begin
      ROLLBACK;
      out_msg       := v_msg || '����' || sqlerrm || ',�����ڵ�[' ||
                       dbms_utility.format_error_backtrace() || ']��';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_mv;
/

prompt
prompt Creating procedure UP_DM_CM_TABLE_DROP
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_table_drop(out_retresult OUT INTEGER, --���ؽ��
                                                out_msg       OUT VARCHAR2 --������Ϣ
                                                )
---------------------------------------------------------------------------------
  --��������  up_dm_cm_table_drop
  --�������������ݽ���ϵͳ--�ֱ���������¼������������ǰ����
  --�������  ��out_retresult ģ�鷵��ֵ
  --����ֵ    ��0�������ɹ�������������ʧ�ܣ�
  --�����ߣ��Ž���
  --�������ڣ�20131123
  -------------------------------------------------------------------------------
 AS
  v_sql               varchar2(2048);
  v_his_table         varchar2(30); --��Ҫɾ������ʷ����
  v_origin_table_name varchar2(30);
  v_msg               varchar2(255); --ע��
BEGIN
  begin
  
    v_sql := ' delete from cm_log_clear_table ';
    execute immediate v_sql;
    declare
      cursor scc_table is
        select t.his_table         tmp_his_table,
               t.origin_table_name tmp_origin_table_name
          from cm_idx_history t;
    begin
      for i_cur in scc_table loop
        v_his_table         := i_cur.tmp_his_table;
        v_origin_table_name := trim(i_cur.tmp_origin_table_name);
        begin
          v_sql := ' drop table ' || v_his_table;
          execute immediate v_sql;
        EXCEPTION
          when others then
            begin
              NULL;
            end;
        end;
        v_sql := ' delete from  cm_idx_history where his_table=''' ||
                 v_his_table || '''';
        execute immediate v_sql;
      end loop;
    end;
    out_msg       := '��������';
    out_retResult := sqlcode;
  end;
  commit;
exception
  when OTHERS THEN
    begin
    
      out_msg       := v_msg || '����' || sqlerrm || ',�����ڵ�[' ||
                       dbms_utility.format_error_backtrace() || ']��';
      out_retResult := sqlcode;
      return;
    end;  
END up_dm_cm_table_drop;
/

prompt
prompt Creating procedure UP_FM_EXIT_PASS
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_EXIT_PASS(V_PASS          IN VARCHAR2, --��������
                                            P_RESULT        OUT VARCHAR2) --���ؽ������

---------------------------------------------------------------------------------
--������:  UP_FM_EXIT_PASS
--ϵͳ��״̬��������ϵͳ
--����: �����˳�����
--���:P_RESULT ���ؽ������
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������

BEGIN

    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;
    --���ݱ�Ϊ��ʱ
    IF TMP_COUNT=0 THEN
        P_RESULT := '1';
        RETURN;
    ELSE
       --���ݱ���ڸ�����
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_PASS;
       IF TMP_COUNT >=1 THEN
          P_RESULT := '0';
          RETURN;
       ELSE
          --���벻��ȷ
          P_RESULT := '2';
          RETURN;
       END IF;
     END IF;

END UP_FM_EXIT_PASS;
/

prompt
prompt Creating procedure UP_FM_FLOW_HOUR
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_HOUR(V_LINE_STATION          IN VARCHAR2, --��·����վ
                                            V_TRAFFIC_TIME          IN VARCHAR2, --��ѯʱ��
                                            INFO_CUR                OUT SYS_REFCURSOR) --���ؽ����

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_HOUR
--ϵͳ��״̬��������ϵͳ
--��������:  ��������·����վ��ǰ������
--�������:   line_id��·ID,station_id ��վID
--����ֵ���ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
--ʹ��:��������վСʱ�������
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_STATION_ID    VARCHAR2(10); --��վ
  TMP_SHH           VARCHAR2(4); --��Ӫʱ�䣨ʱ�֣�
  TMP_PHH           VARCHAR2(4); --����ʱ�䣨ʱ�֣�
  TMP_TRAFFIC_TIME  VARCHAR2(8); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��
  P_TRAFFIC_IN         INTEGER; --��վ����
  P_TRAFFIC_OUT        INTEGER; --��վ����

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --��·
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --��վ
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --��Ӫʱ��
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --����ʱ�䣨ʱ�֣�
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--��ǰʱ�������Ӫʱ��

     --��ʼʱ��=��ǰ����+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--��ǰʱ��С����Ӫʱ��

     --��ʼʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --ѡ��վʱ��ͳ�Ƴ�վ����
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----ȡ�õ�ǰ��·��վ�����п���
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,2),t.line_id,t.station_id,flag;

      END;

  --δѡ��վʱ��ͳ����·����
  ELSE
      BEGIN

          --������·(ȫ����·��������)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                     FROM acc_commu.cm_traffic_afc_min t
                     WHERE to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     GROUP BY substr(t.traffic_datetime,9,2),flag;


          --ĳ����·��������
          ELSE
              OPEN INFO_CUR FOR

              SELECT t.line_id,substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                     FROM acc_commu.cm_traffic_afc_min t
                     WHERE t.line_id = TMP_LINE_ID
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     GROUP BY substr(t.traffic_datetime,9,2),t.line_id,flag;

          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_HOUR;
/

prompt
prompt Creating procedure UP_FM_FLOW_MIN
prompt =================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_MIN(V_LINE_STATION          IN VARCHAR2, --��·����վ
                                           V_TRAFFIC_TIME          IN VARCHAR2, --��ѯʱ��
                                           INFO_CUR                OUT SYS_REFCURSOR) --���ؽ����

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_MIN
--ϵͳ��״̬��������ϵͳ
--��������:  ������վ5���ӿ�����
--�������:   line_id��·ID,station_id ��վID
--����ֵ���ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_STATION_ID    VARCHAR2(10); --��վ
  TMP_TRAFFIC_TIME  VARCHAR2(10); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��
  P_TRAFFIC_IN         INTEGER; --��վ����
  P_TRAFFIC_OUT        INTEGER; --��վ����

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --��·
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --��վ
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2)||substr(V_TRAFFIC_TIME,12,2);
  --��ʼʱ��
  TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || '00','yyyyMMddHH24miss')-1/24;
  --��ֹʱ��
  TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || '59','yyyyMMddHH24miss');


  --ѡ��վʱ��ͳ�Ƴ�վ����
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----ȡ�õ�ǰ��·��վ�����п���
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,4),flag,t.line_id,t.station_id;

      END;

  --δѡ��վʱ��ͳ����·����
  ELSE
      BEGIN

          --������·(ȫ����·��������)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT s.belong_line_id line_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s
                 WHERE t.line_id = s.line_id
                     AND t.station_id = s.station_id
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY s.belong_line_id,substr(t.traffic_datetime,9,4);


          --ĳ����·��������
          ELSE
              OPEN INFO_CUR FOR

              SELECT s.belong_line_id line_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s
                 WHERE t.line_id = s.line_id
                     AND t.station_id = s.station_id
                     AND s.belong_line_id = TMP_LINE_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,4),flag,s.belong_line_id;

          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_MIN;
/

prompt
prompt Creating procedure UP_FM_FLOW_NET
prompt =================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_NET(V_LINE_STATION          IN VARCHAR2, --��·����վ
                                           V_TRAFFIC_TIME          IN VARCHAR2, --��ѯʱ��
                                           INFO_CUR                OUT SYS_REFCURSOR) --���ؽ����

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_NET
--ϵͳ��״̬��������ϵͳ
--��������:  ��������·����վ��ǰ������
--�������:   line_id��·ID,station_id ��վID
--����ֵ���ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
--ʹ��:��������վСʱ�������
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_STATION_ID    VARCHAR2(10); --��վ
  TMP_SHH           VARCHAR2(4); --��Ӫʱ�䣨ʱ�֣�
  TMP_PHH           VARCHAR2(4); --����ʱ�䣨ʱ�֣�
  TMP_TRAFFIC_TIME  VARCHAR2(8); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��
  P_TRAFFIC_IN         INTEGER; --��վ����
  P_TRAFFIC_OUT        INTEGER; --��վ����

BEGIN

  --��ʼ��
  P_TRAFFIC_IN := 0;
  P_TRAFFIC_OUT := 0;

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --��·
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --��վ
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --��Ӫʱ��
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --����ʱ�䣨ʱ�֣�
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--��ǰʱ�������Ӫʱ��

     --��ʼʱ��=��ǰ����+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--��ǰʱ��С����Ӫʱ��

     --��ʼʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;

  --��������
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --ѡ��վʱ��ͳ�Ƴ�վ����
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----ȡ�õ�ǰ��·��վ�����п���
          SELECT nvl(SUM(TRAFFIC),0) INTO P_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='1' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                     GROUP BY FLAG;

          EXCEPTION
          WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

      END;

      BEGIN
           ----ȡ�õ�ǰ��·��վ�����п���
          SELECT nvl(SUM(TRAFFIC),0) INTO P_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='0' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                     GROUP BY FLAG;
          EXCEPTION
          WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

      END;

      OPEN INFO_CUR FOR
      SELECT TMP_LINE_ID line_name,TMP_STATION_ID station_name,P_TRAFFIC_IN traffic_in,P_TRAFFIC_OUT traffic_out FROM dual;

  --δѡ��վʱ��ͳ����·����
  ELSE
      BEGIN

          --������·
          IF TMP_LINE_ID = 'all' THEN
             --����ȫ����վ��ʼ����վ����0����ʱ��acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0';

             --����ͳ����վ��������ʱ��
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in = (SELECT a.traffic_in  FROM
                 (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_in FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                    WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                    AND s1.station_id = t1.station_id
                                                    AND s1.line_id = t1.line_id
                                                    AND s1.record_flag='0'
                                                    GROUP BY t1.FLAG,s1.belong_line_id) a
              WHERE t.line_id=a.belong_line_id);


             --����ͳ�Ƴ�վ��������ʱ��
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out = (SELECT a.traffic_out  FROM
                 (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_out FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                    WHERE t1.TRAFFIC>=0 AND t1.flag='1'
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                    AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                    AND s1.station_id = t1.station_id
                                                    AND s1.line_id = t1.line_id
                                                    AND s1.record_flag='0'
                                                    GROUP BY t1.FLAG,s1.belong_line_id) a
              WHERE t.line_id=a.belong_line_id);


              --������վ��������洢����
              OPEN INFO_CUR FOR
              SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

          --ĳ����·
          ELSE
             --����ȫ����վ��ʼ����վ����0����ʱ��acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0' AND line_id=TMP_LINE_ID;

             --����ͳ����վ��������ʱ��
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in = (SELECT a.traffic_in FROM
                    (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_in FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                AND s1.station_id = t1.station_id
                                                AND s1.belong_line_id = TMP_LINE_ID
                                                AND s1.record_flag='0'
                                                GROUP BY s1.belong_line_id) a
                WHERE t.line_id = a.belong_line_id);


             --����ͳ�Ƴ�վ��������ʱ��
             UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out = (SELECT a.traffic_out FROM
                    (SELECT s1.belong_line_id,SUM(t1.TRAFFIC) traffic_out FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='1'
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                AND s1.station_id = t1.station_id
                                                AND s1.belong_line_id = TMP_LINE_ID
                                                AND s1.record_flag='0'
                                                GROUP BY s1.belong_line_id) a
                WHERE t.line_id = a.belong_line_id);


             --������վ��������洢����
             OPEN INFO_CUR FOR
             SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

          END IF;

      END;
  END IF;

  --��������
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_NET;
/

prompt
prompt Creating procedure UP_FM_FLOW_NET_TOTAL
prompt =======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_NET_TOTAL(V_LINE_STATION          IN VARCHAR2, --��·����վ
                                                 V_TRAFFIC_TIME          IN VARCHAR2, --��ѯʱ��
                                                 INFO_CUR                OUT SYS_REFCURSOR) --����վ����

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_NET_TOTAL
--ϵͳ��״̬��������ϵͳ
--��������:  Ʊ�����͵ĸ�Ʊ����վ������
--�������:   line_id��·ID,station_id ��վID
--����ֵ���ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
--ʹ��:ʹ��5���ӿ������
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_STATION_ID    VARCHAR2(10); --��վ
  TMP_SHH           VARCHAR2(4); --��Ӫʱ�䣨ʱ�֣�
  TMP_PHH           VARCHAR2(4); --����ʱ�䣨ʱ�֣�
  TMP_TRAFFIC_TIME  VARCHAR2(8); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��
  TMP_TRAFFIC_IN         INTEGER; --��վ����
  TMP_TRAFFIC_OUT        INTEGER; --��վ����

BEGIN

  --��ʼ��
  TMP_TRAFFIC_IN := 0;
  TMP_TRAFFIC_OUT := 0;

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --��·
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --��վ
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --��Ӫʱ��
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --����ʱ�䣨ʱ�֣�
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--��ǰʱ�������Ӫʱ��

     --��ʼʱ��=��ǰ����+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--��ǰʱ��С����Ӫʱ��

     --��ʼʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --ѡ��վʱ��ͳ�Ƴ�վ����
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----ȡ�õ�ǰ��·��վ�����п���
          SELECT SUM(TRAFFIC) INTO TMP_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='1' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

           ----ȡ�õ�ǰ��·��վ�����п���
          SELECT SUM(TRAFFIC) INTO TMP_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='0' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;


      END;

  --δѡ��վʱ��ͳ����·����
  ELSE
      BEGIN

          --������·
          IF TMP_LINE_ID = 'all' THEN

             SELECT nvl(SUM(t1.TRAFFIC),0) INTO TMP_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min t1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

             SELECT nvl(SUM(t2.TRAFFIC),0) INTO TMP_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min t2
                                                WHERE t2.TRAFFIC>=0 AND t2.flag='1'
                                                  AND to_date(t2.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(t2.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

          --ĳ����·
          ELSE

             SELECT nvl(SUM(t1.TRAFFIC),0) INTO TMP_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min t1,acc_st.op_prm_station s1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                  AND s1.station_id = t1.station_id
                                                  AND s1.belong_line_id = TMP_LINE_ID
                                                  AND s1.record_flag='0';

             SELECT nvl(SUM(t2.TRAFFIC),0) INTO TMP_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min t2,acc_st.op_prm_station s2
                                                WHERE TRAFFIC>=0 AND flag='1'
                                                  AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                                                  AND t2.station_id = s2.station_id
                                                  AND s2.belong_line_id = TMP_LINE_ID
                                                  AND s2.record_flag='0';

          END IF;

      END;
  END IF;

  OPEN INFO_CUR FOR
  SELECT TMP_TRAFFIC_IN AS TRAFFIC_IN,TMP_TRAFFIC_OUT AS TRAFFIC_OUT FROM dual;


  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_NET_TOTAL;
/

prompt
prompt Creating procedure UP_FM_FLOW_STATION
prompt =====================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_STATION(V_LINE_ID          IN VARCHAR2, --��·
                                               V_TRAFFIC_TIME     IN VARCHAR2, --��ѯʱ��
                                               P_CUR              OUT SYS_REFCURSOR) --���������

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_STATION
--ϵͳ��״̬��������ϵͳ
--��������:  ��·���������
--�������:   line_id��·ID
--����ֵ����վ�ܿ���������վ�ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
--ʹ��:��·Сʱ�������
-------------------------------------------------------------------------------

AS
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_SHH           VARCHAR2(4); --��Ӫʱ�䣨ʱ�֣�
  TMP_PHH           VARCHAR2(4); --����ʱ�䣨ʱ�֣�
  TMP_TRAFFIC_TIME  VARCHAR2(8); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��

BEGIN
  --��������
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --��·
  TMP_LINE_ID := V_LINE_ID;
  --��Ӫʱ��
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --����ʱ�䣨ʱ�֣�
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--��ǰʱ�������Ӫʱ��

     --��ʼʱ��=��ǰ����+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--��ǰʱ��С����Ӫʱ��

     --��ʼʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;

  --����ȫ����վ��ʼ����վ����0����ʱ��acc_commu.T#FM_TRAFFIC_ON_OUT
  INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
  SELECT belong_line_id line_id,station_id,0,0 FROM acc_st.op_prm_station WHERE record_flag = '0';

  --����ͳ����վ��������ʱ��
  UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_in =
  (SELECT t1.traffic_in FROM
        (SELECT b.belong_line_id line_id,a.station_id,nvl(sum(a.traffic),0) traffic_in
              FROM acc_commu.cm_traffic_afc_min a,acc_st.op_prm_station b
              WHERE a.TRAFFIC>=0 AND a.flag='0'
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                AND b.record_flag='0'
                AND b.belong_line_id = TMP_LINE_ID
                AND a.station_id = b.station_id
                AND a.line_id = b.belong_line_id
                GROUP BY b.belong_line_id,a.station_id) t1
  WHERE t.line_id=t1.line_id AND trim(t.station_id) = t1.station_id);


  --����ͳ�Ƴ�վ��������ʱ��
  UPDATE acc_commu.T#FM_TRAFFIC_ON_OUT t SET t.traffic_out =
  (SELECT t1.traffic_out FROM
        (SELECT b.belong_line_id line_id,a.station_id,nvl(sum(a.traffic),0) traffic_out
              FROM acc_commu.cm_traffic_afc_min a,acc_st.op_prm_station b
              WHERE a.TRAFFIC>=0 AND a.flag='1'
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                AND to_date(a.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                AND b.record_flag='0'
                AND b.belong_line_id = TMP_LINE_ID
                AND a.station_id = b.station_id
                AND a.line_id = b.belong_line_id
                GROUP BY b.belong_line_id,a.station_id) t1
  WHERE t.line_id=t1.line_id AND trim(t.station_id) = t1.station_id);


  --������վ��������洢����
  OPEN P_CUR FOR
  SELECT line_id,station_id,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --��������
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_STATION;
/

prompt
prompt Creating procedure UP_FM_FLOW_TYPE_TOTAL
prompt ========================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_TYPE_TOTAL(V_LINE_STATION          IN VARCHAR2, --��·����վ
                                                 V_TRAFFIC_TIME           IN VARCHAR2, --��ѯʱ��
                                                 V_TYPE                   IN VARCHAR2, --������/������
                                                 INFO_CUR                 OUT SYS_REFCURSOR) --�����ͽ���վ����

---------------------------------------------------------------------------------
--������:  UP_FM_FLOW_TYPE_TOTAL
--ϵͳ��״̬��������ϵͳ
--��������:  Ʊ�����͵ĸ�Ʊ����վ������
--�������:   line_id��·ID,station_id ��վID
--����ֵ���ܿ�����
--�����ߣ�  lindaquan
--�������ڣ�20131016
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������
  TMP_LINE_ID       VARCHAR2(10); --��·
  TMP_STATION_ID    VARCHAR2(10); --��վ
  TMP_SHH           VARCHAR2(4); --��Ӫʱ�䣨ʱ�֣�
  TMP_PHH           VARCHAR2(4); --����ʱ�䣨ʱ�֣�
  TMP_TRAFFIC_TIME  VARCHAR2(8); --��ǰʱ��
  TMP_TRAFFIC_TIME_ST  DATE;--��Ӫ��ʼʱ��
  TMP_TRAFFIC_TIME_ED  DATE;--��Ӫ����ʱ��

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --��·
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --��վ
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --��Ӫʱ��
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --����ʱ�䣨ʱ�֣�
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --��ǰʱ��
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--��ǰʱ�������Ӫʱ��

     --��ʼʱ��=��ǰ����+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--��ǰʱ��С����Ӫʱ��

     --��ʼʱ��=��һ������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --��ֹʱ��=��������+��Ӫ�Ŀ�ʼʱ��
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --ѡ��վʱ��ͳ�Ƴ�վ����
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----ȡ�õ�ǰ��·����վ�����п���
          --������
          IF V_TYPE = 'MAIN' THEN
            OPEN INFO_CUR FOR
            SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
              FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_main s
                WHERE t.LINE_ID=TMP_LINE_ID AND t.STATION_ID=TMP_STATION_ID
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                   and t.card_main_type = s.card_main_id
                   and s.record_flag = '0'
                   group by t.flag,t.card_main_type,s.card_main_name;
          --������
          ELSE

            OPEN INFO_CUR FOR
            SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name
              FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_sub s
                WHERE t.LINE_ID=TMP_LINE_ID AND t.STATION_ID=TMP_STATION_ID
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                   AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                   and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                   group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
          END IF;

      END;

  --δѡ��վʱ��ͳ����·����
  ELSE
      BEGIN

          --������·
          IF TMP_LINE_ID = 'all' THEN
             --������
             IF V_TYPE = 'MAIN' THEN
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_main s
                  WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     and t.card_main_type = s.card_main_id
                     and s.record_flag = '0'
                     group by t.flag,t.card_main_type,s.card_main_name;
             --������
             ELSE

               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_sub s
                  WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                     group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
             END IF;

          --ĳ����·
          ELSE

             --������
             IF V_TYPE = 'MAIN' THEN

               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
                    FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s1,acc_st.op_prm_card_main s
                    WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                      AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                      AND s1.station_id = t.station_id
                      and s1.belong_line_id = t.line_id
                      AND s1.belong_line_id = TMP_LINE_ID
                      AND s1.record_flag='0'
                      and t.card_main_type = s.card_main_id
                      and s.record_flag = '0'
                  group by t.flag,t.card_main_type,s.card_main_name;

             --������
             ELSE

               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type, lpad(t.card_sub_type,2,'0') card_sub_type,s.card_sub_name
                    FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s1,acc_st.op_prm_card_sub s
                    WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                      AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                      AND s1.station_id = t.station_id
                      and s1.belong_line_id = t.line_id
                      AND s1.belong_line_id = TMP_LINE_ID
                      AND s1.record_flag='0'
                      and s.record_flag='0'
                     and s.card_main_id = t.card_main_type
                     and s.card_sub_id = t.card_sub_type
                  group by t.flag,t.card_main_type,t.card_sub_type,s.card_sub_name;
             END IF;

          END IF;

      END;
  END IF;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_TYPE_TOTAL;
/

prompt
prompt Creating procedure UP_FM_SET_PASS
prompt =================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_SET_PASS(V_OLD_PASS          IN VARCHAR2, --���������
                                           V_NEW_PASS          IN VARCHAR2, --����������
                                           P_RESULT            OUT VARCHAR2) --���ؽ������

---------------------------------------------------------------------------------
--������:  UP_FM_SET_PASS
--ϵͳ��״̬��������ϵͳ
--����: �����޸�
--���:P_RESULT ���ؽ������
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --��ʱ��������

BEGIN
    --�����벻��Ϊ��
    IF V_NEW_PASS IS NULL OR V_NEW_PASS = '' THEN
        P_RESULT := 1;
        RETURN;
    END IF;

    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;
    --���ݱ�Ϊ��ʱ
    IF TMP_COUNT=0 THEN
        --��ʼ����
        IF V_OLD_PASS IS NULL OR V_OLD_PASS = '' THEN
            INSERT INTO fm_password SELECT V_NEW_PASS FROM dual;
            P_RESULT := 0;
            RETURN;
        ELSE
            P_RESULT := 1;
            RETURN;
        END IF;
    ELSE
       --���ݱ���ڸ�����
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_OLD_PASS;
       IF TMP_COUNT >=1 THEN
          UPDATE fm_password SET exit_pass = V_NEW_PASS;
          P_RESULT := 0;
          RETURN;
       ELSE
          --���벻��ȷ
          P_RESULT := 1;
          RETURN;
       END IF;
     END IF;

END UP_FM_SET_PASS;
/


spool off
