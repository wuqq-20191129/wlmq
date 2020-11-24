create table W_CM_CFG_CLEAR_TABLE
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
);
comment on table W_CM_CFG_CLEAR_TABLE
  is '表数据清理配置表';
alter table W_CM_CFG_CLEAR_TABLE
  add constraint PK_W_CM_CFG_CLEAR_TABLE primary key (ORIGIN_TABLE_NAME);
  
create table W_CM_CFG_STATUS_MAPPING
(
  status_id        CHAR(3) not null,
  status_value     CHAR(1) not null,
  acc_status_value CHAR(1),
  status_name      VARCHAR2(50),
  acc_status_name  VARCHAR2(50)
);
alter table W_CM_CFG_STATUS_MAPPING
  add constraint W_CM_CFG_STATUS_MAPPING_PK primary key (STATUS_ID, STATUS_VALUE);
create table W_CM_CFG_SYS
(
  config_name  VARCHAR2(100) not null,
  config_value VARCHAR2(550) not null,
  remark       VARCHAR2(250)
);
alter table W_CM_CFG_SYS
  add constraint W_CM_CFG_SYS_PK primary key (CONFIG_NAME);
create table W_CM_DEV_CURRENT_STATUS
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
);
alter table W_CM_DEV_CURRENT_STATUS
  add constraint PK_W_CM_DEV_CURRENT_STATUS primary key (LINE_ID, STATION_ID, DEV_TYPE_ID, DEVICE_ID);
create table W_CM_DEV_HIS_STATUS
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
);
create table W_CM_DEV_EVENT
(
  event_datetime DATE,
  line_id        VARCHAR2(2),
  station_id     VARCHAR2(2),
  dev_type_id    VARCHAR2(2),
  device_id      VARCHAR2(4),
  event_id       VARCHAR2(2),
  event_argument VARCHAR2(90)
);
create table W_CM_DEV_PARA_VER_CUR
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
);
alter table W_CM_DEV_PARA_VER_CUR
  add constraint W_CM_DEV_PARA_VER_CUR_PK primary key (LINE_ID, STATION_ID, DEV_TYPE_ID, DEVICE_ID, PARM_TYPE_ID, RECORD_FLAG);
create table W_CM_DEV_PARA_VER_HIS
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
);
alter table W_CM_DEV_PARA_VER_HIS
  add constraint W_CM_DEV_PARA_VER_HIS_PK primary key (WATER_NO);
create table W_CM_EC_CONNECT_LOG
(
  water_no         NUMBER(8) not null,
  connect_datetime DATE not null,
  connect_ip       VARCHAR2(15) not null,
  connect_result   CHAR(1),
  remark           VARCHAR2(100)
);
alter table W_CM_EC_CONNECT_LOG
  add primary key (WATER_NO);
create table W_CM_EC_FTP_LOG
(
  water_no     NUMBER(8) not null,
  datetime_ftp DATE not null,
  ip           VARCHAR2(15) not null,
  filename     VARCHAR2(50),
  start_time   DATE,
  spend_time   NUMBER(8),
  result       CHAR(1),
  remark       VARCHAR2(512)
);
alter table W_CM_EC_FTP_LOG
  add primary key (WATER_NO);
create table W_CM_EC_LOG
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
);
alter table W_CM_EC_LOG
  add primary key (WATER_NO);
create table W_CM_EC_LOG_LEVEL
(
  sys_code    CHAR(1) not null,
  sys_level   CHAR(1) not null,
  set_time    DATE not null,
  operator    CHAR(6) not null,
  version_no  CHAR(10) not null,
  record_flag CHAR(1) not null
);
alter table W_CM_EC_LOG_LEVEL
  add primary key (SYS_CODE, VERSION_NO, RECORD_FLAG);
create table W_CM_EC_MSG_PRIORITY
(
  type   VARCHAR2(1) not null,
  msg_id VARCHAR2(3) not null,
  remark VARCHAR2(100)
);
create table W_CM_EC_OPERATOR_LOG
(
  oper_id    VARCHAR2(10) not null,
  dev_id     VARCHAR2(8) not null,
  login_time DATE,
  exit_time  DATE,
  memo       CHAR(30)
);
create table W_CM_EC_RECV_SEND_LOG
(
  water_no     NUMBER(10) not null,
  datetime_rec DATE not null,
  ip           VARCHAR2(15) not null,
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(1)
);
alter table W_CM_EC_RECV_SEND_LOG
  add primary key (WATER_NO, DATETIME_REC, IP);
create table W_CM_ERR_PRM_BLACK_DOWN
(
  card_logical_id VARCHAR2(20) not null,
  card_type       CHAR(1),
  remark          VARCHAR2(256),
  create_datetime DATE
);
comment on table W_CM_ERR_PRM_BLACK_DOWN
  is '黑名单参数下发错误记录表';
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:地铁卡,2:公交卡';
create table W_CM_ERR_PRM_BLACK_SEC_DOWN
(
  begin_logical_id VARCHAR2(20) not null,
  end_logical_id   VARCHAR2(20) not null,
  card_type        CHAR(1),
  remark           VARCHAR2(256),
  create_datetime  DATE
);
comment on table W_CM_ERR_PRM_BLACK_SEC_DOWN
  is '黑名单段参数下发错误记录表';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.card_type
  is '1:地铁卡,2:公交卡';
create table W_CM_FILE_RECV
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
);
comment on column W_CM_FILE_RECV.file_type
  is '文件类型';
comment on column W_CM_FILE_RECV.file_path
  is '接收文件目录';
comment on column W_CM_FILE_RECV.his_path
  is '历史文件目录';
comment on column W_CM_FILE_RECV.err_path
  is '错误文件目录';
comment on column W_CM_FILE_RECV.status
  is '0：未处理
1：正在处理
2：文件格式有问题
3：文件数据入库异常
4:   已处理
5:    文件不存在';
comment on column W_CM_FILE_RECV.flag
  is 'st:清算
tk:票务
cm:通信';
comment on column W_CM_FILE_RECV.handle_path
  is '正在处理的文件目录';
alter table W_CM_FILE_RECV
  add constraint PK_W_CM_FILE_RECV primary key (WATER_NO);
create table W_CM_IDX_HISTORY
(
  his_table         VARCHAR2(40) not null,
  origin_table_name VARCHAR2(30),
  begin_date        VARCHAR2(20),
  end_date          VARCHAR2(20),
  recd_count        INTEGER,
  recd_type         VARCHAR2(3)
);
comment on table W_CM_IDX_HISTORY
  is '中间统计表数据历史索引表';
alter table W_CM_IDX_HISTORY
  add constraint PK_W_CM_IDX_HISTORY primary key (HIS_TABLE);
create table W_CM_LOG_BUSDATA_FILE
(
  balance_water_no CHAR(10) not null,
  file_type        VARCHAR2(6) not null,
  insert_time      DATE
);
alter table W_CM_LOG_BUSDATA_FILE
  add constraint PK_W_CM_LOG_BUSDATA_FILE primary key (BALANCE_WATER_NO, FILE_TYPE);
create table W_CM_LOG_CLEAR_TABLE
(
  origin_table_name    VARCHAR2(30),
  dest_table_name      VARCHAR2(30),
  begin_clear_datetime CHAR(19),
  end_clear_datetime   CHAR(19),
  spent_time           VARCHAR2(8),
  clear_recd_count     NUMBER,
  err_discribe         VARCHAR2(1000),
  sql_label            VARCHAR2(1500)
);
comment on table W_CM_LOG_CLEAR_TABLE
  is '表数据清理配置日志表';
create table W_CM_LOG_COMMU
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
);
alter table W_CM_LOG_COMMU
  add constraint W_CM_LOG_COMMU_PK primary key (WATER_NO);
create table W_CM_LOG_CONNECT
(
  water_no         NUMBER(8) not null,
  connect_datetime DATE not null,
  connect_ip       VARCHAR2(15) not null,
  connect_result   CHAR(1),
  remark           VARCHAR2(100)
);
alter table W_CM_LOG_CONNECT
  add constraint PK_W_CM_LOG_CONNECT primary key (WATER_NO);
create table W_CM_LOG_FTP
(
  water_no     NUMBER(8) not null,
  datetime_ftp DATE not null,
  ip           VARCHAR2(15) not null,
  filename     VARCHAR2(50),
  start_time   DATE,
  spend_time   NUMBER(8),
  result       CHAR(1),
  remark       VARCHAR2(512)
);
create table W_CM_LOG_RECV_SEND
(
  water_no     NUMBER(10) not null,
  datetime_rec DATE not null,
  ip           VARCHAR2(15) not null,
  type         CHAR(1),
  message_code CHAR(2),
  message_sequ CHAR(22),
  message      BLOB,
  result       CHAR(1)
);
alter table W_CM_LOG_RECV_SEND
  add constraint PK_W_CM_LOG_RECV_SEND primary key (WATER_NO);
create table W_CM_PRM_DW_AUTO_CONFIG
(
  parm_type_id  CHAR(4) not null,
  cfg_year      VARCHAR2(100) not null,
  cfg_month     VARCHAR2(100) not null,
  cfg_date      VARCHAR2(100) not null,
  cfg_hour      VARCHAR2(100) not null,
  cfg_minute    VARCHAR2(100) not null,
  download_flag VARCHAR2(1) not null,
  remark        VARCHAR2(50)
);
alter table W_CM_PRM_DW_AUTO_CONFIG
  add constraint W_CM_PRM_DW_AUTO_CONFIG_PK primary key (PARM_TYPE_ID);
create table W_CM_PRM_DW_AUTO_CONFIG_NUM
(
  parm_type_id     CHAR(4) not null,
  min_download_num INTEGER not null,
  remark           VARCHAR2(50)
);
alter table W_CM_PRM_DW_AUTO_CONFIG_NUM
  add constraint W_CM_PRM_DW_AUTO_CONFIG_NUM_PK primary key (PARM_TYPE_ID);
create table W_CM_PRM_DW_AUTO_LOG
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
);
alter table W_CM_PRM_DW_AUTO_LOG
  add constraint W_CM_PRM_DW_AUTO_LOG_PK primary key (WATER_NO);
create table W_CM_PRM_DW_AUTO_LOG_WATERNO
(
  water_no INTEGER not null
);
create table W_CM_QUE_MESSAGE
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
);
alter table W_CM_QUE_MESSAGE
  add constraint W_CM_QUE_MESSAGE_PK primary key (MESSAGE_ID);
create table W_CM_SEQ_TK_FILE
(
  file_type VARCHAR2(10),
  seq       INTEGER,
  alter_day CHAR(8),
  remark    VARCHAR2(50)
);
comment on table W_CM_SEQ_TK_FILE
  is '票库通讯接口文件序号记录表';
comment on column W_CM_SEQ_TK_FILE.file_type
  is '文件类型';
comment on column W_CM_SEQ_TK_FILE.seq
  is '当前序号';
comment on column W_CM_SEQ_TK_FILE.alter_day
  is '修改日期';
comment on column W_CM_SEQ_TK_FILE.remark
  is '备注';
create table W_CM_SYS_VERSION
(
  version_no  VARCHAR2(10) not null,
  operator_id VARCHAR2(10),
  valid_date  CHAR(10),
  del_desc    VARCHAR2(255),
  update_desc VARCHAR2(255),
  add_desc    VARCHAR2(255),
  note        VARCHAR2(255)
);
alter table W_CM_SYS_VERSION
  add constraint PK_W_CM_SYS_VERSION_1 primary key (VERSION_NO);
create table W_CM_TK_FILE_RECV
(
  water_no  INTEGER not null,
  file_name VARCHAR2(30),
  file_path VARCHAR2(30),
  状态        INTEGER,
  remark    VARCHAR2(50)
);
comment on column W_CM_TK_FILE_RECV.状态
  is '0：未处理
1：正在处理
2：文件格式有问题
3：已处理';
alter table W_CM_TK_FILE_RECV
  add constraint PK_W_CM_TK_FILE_RECV primary key (WATER_NO);
create table W_CM_TRAFFIC
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
);
alter table W_CM_TRAFFIC
  add constraint PK_W_CM_TRAFFIC primary key (MESSAGE_SEQU);
create table W_CM_TRAFFIC_AFC_MIN
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
);
alter table W_CM_TRAFFIC_AFC_MIN
  add constraint PK_W_CM_TRAFFIC_AFC_MIN primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG);
create table W_CM_TRAFFIC_AFC_MIN_TOTAL
(
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  traffic_datetime VARCHAR2(12) not null,
  card_main_type   VARCHAR2(2) not null,
  card_sub_type    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic          INTEGER
);
alter table W_CM_TRAFFIC_AFC_MIN_TOTAL
  add constraint PK_W_CM_TRAFFIC_AFC_MIN_TOTAL primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG);
create table W_CM_TRAFFIC_DETAIL
(
  message_sequ     CHAR(22) not null,
  line_id          VARCHAR2(2) not null,
  station_id       VARCHAR2(2) not null,
  card_main_code   VARCHAR2(2) not null,
  card_sub_code    VARCHAR2(2) not null,
  flag             VARCHAR2(2) not null,
  traffic_datetime DATE not null,
  traffic          INTEGER
);
alter table W_CM_TRAFFIC_DETAIL
  add constraint PK_W_CM_TRAFFIC_DETAIL primary key (MESSAGE_SEQU, LINE_ID, STATION_ID, CARD_MAIN_CODE, CARD_SUB_CODE, FLAG, TRAFFIC_DATETIME);
create table W_CM_TRAFFIC_TVM
(
  message_sequ     CHAR(22) not null,
  traffic_datetime DATE,
  line_id          CHAR(2),
  station_id       CHAR(2),
  card_main_code   CHAR(2),
  card_sub_code    CHAR(2),
  traffic          INTEGER
);
alter table W_CM_TRAFFIC_TVM
  add constraint PK_W_CM_TRAFFIC_TVM primary key (MESSAGE_SEQU);
create table W_CM_TRD_MAX_HDLTIME
(
  msg_id       VARCHAR2(10) not null,
  max_hdl_time INTEGER not null,
  remark       VARCHAR2(50) not null
);
alter table W_CM_TRD_MAX_HDLTIME
  add constraint W_CM_TRD_MAX_HDLTIME_PK primary key (MSG_ID);
create table W_CM_TRD_MONITOR
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
alter table W_CM_TRD_MONITOR
  add constraint W_CM_TRD_MONITOR_PK primary key (THREAD_ID);
create table W_CM_TRD_MONITOR_HIS
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
alter table W_CM_TRD_MONITOR_HIS
  add constraint W_CM_TRD_MONITOR_HIS_PK primary key (WATER_NO);
create table W_CM_TRD_MSG_DEL
(
  water_no   NUMBER(18) not null,
  del_date   DATE not null,
  thread_id  VARCHAR2(50) not null,
  queue_type VARCHAR2(1) not null,
  msg_id     VARCHAR2(2) not null,
  message    BLOB not null,
  remark     VARCHAR2(50)
);
alter table W_CM_TRD_MSG_DEL
  add constraint W_CM_TRD_MSG_DEL primary key (WATER_NO);
create table W_CM_TRD_MSG_HANDUP_NUM
(
  water_no     NUMBER(18) not null,
  msg_id       VARCHAR2(2) not null,
  hand_up_num  INTEGER not null,
  hand_up_date DATE not null,
  remark       VARCHAR2(50) not null
);
alter table W_CM_TRD_MSG_HANDUP_NUM
  add constraint W_CM_TRD_MSG_HANDUP_NUM_PK primary key (WATER_NO);
create table W_CM_TRD_RESET
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
);
