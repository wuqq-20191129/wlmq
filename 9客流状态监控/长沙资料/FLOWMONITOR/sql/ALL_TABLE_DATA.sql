-- Create table
create global temporary table T#FM_TRAFFIC_ON_OUT
(
  LINE_ID     CHAR(2) not null,
  STATION_ID  CHAR(100) not null,
  TRAFFIC_IN  INTEGER,
  TRAFFIC_OUT INTEGER
)
on commit preserve rows;
-- Add comments to the table 
comment on table T#FM_TRAFFIC_ON_OUT
  is '状态客流监控 客流统计临时表';
-- Add comments to the columns 
comment on column T#FM_TRAFFIC_ON_OUT.LINE_ID
  is '线路';
comment on column T#FM_TRAFFIC_ON_OUT.STATION_ID
  is '车站';
comment on column T#FM_TRAFFIC_ON_OUT.TRAFFIC_IN
  is '入站总人数';
comment on column T#FM_TRAFFIC_ON_OUT.TRAFFIC_OUT
  is '出站总人数';

create table FM_CONFIG
(
  CONFIG_NAME  VARCHAR2(100) not null,
  CONFIG_VALUE VARCHAR2(100) not null,
  REMARK       VARCHAR2(200)
);
comment on table FM_CONFIG
  is '状态客流监视系统运行时间配置表';
comment on column FM_CONFIG.CONFIG_NAME
  is '配置名';
comment on column FM_CONFIG.CONFIG_VALUE
  is '配置值';
comment on column FM_CONFIG.REMARK
  is '备注';


create table FM_DEV_MONITOR
(
  NODE_ID         CHAR(8) not null,
  DESCRIPTION     VARCHAR2(100),
  POS_X           INTEGER not null,
  POS_Y           INTEGER not null,
  START_X         INTEGER not null,
  START_Y         INTEGER not null,
  END_X           INTEGER not null,
  END_Y           INTEGER not null,
  NODE_TYPE       CHAR(2) not null,
  DEVICE_ID       VARCHAR2(4),
  DEV_TYPE_ID     CHAR(2),
  STATION_ID      VARCHAR2(2) not null,
  LINE_ID         VARCHAR2(2) not null,
  IMAGE_URL       VARCHAR2(50),
  FONTSIZE        CHAR(1),
  ID_X            INTEGER,
  ID_Y            INTEGER,
  IMAGE_DIRECTION CHAR(2)
);
comment on table FM_DEV_MONITOR
  is '状态客流监视系统车站图片表';
comment on column FM_DEV_MONITOR.POS_X
  is 'x坐标';
comment on column FM_DEV_MONITOR.POS_Y
  is 'y坐标';
comment on column FM_DEV_MONITOR.START_X
  is '线条开始x坐标';
comment on column FM_DEV_MONITOR.START_Y
  is '线条开始y坐标';
comment on column FM_DEV_MONITOR.END_X
  is '线条结束x坐标';
comment on column FM_DEV_MONITOR.END_Y
  is '线条结束y坐标';
comment on column FM_DEV_MONITOR.NODE_TYPE
  is '类型 01动态图,02静态图,03文字,04线条';
comment on column FM_DEV_MONITOR.DEVICE_ID
  is '设备ID';
comment on column FM_DEV_MONITOR.DEV_TYPE_ID
  is '设备类型';
comment on column FM_DEV_MONITOR.STATION_ID
  is '车站';
comment on column FM_DEV_MONITOR.LINE_ID
  is '线路';
comment on column FM_DEV_MONITOR.IMAGE_URL
  is '图片路径';
comment on column FM_DEV_MONITOR.FONTSIZE
  is '文字大小';
alter table FM_DEV_MONITOR
  add primary key (NODE_ID);


create table FM_DEV_STATUS_IMAGE
(
  DEV_TYPE_ID     CHAR(2) not null,
  STATUS          CHAR(2) not null,
  IMAGE_DIRECTION CHAR(2) not null,
  IMAGE_URL       VARCHAR2(100),
  DESCRIPTION     VARCHAR2(100),
  DEVICE_SUB_TYPE CHAR(1) not null
);
comment on table FM_DEV_STATUS_IMAGE
  is '状态客流监视系统图片状态对照表';
comment on column FM_DEV_STATUS_IMAGE.DEV_TYPE_ID
  is '设备类型';
comment on column FM_DEV_STATUS_IMAGE.STATUS
  is '状态';
comment on column FM_DEV_STATUS_IMAGE.IMAGE_DIRECTION
  is '图片描述';
comment on column FM_DEV_STATUS_IMAGE.IMAGE_URL
  is '图片路径';
comment on column FM_DEV_STATUS_IMAGE.DESCRIPTION
  is '说明';
alter table FM_DEV_STATUS_IMAGE
  add constraint PRIMARY_KEY_FM_DSI primary key (DEV_TYPE_ID, STATUS, IMAGE_DIRECTION, DEVICE_SUB_TYPE);


create table FM_PASSWORD
(
  EXIT_PASS VARCHAR2(100) not null
);
comment on table FM_PASSWORD
  is '状态客流监视系统退出密码';
comment on column FM_PASSWORD.EXIT_PASS
  is '退出密码';



insert into FM_CONFIG (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('reserve_days', '7', '保留记录天数');
insert into FM_CONFIG (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('runTime', '0200', '运行时间点');
insert into FM_CONFIG (CONFIG_NAME, CONFIG_VALUE, REMARK)
values ('threadInterval', '300000', '线程运行时间间隔');
commit;

--通讯同步状态表
create table CM_DEV_CURRENT_STATUS
(
  LINE_ID          VARCHAR2(2) not null,
  STATION_ID       VARCHAR2(2) not null,
  DEV_TYPE_ID      VARCHAR2(2) not null,
  DEVICE_ID        VARCHAR2(4) not null,
  STATUS_ID        VARCHAR2(4),
  STATUS_VALUE     VARCHAR2(3),
  STATUS_DATETIME  DATE,
  ACC_STATUS_VALUE VARCHAR2(1),
  OPER_ID          VARCHAR2(6)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table CM_DEV_CURRENT_STATUS
  add constraint PK_CM_DEV_CURRENT_STATUS primary key (LINE_ID, STATION_ID, DEV_TYPE_ID, DEVICE_ID);


--客流量数据表
-- Create table
create table CM_TRAFFIC_AFC_MIN
(
  LINE_ID          VARCHAR2(2) not null,
  STATION_ID       VARCHAR2(2) not null,
  TRAFFIC_DATETIME VARCHAR2(12) not null,
  CARD_MAIN_TYPE   VARCHAR2(2) not null,
  CARD_SUB_TYPE    VARCHAR2(2) not null,
  FLAG             VARCHAR2(2) not null,
  TRAFFIC          INTEGER
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table CM_TRAFFIC_AFC_MIN
  add constraint PK_CM_TRAFFIC_AFC_MIN primary key (LINE_ID, STATION_ID, TRAFFIC_DATETIME, CARD_MAIN_TYPE, CARD_SUB_TYPE, FLAG);