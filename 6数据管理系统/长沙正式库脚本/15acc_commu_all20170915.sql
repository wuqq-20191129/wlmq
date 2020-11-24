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
  is '表数据清理配置表';
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
  is '黑名单参数下发错误记录表';
comment on column ACC_COMMU.CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:地铁卡,2:公交卡';

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
  is '黑名单段参数下发错误记录表';
comment on column ACC_COMMU.CM_ERR_PRM_BLACK_SEC_DOWN.card_type
  is '1:地铁卡,2:公交卡';

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
  is '文件类型';
comment on column ACC_COMMU.CM_FILE_RECV.file_path
  is '接收文件目录';
comment on column ACC_COMMU.CM_FILE_RECV.his_path
  is '历史文件目录';
comment on column ACC_COMMU.CM_FILE_RECV.err_path
  is '错误文件目录';
comment on column ACC_COMMU.CM_FILE_RECV.status
  is '0：未处理
1：正在处理
2：文件格式有问题
3：文件数据入库异常
4:   已处理
5:    文件不存在';
comment on column ACC_COMMU.CM_FILE_RECV.flag
  is 'st:清算
tk:票务
cm:通信';
comment on column ACC_COMMU.CM_FILE_RECV.handle_path
  is '正在处理的文件目录';
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
  is '中间统计表数据历史索引表';
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
  is '表数据清理配置日志表';

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
  is '票库通讯接口文件序号记录表';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.file_type
  is '文件类型';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.seq
  is '当前序号';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.alter_day
  is '修改日期';
comment on column ACC_COMMU.CM_SEQ_TK_FILE.remark
  is '备注';

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
  状态        INTEGER,
  remark    VARCHAR2(50)
)
tablespace ACC_COMMU
  pctfree 10
  initrans 1
  maxtrans 255;
comment on column ACC_COMMU.CM_TK_FILE_RECV.状态
  is '0：未处理
1：正在处理
2：文件格式有问题
3：已处理';
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
  is '状态客流监视系统运行时间配置表';
comment on column ACC_COMMU.FM_CONFIG.config_name
  is '配置名';
comment on column ACC_COMMU.FM_CONFIG.config_value
  is '配置值';
comment on column ACC_COMMU.FM_CONFIG.remark
  is '备注';

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
  is '状态客流监视系统车站图片表';
comment on column ACC_COMMU.FM_DEV_MONITOR.pos_x
  is 'x坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.pos_y
  is 'y坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.start_x
  is '线条开始x坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.start_y
  is '线条开始y坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.end_x
  is '线条结束x坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.end_y
  is '线条结束y坐标';
comment on column ACC_COMMU.FM_DEV_MONITOR.node_type
  is '类型 01动态图,02静态图,03文字,04线条';
comment on column ACC_COMMU.FM_DEV_MONITOR.device_id
  is '设备ID';
comment on column ACC_COMMU.FM_DEV_MONITOR.dev_type_id
  is '设备类型';
comment on column ACC_COMMU.FM_DEV_MONITOR.station_id
  is '车站';
comment on column ACC_COMMU.FM_DEV_MONITOR.line_id
  is '线路';
comment on column ACC_COMMU.FM_DEV_MONITOR.image_url
  is '图片路径';
comment on column ACC_COMMU.FM_DEV_MONITOR.fontsize
  is '文字大小';
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
  is '状态客流监视系统图片状态对照表';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.dev_type_id
  is '设备类型';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.status
  is '状态';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.image_direction
  is '图片描述';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.image_url
  is '图片路径';
comment on column ACC_COMMU.FM_DEV_STATUS_IMAGE.description
  is '说明';
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
  is '状态客流监视系统退出密码';
comment on column ACC_COMMU.FM_PASSWORD.exit_pass
  is '退出密码';

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
  is '状态客流监控 客流统计临时表';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.line_id
  is '线路';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.station_id
  is '车站';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.traffic_in
  is '入站总人数';
comment on column ACC_COMMU.T#FM_TRAFFIC_ON_OUT.traffic_out
  is '出站总人数';

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
  /** * *  function name :存储过程或function 删除后恢复方法  * *  del_time 对象被删除时间 * *  proc_name :被删除对象名称  * *  return :返回重建对象的语句 * */
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
create or replace function acc_commu.up_cm_history_num(in_num number) --输入的当前序号
  --函 数 名： up_cm_history_num
  --功能描述：根据传入的序列号值，计算一下个序列号值，例如传入1，返回002，传入22返回023，传入123返回124
  --输   入: 整数序列号值
  --输   出：3位的序列号值
  --返回值    ：
  --创建者：  张建华
  --创建日期：20131030

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
create or replace function acc_commu.up_dm_cm_tableno(in_num number) --输入的当前序号
  --函 数 名： up_dms_tk_tableno
  --功能描述：根据传入的序列号值，计算一下个序列号值，例如传入1，返回0002，传入22返回0023，传入123返回0124
  --输   入: 整数序列号值
  --输   出：4位的序列号值
  --返回值    ：
  --创建者：  张建华
  --创建日期：20131021

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
  --过程名：  up_dm_cm_mv_log
  --功能描述：记录迁移数据时的日志
  --输入参数 origin_table_name  原表名
  --输入参数 dest_table_name   分表表名
  --输入参数 begin_clear_datetime  操作开始时间
  --输入参数 end_clear_datetimein  操作结束时间
  --输入参数 spent_time  操作所花时间
  --输入参数 clear_recd_count  清理记录数
  --输入参数 err_discribe  错误描述
  --创建者：张建华
  --创建日期：20131028
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_clear(out_retresult OUT INTEGER, --返回结果
                                               out_msg       OUT VARCHAR2 --返回信息
                                               )
---------------------------------------------------------------------------------
  --过程名：  up_dm_cm_his_clear
  --功能描述：数据交换系统--日志清理
  --输出参数  ：out_retresult 模块返回值
  --返回值    ：0：操作成功；其他：操作失败；
  --创建者：张建华
  --创建日期：20131123
  --修改日期：20150514 clear_flag 改为0：只删除数据，1：迁移数据到分表
  --cm_traffic_afc_min表的traffic_datetime字段为VARCHAR2(12)类型，需要与其他表的Date类型字段 区分处理
  -------------------------------------------------------------------------------
 AS
  v_sql             varchar2(2048);
  v_count           integer; --临时统计变量
  v_begin_time      date; --清理开始时间
  v_clear_time      date; --清理开始时间
  origin_table_name varchar2(30); --中间表表名
  -- table_columns     varchar2(1000); --中间表各字段
  v_cost_seconds int; --统计时间
  v_msg          varchar2(255); --注释

  v_clear_time_str  varchar2(40); --清理开始时间
  v_date_str     VARCHAR2(20);  --20150529 modify by mqf

BEGIN
  begin
    out_msg       := '清理数据';
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
         where t.clear_flag = 0; --20150514 modify by mqf clear_flag 1改为0。 0：只删除数据，1：迁移数据到分表
    begin

      for i_cur in scc_table loop

        origin_table_name := trim(i_cur.tmp_origin_table);
        --table_columns     := trim(i_cur.tmp_table_columns);
        v_begin_time := sysdate;

    --20150525 modify by mqf cm_traffic_afc_min表的traffic_datetime字段为VARCHAR2(12)类型，需要与其他表的Date类型字段 区分处理
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
              out_msg        := origin_table_name || '删除数据错误,错误代码:' ||
                                SQLERRM || ',发生在第[' ||
                                dbms_utility.format_error_backtrace() || ']行';
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
        --记录清理日志
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
      out_msg       := v_msg || '错误：' || sqlerrm || ',发生在第[' ||
                       dbms_utility.format_error_backtrace() || ']行';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_clear;
/

prompt
prompt Creating procedure UP_DM_CM_HIS_CREATE
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_create(out_retResult OUT INTEGER, --返回结果
                                                          out_msg       OUT VARCHAR2 --返回信息
                                                          )
---------------------------------------------------------------------------------
  --过程名：  up_dm_cm_his_create
  --功能描述：建立数据交换系统各日志表的历史分表
  --输出参数  ：out_retResult 模块返回值 out_msg 返回信息
  --返回值    ：0：操作成功；其他：操作失败；
  --创建者：  张建华
  --创建日期：201301105
  --修改：20150511
  --clear_flag 改为0：只删除数据，1：迁移数据到分表
  -------------------------------------------------------------------------------
 AS

  v_msg VARCHAR(50);
  --v_sql              varchar(5000);
  --v_new_begin date; --新的开始时间
  v_new_begin varchar2(40); --新的结束时间
  --v_new_end      varchar2(40); --新的结束时间
  v_begin_time   date;
  v_min_date     varchar2(40); --最小时间
  v_count        number(18); --临时统计变量
  v_count2       number(18); --临时统计变量
  v_flag         number(1); --创建001的标志，0：创建
  v_time         varchar2(20); --临时时间变量
  v_temp_sql     varchar2(4000); --临时sql语句
  v_table_sql    varchar2(4000); --建表临时sql语句
  v_temp_num     varchar2(6); --分表序列号
  new_table_name varchar2(40); --分表表名
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
         where t.clear_flag = 1; --20150511 clear_flag 改为 1, 0：只删除数据，1：迁移数据到分表
    begin
      for i_cur in scc_table loop
        begin
          v_flag := 2;
          --如果原始表没有数据，则不建立分表
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
          --因为有些原表名太长，所以生成的分表采用自定义的表名，控制了长度
          new_table_name := i_cur.tmp_ab_name || '000001';

          v_temp_sql := 'select count(*) from user_objects where object_name = upper (''' ||
                        new_table_name || ''')';
          execute immediate v_temp_sql
            into v_count2;
        end;
        begin
          if v_count = 0 then
            --索引表没有对应分表的记录
            if v_count2 > 0 then
              --分表的物理表存在
              v_temp_sql := 'drop table acc_commu.' || new_table_name;
              execute immediate v_temp_sql; --删除0001分表表

            end if;
            v_flag := 0;

          else
            ----索引表有对应分表的记录
            if v_count2 = 0 then
              --分表的物理表不存在
              v_flag := 0;
            end if;
          end if;
        end;
        if v_flag = 0 then
          --先建表，再初始化
          begin
            --建001表
            begin
              --获取建表语句
              --替换表名 替换索引 替换主键
              select regexp_replace(i_cur.tmp_create_sql,
                                    '%s',
                                    new_table_name)
                into v_temp_sql
                from dual;

              --每个建表语句中含有建索引、主键信息，且以；分割
              declare
                cursor sql_cur is
                  select *
                    from table(acc_commu.up_dm_cm_splitstr(v_temp_sql, ';'));
              begin
                for t_cur in sql_cur loop
                  v_table_sql := t_cur.column_value;

                  if trim(v_table_sql) is not null and v_table_sql <> ' ' then
                    execute immediate v_table_sql; --创建0001分表相关的操作
                  end if;
                end loop;
              EXCEPTION
                when others then
                  begin
                    rollback;
                    out_retresult := SQLCODE;
                    out_msg       := new_table_name || '创建表结构出错:' ||
                                     SQLERRM || ',发生在第[' ||
                                     dbms_utility.format_error_backtrace() || ']行';
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

      --20150512 add by mqf 增加日志
      acc_commu.up_dm_cm_mv_log(i_cur.tmp_origin_table,
                                              new_table_name,

                                              sysdate,
                                              sysdate,
                                              0,
                                              0,
                                              '成功创建' || new_table_name || '表',
                                              'create table ' || new_table_name || '...');

            --计算最小、最大流水号、最小运营日，最大运营日
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

  ---------------以下为创建非001表

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
           and b.clear_flag = 1 --20150511 clear_flag 改为 1, 0：只删除数据，1：迁移数据到分表
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
              --达到配置表中的最大记录数，需要建表
              --下一分表的序列号
              v_temp_num     := acc_commu.up_dm_cm_tableno(my_cur.tmp_i_list_no);
              new_table_name := my_cur.tmp_ab_name || v_temp_num;
              begin
                v_temp_sql := 'select count(*) from user_objects where object_name = upper (''' ||
                              new_table_name || ''')';
                execute immediate v_temp_sql
                  into v_count2;
                if v_count2 > 0 then
                  --删除该表
                  v_temp_sql := 'drop table  ' || new_table_name;
                  execute immediate v_temp_sql;
                end if;
              end;
              --获取建表语句
              --替换表名 替换索引 替换主键
              select REGEXP_REPLACE(my_cur.tmp_create_sql,
                                    '%s',
                                    new_table_name)
                into v_temp_sql
                from dual;

              --每个建表语句中含有建索引、主键信息，且以；分割
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
                      execute immediate v_table_sql; --创建001分表相关的操作
                    EXCEPTION
                      when others then
                        begin
                          rollback;
                          out_retresult := SQLCODE;
                          out_msg       := new_table_name || '创建表结构出错:' ||
                                           SQLERRM || ',发生在第[' ||
                                           dbms_utility.format_error_backtrace() || ']行';
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

        --20150512 add by mqf 增加日志
        acc_commu.up_dm_cm_mv_log(my_cur.tmp_origin_table,
                        new_table_name,

                        sysdate,
                        sysdate,
                        0,
                        0,
                        '成功创建' || new_table_name || '表',
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
              end; --if上的begin

            end if;
          end;

        end; --loop中的第2个begin
      end loop;
    end; -- for之前的begin
    out_msg       := '建表成功';
    out_retResult := sqlcode;
    commit;
  end; --declare前的begin

exception
  when OTHERS THEN
    begin
      ROLLBACK;
      out_msg       := v_msg || '错误：' || sqlerrm || ',发生在第[' ||
                       dbms_utility.format_error_backtrace() || ']行';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_create;
/

prompt
prompt Creating procedure UP_DM_CM_HIS_MV
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_his_mv(out_retresult OUT INTEGER, --返回结果
                                                      out_msg       OUT VARCHAR2 --返回信息
                                                      )
---------------------------------------------------------------------------------
  --过程名：  up_dm_cm_his_mv
  --功能描述：数据交换系统--数据表导入历史表
  --输出参数  ：out_retresult 模块返回值
  --返回值    ：0：操作成功；其他：操作失败；
  --创建者：张建华
  --创建日期：20131104
  --修改日期： 20150424 by mqf
  --修改内容： 按日期遍历，记录数大于分表最大记录数则停止迁移数据，新建分表再迁移数据。
             --解决上线运行时，堆积大量的记录插入到第一个分表的问题
       --clear_flag 改为0：只删除数据，1：迁移数据到分表
  --修改日期：20160912 modify by mqf
  --修改内容：1.关闭游标
  -------------------------------------------------------------------------------
 AS
  v_sql       varchar2(2048);
  v_whereSql  varchar2(2048);
  v_max_table varchar2(40); --最大的历史表
  c_begin     varchar2(40); --当前表对应的开始时间
  c_end       varchar2(40); --当前表对应的结束时间
  --v_new_begin       date;
  --v_new_end         date;
  v_time            varchar2(10);
  v_count           integer; --临时统计变量
  v_table_columns   varchar2(2048); --表字段
  v_begin_time      date; --清理开始时间
  origin_table_name varchar2(30); --中间表表名
  table_columns     varchar2(1000); --中间表各字段
  v_cost_seconds    int; --统计时间
  v_msg             varchar2(255); --注释

  --20140422 add by mqf
  divide_recd_count integer; --配置表最大记录数
  v_recd_count  integer; --索引表已更新记录数
  v_rowcount  integer; --更新记录数
  v_date_type_value varchar2(40); --日期
  TYPE MYCURSOR IS REF CURSOR;  --定义游标类型
  cur_date_type MYCURSOR;       --定义游标
  v_date_begin     varchar2(40); --遍历的开始时间
  v_date_end     varchar2(40); --遍历的结束时间
  v_date_whereSql  varchar2(2048);


BEGIN
  begin
    out_msg       := '成功迁移数据';
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
         where t.clear_flag = 1; --20150511 clear_flag 改为 1, 0：只删除数据，1：迁移数据到分表
    begin

      for i_cur in scc_table loop

        origin_table_name := trim(i_cur.tmp_origin_table);
        table_columns     := trim(i_cur.tmp_table_columns);
    --20150422 add by mqf
    divide_recd_count := trim(i_cur.tmp_divide_recd_count);
        v_begin_time      := sysdate;
        --清理标志为1时才清理中间表至历史表
        --在配置表中 clear_table_config 的station_entry_rpt 的 clear_flag 设置为-1

        --最大表
        v_sql := 'select max(his_table) from acc_commu.cm_idx_history where origin_table_name = ''' ||
                 origin_table_name || '''';
        execute immediate v_sql
          into v_max_table;
    if (v_max_table is null) or (v_max_table = '') then --20150511 add by mqf
            continue;
        end if;
        v_table_columns := table_columns;

        --对最大的历史表导入数据
        select begin_date,recd_count
          into c_begin,v_recd_count
          from acc_commu.cm_idx_history
         where his_table = v_max_table;
        --v_new_begin := to_date(c_begin, 'yyyy-mm-dd hh24:mi:ss');

    --索引表已迁移记录大于或等于分表最大记录数，将跳到一次循环 20150520 modify by mqf
    if v_recd_count>=divide_recd_count then
      continue;
    end if;

        v_time := to_char(v_begin_time - i_cur.tmp_keep_days, 'yyyymmdd');
        --更新到索引表中
        c_end := v_time || '2359';

        --v_new_end := to_date(c_end, 'yyyy-mm-dd hh24:mi:ss');
        v_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
                      i_cur.tmp_date_type || ' <=''' || c_end || ''' and ' ||
                      i_cur.tmp_date_type || '>=''' || c_begin || '''';
        begin
          --更新st_idx_rpt_history表信息
          v_sql := 'select count(*) ' || v_whereSql;
          execute immediate v_sql
            into v_count;
          if v_count = 0 then
            continue;
          end if;
        end;
        --对最大的历史表导入数据
        v_sql := 'lock table acc_commu.' || v_max_table || ' in exclusive mode';
        execute immediate v_sql;

    --20150422 add by mqf 按日期遍历，解决上线运行时，堆积大量的记录插入到第一个分表中。
    v_sql := 'select distinct substr(' || i_cur.tmp_date_type  || ',0,8) ' ||
         v_whereSql || ' order by substr(' || i_cur.tmp_date_type || ',0,8) ';
    --更新记录数初始为0
    v_rowcount := 0;

    open cur_date_type for v_sql;
        loop
      fetch cur_date_type into v_date_type_value;
        exit when cur_date_type%notfound;

      --超过分表最大记录数，将结束循环
        if (v_recd_count+v_rowcount>=divide_recd_count) then
          exit;
        end if;

      --按日期导入数据
      begin
        v_date_begin := substr(v_date_type_value,0,8) || '0000';
        v_date_end := substr(v_date_type_value,0,8) || '2359';
        v_date_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
                      i_cur.tmp_date_type || ' <=''' || v_date_end || ''' and ' ||
                      i_cur.tmp_date_type || '>=''' || v_date_begin || '''';

        v_sql := ' insert into acc_commu.' || v_max_table || ' select ' ||
             v_table_columns || v_date_whereSql;
        execute immediate v_sql;
        --累加更新记录数
        v_rowcount := v_rowcount + sql%rowcount;



      EXCEPTION
        when others then
        begin
          rollback;
          out_retresult  := SQLCODE;
          out_msg        := origin_table_name || '插入历史表错误,错误代码:' ||
                  SQLERRM || ',发生在第[' ||
                  dbms_utility.format_error_backtrace() || ']行';
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
  close cur_date_type; --20160912 add by mqf 关闭游标


    --20150422 add by mqf v_whereSql用于删除记录
    v_whereSql := ' from acc_commu.' || origin_table_name || ' where ' ||
            i_cur.tmp_date_type || ' <=''' || v_date_end || ''' and ' ||
            i_cur.tmp_date_type || '>=''' || c_begin || '''';

    c_end := v_date_end;
    v_count := v_rowcount;


    --写入日志记录表clear_table_log
    out_msg := '从' || origin_table_name || '表中导入' || v_max_table ||
           '历史表的数据成功，记录数：' || v_count;
    --20150422 add by mqf
    v_cost_seconds := ROUND(TO_NUMBER(sysdate - v_begin_time) * 24 * 60 * 60);
    acc_commu.up_dm_cm_mv_log(origin_table_name,
                  v_max_table,
                  v_begin_time,
                  sysdate,
                  v_cost_seconds,
                  v_count,
                  out_msg,
                  v_sql); --20140424 modify by mqf out_msg改为v_sql

    if v_count>0 then --20150511 add by mqf 记录大于0才更新索引表、删除源表数据
      begin

          update acc_commu.cm_idx_history
           set recd_count = recd_count + v_count, end_date = c_end
           where his_table = v_max_table;

      end;

      --删除已经导入历史表的数据
      v_begin_time := sysdate;
      begin
        v_sql := ' delete  ' || v_whereSql;
        execute immediate v_sql;

      end;
      out_msg        := '删除' || origin_table_name || '表中导入' ||
                v_max_table || '历史表的数据成功';
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
      out_msg       := v_msg || '错误：' || sqlerrm || ',发生在第[' ||
                       dbms_utility.format_error_backtrace() || ']行';
      out_retResult := sqlcode;
      return;
    end;

END up_dm_cm_his_mv;
/

prompt
prompt Creating procedure UP_DM_CM_TABLE_DROP
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.up_dm_cm_table_drop(out_retresult OUT INTEGER, --返回结果
                                                out_msg       OUT VARCHAR2 --返回信息
                                                )
---------------------------------------------------------------------------------
  --过程名：  up_dm_cm_table_drop
  --功能描述：数据交换系统--分表和索引表记录清理（用于上线前清理）
  --输出参数  ：out_retresult 模块返回值
  --返回值    ：0：操作成功；其他：操作失败；
  --创建者：张建华
  --创建日期：20131123
  -------------------------------------------------------------------------------
 AS
  v_sql               varchar2(2048);
  v_his_table         varchar2(30); --需要删除的历史表名
  v_origin_table_name varchar2(30);
  v_msg               varchar2(255); --注释
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
    out_msg       := '清理数据';
    out_retResult := sqlcode;
  end;
  commit;
exception
  when OTHERS THEN
    begin
    
      out_msg       := v_msg || '错误：' || sqlerrm || ',发生在第[' ||
                       dbms_utility.format_error_backtrace() || ']行';
      out_retResult := sqlcode;
      return;
    end;  
END up_dm_cm_table_drop;
/

prompt
prompt Creating procedure UP_FM_EXIT_PASS
prompt ==================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_EXIT_PASS(V_PASS          IN VARCHAR2, --输入密码
                                            P_RESULT        OUT VARCHAR2) --返回结果代码

---------------------------------------------------------------------------------
--过程名:  UP_FM_EXIT_PASS
--系统：状态客流监视系统
--功能: 检验退出密码
--输出:P_RESULT 返回结果代码
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量

BEGIN

    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;
    --数据表为空时
    IF TMP_COUNT=0 THEN
        P_RESULT := '1';
        RETURN;
    ELSE
       --数据表存在该密码
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_PASS;
       IF TMP_COUNT >=1 THEN
          P_RESULT := '0';
          RETURN;
       ELSE
          --密码不正确
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_HOUR(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                            V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                            INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_HOUR
--系统：状态客流监视系统
--功能描述:  线网、线路、车站当前客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:线网、车站小时客流表格
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  P_TRAFFIC_IN         INTEGER; --进站客流
  P_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路车站的所有客流
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,2),t.line_id,t.station_id,flag;

      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路(全部线路进出客流)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT substr(t.traffic_datetime,9,2) hour,SUM(t.traffic) traffic,flag memo
                     FROM acc_commu.cm_traffic_afc_min t
                     WHERE to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                         AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     GROUP BY substr(t.traffic_datetime,9,2),flag;


          --某条线路进出客流
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_MIN(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                           V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                           INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_MIN
--系统：状态客流监视系统
--功能描述:  进、出站5分钟客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_TRAFFIC_TIME  VARCHAR2(10); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  P_TRAFFIC_IN         INTEGER; --进站客流
  P_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2)||substr(V_TRAFFIC_TIME,12,2);
  --起始时间
  TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || '00','yyyyMMddHH24miss')-1/24;
  --终止时间
  TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || '59','yyyyMMddHH24miss');


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路车站的所有客流
          OPEN INFO_CUR FOR

          SELECT t.line_id,t.station_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic,flag memo
                 FROM acc_commu.cm_traffic_afc_min t
                 WHERE t.line_id = TMP_LINE_ID
                     AND t.station_id = TMP_STATION_ID
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY substr(t.traffic_datetime,9,4),flag,t.line_id,t.station_id;

      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路(全部线路进出客流)
          IF TMP_LINE_ID = 'all' THEN
              OPEN INFO_CUR FOR

              SELECT s.belong_line_id line_id,substr(t.traffic_datetime,9,4) hour_min,SUM(t.traffic) traffic
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_station s
                 WHERE t.line_id = s.line_id
                     AND t.station_id = s.station_id
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                 GROUP BY s.belong_line_id,substr(t.traffic_datetime,9,4);


          --某条线路进出客流
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_NET(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                           V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                           INFO_CUR                OUT SYS_REFCURSOR) --返回结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_NET
--系统：状态客流监视系统
--功能描述:  线网、线路、车站当前客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:线网、车站小时客流表格
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  P_TRAFFIC_IN         INTEGER; --进站客流
  P_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  --初始化
  P_TRAFFIC_IN := 0;
  P_TRAFFIC_OUT := 0;

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;

  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路出站的所有客流
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
           ----取得当前线路进站的所有客流
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

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路
          IF TMP_LINE_ID = 'all' THEN
             --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0';

             --更新统计入站总数到临时表
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


             --更新统计出站总数到临时表
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


              --将出入站总数放入存储过程
              OPEN INFO_CUR FOR
              SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

          --某条线路
          ELSE
             --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
             INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
             SELECT line_id,line_name,0,0 FROM acc_st.op_prm_line WHERE record_flag = '0' AND line_id=TMP_LINE_ID;

             --更新统计入站总数到临时表
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


             --更新统计出站总数到临时表
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


             --将出入站总数放入存储过程
             OPEN INFO_CUR FOR
             SELECT line_id,station_id line_name,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

          END IF;

      END;
  END IF;

  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_NET;
/

prompt
prompt Creating procedure UP_FM_FLOW_NET_TOTAL
prompt =======================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_NET_TOTAL(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                                 V_TRAFFIC_TIME          IN VARCHAR2, --查询时间
                                                 INFO_CUR                OUT SYS_REFCURSOR) --进出站客流

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_NET_TOTAL
--系统：状态客流监视系统
--功能描述:  票卡类型的各票卡进站客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:使用5分钟客流表格
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间
  TMP_TRAFFIC_IN         INTEGER; --进站客流
  TMP_TRAFFIC_OUT        INTEGER; --出站客流

BEGIN

  --初始化
  TMP_TRAFFIC_IN := 0;
  TMP_TRAFFIC_OUT := 0;

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路出站的所有客流
          SELECT SUM(TRAFFIC) INTO TMP_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='1' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

           ----取得当前线路进站的所有客流
          SELECT SUM(TRAFFIC) INTO TMP_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min
                               WHERE LINE_ID=TMP_LINE_ID AND STATION_ID=TMP_STATION_ID
                                     AND FLAG='0' AND TRAFFIC>=0
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                     AND to_date(traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;


      END;

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路
          IF TMP_LINE_ID = 'all' THEN

             SELECT nvl(SUM(t1.TRAFFIC),0) INTO TMP_TRAFFIC_IN FROM acc_commu.cm_traffic_afc_min t1
                                                WHERE t1.TRAFFIC>=0 AND t1.flag='0'
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(t1.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

             SELECT nvl(SUM(t2.TRAFFIC),0) INTO TMP_TRAFFIC_OUT FROM acc_commu.cm_traffic_afc_min t2
                                                WHERE t2.TRAFFIC>=0 AND t2.flag='1'
                                                  AND to_date(t2.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                                                  AND to_date(t2.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED;

          --某条线路
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_STATION(V_LINE_ID          IN VARCHAR2, --线路
                                               V_TRAFFIC_TIME     IN VARCHAR2, --查询时间
                                               P_CUR              OUT SYS_REFCURSOR) --客流结果集

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_STATION
--系统：状态客流监视系统
--功能描述:  线路当天客流量
--输入参数:   line_id线路ID
--返回值：出站总客流量、进站总客流量
--创建者：  lindaquan
--创建日期：20131016
--使用:线路小时客流表格
-------------------------------------------------------------------------------

AS
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间

BEGIN
  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --线路
  TMP_LINE_ID := V_LINE_ID;
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;

  --插入全部车站初始出入站人数0到临时表acc_commu.T#FM_TRAFFIC_ON_OUT
  INSERT INTO acc_commu.T#FM_TRAFFIC_ON_OUT
  SELECT belong_line_id line_id,station_id,0,0 FROM acc_st.op_prm_station WHERE record_flag = '0';

  --更新统计入站总数到临时表
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


  --更新统计出站总数到临时表
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


  --将出入站总数放入存储过程
  OPEN P_CUR FOR
  SELECT line_id,station_id,traffic_in,traffic_out FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  --清除缓存表
  DELETE FROM acc_commu.T#FM_TRAFFIC_ON_OUT;

  COMMIT;

  DBMS_OUTPUT.PUT_LINE('sqlerrm : ' || SQLERRM);

END UP_FM_FLOW_STATION;
/

prompt
prompt Creating procedure UP_FM_FLOW_TYPE_TOTAL
prompt ========================================
prompt
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_FLOW_TYPE_TOTAL(V_LINE_STATION          IN VARCHAR2, --线路，车站
                                                 V_TRAFFIC_TIME           IN VARCHAR2, --查询时间
                                                 V_TYPE                   IN VARCHAR2, --主类型/子类型
                                                 INFO_CUR                 OUT SYS_REFCURSOR) --子类型进出站客流

---------------------------------------------------------------------------------
--过程名:  UP_FM_FLOW_TYPE_TOTAL
--系统：状态客流监视系统
--功能描述:  票卡类型的各票卡进站客流数
--输入参数:   line_id线路ID,station_id 车站ID
--返回值：总客流量
--创建者：  lindaquan
--创建日期：20131016
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量
  TMP_LINE_ID       VARCHAR2(10); --线路
  TMP_STATION_ID    VARCHAR2(10); --车站
  TMP_SHH           VARCHAR2(4); --运营时间（时分）
  TMP_PHH           VARCHAR2(4); --参数时间（时分）
  TMP_TRAFFIC_TIME  VARCHAR2(8); --当前时间
  TMP_TRAFFIC_TIME_ST  DATE;--运营开始时间
  TMP_TRAFFIC_TIME_ED  DATE;--运营结束时间

BEGIN

  TMP_COUNT := instr(V_LINE_STATION,'_',1,1);
  --线路
  TMP_LINE_ID := substr(V_LINE_STATION,1,TMP_COUNT-1);
  --车站
  TMP_STATION_ID := substr(V_LINE_STATION,TMP_COUNT+1);
  --运营时间
  SELECT LPAD(TRIM(cfg_value),4,'0') INTO TMP_SHH FROM acc_st.st_cfg_sys_base where cfg_key='sys.squadday' and record_flag='0';
  --参数时间（时分）
  TMP_PHH := substr(V_TRAFFIC_TIME,12,2)||substr(V_TRAFFIC_TIME,15,2);
  --当前时间
  TMP_TRAFFIC_TIME := substr(V_TRAFFIC_TIME,1,4)||substr(V_TRAFFIC_TIME,6,2)||substr(V_TRAFFIC_TIME,9,2);

  IF TMP_PHH >= TMP_SHH THEN--当前时间大于运营时间

     --起始时间=当前日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=下一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') + 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');

  ELSE--当前时间小于运营时间

     --起始时间=上一天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ST := to_date(to_char(to_date(TMP_TRAFFIC_TIME||'0000','yyyyMMddHH24miss') - 1,'yyyyMMdd') || TMP_SHH,'yyyyMMddHH24miss');
     --终止时间=当天日期+运营的开始时间
     TMP_TRAFFIC_TIME_ED := to_date(TMP_TRAFFIC_TIME || TMP_SHH,'yyyyMMddHH24miss');
  END IF;


  --选择车站时，统计车站客流
  IF TMP_STATION_ID != '-1' THEN

      BEGIN

          ----取得当前线路进出站的所有客流
          --主类型
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
          --子类型
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

  --未选择车站时，统计线路客流
  ELSE
      BEGIN

          --所有线路
          IF TMP_LINE_ID = 'all' THEN
             --主类型
             IF V_TYPE = 'MAIN' THEN
               OPEN INFO_CUR FOR
               SELECT nvl(SUM(t.traffic),0) traffic,t.flag,lpad(t.card_main_type,2,'0') card_main_type,s.card_main_name
                 FROM acc_commu.cm_traffic_afc_min t,acc_st.op_prm_card_main s
                  WHERE to_date(t.traffic_datetime,'yyyyMMddHH24miss') > TMP_TRAFFIC_TIME_ST
                     AND to_date(t.traffic_datetime,'yyyyMMddHH24miss') <= TMP_TRAFFIC_TIME_ED
                     and t.card_main_type = s.card_main_id
                     and s.record_flag = '0'
                     group by t.flag,t.card_main_type,s.card_main_name;
             --子类型
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

          --某条线路
          ELSE

             --主类型
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

             --子类型
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
CREATE OR REPLACE PROCEDURE ACC_COMMU.UP_FM_SET_PASS(V_OLD_PASS          IN VARCHAR2, --输入旧密码
                                           V_NEW_PASS          IN VARCHAR2, --输入新密码
                                           P_RESULT            OUT VARCHAR2) --返回结果代码

---------------------------------------------------------------------------------
--过程名:  UP_FM_SET_PASS
--系统：状态客流监视系统
--功能: 密码修改
--输出:P_RESULT 返回结果代码
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量

BEGIN
    --新密码不能为空
    IF V_NEW_PASS IS NULL OR V_NEW_PASS = '' THEN
        P_RESULT := 1;
        RETURN;
    END IF;

    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;
    --数据表为空时
    IF TMP_COUNT=0 THEN
        --初始密码
        IF V_OLD_PASS IS NULL OR V_OLD_PASS = '' THEN
            INSERT INTO fm_password SELECT V_NEW_PASS FROM dual;
            P_RESULT := 0;
            RETURN;
        ELSE
            P_RESULT := 1;
            RETURN;
        END IF;
    ELSE
       --数据表存在该密码
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_OLD_PASS;
       IF TMP_COUNT >=1 THEN
          UPDATE fm_password SET exit_pass = V_NEW_PASS;
          P_RESULT := 0;
          RETURN;
       ELSE
          --密码不正确
          P_RESULT := 1;
          RETURN;
       END IF;
     END IF;

END UP_FM_SET_PASS;
/


spool off
