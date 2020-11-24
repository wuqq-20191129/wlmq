create global temporary table T#SR_MIN_AVG_DISTANCE
(
  O_LINE_ID    VARCHAR2(2),
  O_STATION_ID VARCHAR2(2),
  E_LINE_ID    VARCHAR2(2),
  E_STATION_ID VARCHAR2(2),
  MIN_DISTANCE NUMBER(13,4),
  AVG_DISTANCE NUMBER(13,4)
)
on commit delete rows;
-- Add comments to the table 
comment on table T#SR_MIN_AVG_DISTANCE
  is '清分规则权重计算临时表';

create table SR_DISTANCE_CHANGE
(
  OD_ID              INTEGER,
  PCHANGE_STATION_ID VARCHAR2(2),
  PASS_LINE_OUT      VARCHAR2(2),
  PASS_LINE_IN       VARCHAR2(2),
  PASS_DISTANCE      NUMBER(13,4),
  NCHANGE_STATION_ID VARCHAR2(2)
);
comment on table SR_DISTANCE_CHANGE
  is '换乘站点乘距表';
comment on column SR_DISTANCE_CHANGE.OD_ID
  is 'OD路径ID';
comment on column SR_DISTANCE_CHANGE.PCHANGE_STATION_ID
  is '上一换乘站(包含起站)';
comment on column SR_DISTANCE_CHANGE.PASS_LINE_OUT
  is '换出线路';
comment on column SR_DISTANCE_CHANGE.PASS_LINE_IN
  is '换入线路';
comment on column SR_DISTANCE_CHANGE.PASS_DISTANCE
  is '经过换出线路乘距';
comment on column SR_DISTANCE_CHANGE.NCHANGE_STATION_ID
  is '下一换乘站(包含终站)';


create table SR_DISTANCE_OD
(
  ID               INTEGER not null,
  O_LINE_ID        VARCHAR2(2),
  O_STATION_ID     VARCHAR2(2),
  E_LINE_ID        VARCHAR2(2),
  E_STATION_ID     VARCHAR2(2),
  PASS_STATIONS    VARCHAR2(1000),
  PASS_TIME        INTEGER,
  CHANGE_TIMES     INTEGER,
  STATIONS_NUM     INTEGER,
  DISTANCE         NUMBER(13,4),
  VERSION          VARCHAR2(20),
  RECORD_FLAG      CHAR(1),
  CREATE_TIME      DATE,
  CREATE_OPERATOR  VARCHAR2(20),
  DISTANCE_PERCENT NUMBER(8,6) default 0,
  DISTANCE_BASE    NUMBER(13,4) default 0
);
comment on table SR_DISTANCE_OD
  is 'OD路径表';
comment on column SR_DISTANCE_OD.ID
  is 'id';
comment on column SR_DISTANCE_OD.O_LINE_ID
  is '起始站';
comment on column SR_DISTANCE_OD.O_STATION_ID
  is '起始线路';
comment on column SR_DISTANCE_OD.E_LINE_ID
  is '结束站';
comment on column SR_DISTANCE_OD.E_STATION_ID
  is '结束线路';
comment on column SR_DISTANCE_OD.PASS_STATIONS
  is '经过站点';
comment on column SR_DISTANCE_OD.PASS_TIME
  is '乘车总时间';
comment on column SR_DISTANCE_OD.CHANGE_TIMES
  is '换乘总次数';
comment on column SR_DISTANCE_OD.STATIONS_NUM
  is '总站数';
comment on column SR_DISTANCE_OD.DISTANCE
  is '总里程数';
comment on column SR_DISTANCE_OD.VERSION
  is '版本';
comment on column SR_DISTANCE_OD.RECORD_FLAG
  is '记录标志';
comment on column SR_DISTANCE_OD.CREATE_TIME
  is '创建时间';
comment on column SR_DISTANCE_OD.CREATE_OPERATOR
  is '创建人';
comment on column SR_DISTANCE_OD.DISTANCE_PERCENT
  is '临时线路分摊比例';
comment on column SR_DISTANCE_OD.DISTANCE_BASE
  is '临时分摊基数';
alter table SR_DISTANCE_OD
  add constraint PK_SR_OD_DISTANCE primary key (ID);


create table SR_LOG
(
  SERIALNO    NUMBER(18),
  OPERATOR_ID VARCHAR2(8) not null,
  OP_TIME     DATE not null,
  MODULE_ID   VARCHAR2(6) not null,
  OPER_TYPE   VARCHAR2(50) not null,
  DESCRIPTION VARCHAR2(200),
  SYS_TYPE    VARCHAR2(2) default 0
);
comment on table SR_LOG
  is '清分规则日志表';
comment on column SR_LOG.SERIALNO
  is '流水号';
comment on column SR_LOG.OPERATOR_ID
  is '操作员';
comment on column SR_LOG.OP_TIME
  is '操作时间';
comment on column SR_LOG.MODULE_ID
  is '模块';
comment on column SR_LOG.OPER_TYPE
  is '操作类型';
comment on column SR_LOG.DESCRIPTION
  is '描述';


create table SR_PARAMS_STATION
(
  P_STATION_ID    VARCHAR2(2) not null,
  N_STATION_ID    VARCHAR2(2) not null,
  LINE_ID         VARCHAR2(2) not null,
  RECORD_FLAG     CHAR(1),
  VERSION         VARCHAR2(20) not null,
  MILEAGE         NUMBER(13,4),
  CREATE_TIME     DATE,
  CREATE_OPERATOR VARCHAR2(20),
  N_T_STATION_ID  VARCHAR2(2) not null
);
comment on table SR_PARAMS_STATION
  is '线路站点基本参数表';
comment on column SR_PARAMS_STATION.P_STATION_ID
  is '当前站';
comment on column SR_PARAMS_STATION.N_STATION_ID
  is '下一站';
comment on column SR_PARAMS_STATION.LINE_ID
  is '线路';
comment on column SR_PARAMS_STATION.RECORD_FLAG
  is '审核状态 0:有效状态';
comment on column SR_PARAMS_STATION.VERSION
  is '版本';
comment on column SR_PARAMS_STATION.MILEAGE
  is '里程数（km）';
comment on column SR_PARAMS_STATION.CREATE_TIME
  is '创建时间';
comment on column SR_PARAMS_STATION.CREATE_OPERATOR
  is '创建人';
comment on column SR_PARAMS_STATION.N_T_STATION_ID
  is '下一换乘站';
alter table SR_PARAMS_STATION
  add constraint PROMARY_SR_PARAMS_STATION primary key (LINE_ID, P_STATION_ID, N_STATION_ID, VERSION);


create table SR_PARAMS_SYS
(
  TYPE_CODE        VARCHAR2(2) not null,
  TYPE_DESCRIPTION VARCHAR2(100),
  CODE             VARCHAR2(2) not null,
  VALUE            VARCHAR2(100),
  DESCRIPTION      VARCHAR2(100),
  RECORD_FLAG      CHAR(1) not null,
  VERSION          VARCHAR2(20) not null,
  CREATE_TIME      DATE,
  CREATE_OPERATOR  VARCHAR2(20)
);
comment on table SR_PARAMS_SYS
  is '系统参数表';
comment on column SR_PARAMS_SYS.TYPE_CODE
  is '类型代码';
comment on column SR_PARAMS_SYS.TYPE_DESCRIPTION
  is '类型描述';
comment on column SR_PARAMS_SYS.CODE
  is '参数代码';
comment on column SR_PARAMS_SYS.VALUE
  is '参数值';
comment on column SR_PARAMS_SYS.DESCRIPTION
  is '参数描述';
comment on column SR_PARAMS_SYS.RECORD_FLAG
  is '审核状态 0:有效状态';
comment on column SR_PARAMS_SYS.VERSION
  is '版本';
comment on column SR_PARAMS_SYS.CREATE_TIME
  is '创建时间';
comment on column SR_PARAMS_SYS.CREATE_OPERATOR
  is '创建人';
alter table SR_PARAMS_SYS
  add constraint PRIMARY_SR_PARAMS_SYS primary key (TYPE_CODE, CODE, VERSION, RECORD_FLAG);


create table SR_PARAMS_THRESHOLD
(
  DISTANCE_THRES  NUMBER(8,4),
  STATION_THRES   INTEGER,
  CHANGE_THRES    INTEGER,
  TIME_THRES      INTEGER,
  DESCRIPTION     VARCHAR2(100),
  RECORD_FLAG     CHAR(1) not null,
  VERSION         VARCHAR2(20) not null,
  UPDATE_TIME     DATE,
  UPDATE_OPERATOR VARCHAR2(20),
  ID              INTEGER not null
);
comment on table SR_PARAMS_THRESHOLD
  is '阀值参数表';
comment on column SR_PARAMS_THRESHOLD.DISTANCE_THRES
  is '里程差比例阀值';
comment on column SR_PARAMS_THRESHOLD.STATION_THRES
  is '站点差阀值';
comment on column SR_PARAMS_THRESHOLD.CHANGE_THRES
  is '换乘次数阀值';
comment on column SR_PARAMS_THRESHOLD.TIME_THRES
  is '乘车时间（s）阀值';
comment on column SR_PARAMS_THRESHOLD.DESCRIPTION
  is '描述';
comment on column SR_PARAMS_THRESHOLD.RECORD_FLAG
  is '历史状态';
comment on column SR_PARAMS_THRESHOLD.VERSION
  is '版本';
comment on column SR_PARAMS_THRESHOLD.UPDATE_TIME
  is '创建时间';
comment on column SR_PARAMS_THRESHOLD.UPDATE_OPERATOR
  is '创建人';
comment on column SR_PARAMS_THRESHOLD.ID
  is 'id';
alter table SR_PARAMS_THRESHOLD
  add constraint SR_PARAMS_THRESHOLD_PKEY primary key (ID);


create table SR_PROPORTION
(
  VERSION         VARCHAR2(20) not null,
  RECORD_FLAG     CHAR(1) not null,
  O_STATION_ID    VARCHAR2(2) not null,
  O_LINE_ID       VARCHAR2(2) not null,
  D_STATION_ID    VARCHAR2(2) not null,
  D_LINE_ID       VARCHAR2(2) not null,
  DISPART_LINE_ID VARCHAR2(2) not null,
  IN_PERCENT      NUMBER(8,6),
  CREATE_TIME     DATE,
  CREATE_OPERATOR VARCHAR2(20)
);
comment on table SR_PROPORTION
  is 'OD清分权重查询结果';
comment on column SR_PROPORTION.VERSION
  is '版本';
comment on column SR_PROPORTION.RECORD_FLAG
  is '审核状态 0:有效状态';
comment on column SR_PROPORTION.O_STATION_ID
  is '开始站点';
comment on column SR_PROPORTION.O_LINE_ID
  is '开始路线';
comment on column SR_PROPORTION.D_STATION_ID
  is '结束站点';
comment on column SR_PROPORTION.D_LINE_ID
  is '结束站点';
comment on column SR_PROPORTION.DISPART_LINE_ID
  is '权重路线';
comment on column SR_PROPORTION.IN_PERCENT
  is '权重比例';
comment on column SR_PROPORTION.CREATE_TIME
  is '创建时间';
comment on column SR_PROPORTION.CREATE_OPERATOR
  is '创建人';
alter table SR_PROPORTION
  add constraint SR_RESULT_PROP_PRIMARY_KEY primary key (O_STATION_ID, D_STATION_ID, DISPART_LINE_ID, VERSION, RECORD_FLAG, O_LINE_ID, D_LINE_ID);


create sequence SEQ_SR_PARAMS_THRESHOLD
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1;


create sequence SEQ_SR_DISTANCE_OD
minvalue 1
maxvalue 9999999999999999999999999999
start with 1501
increment by 1;


create sequence SEQ_SR_LOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 261
increment by 1;

insert into SR_PARAMS_SYS (TYPE_CODE, TYPE_DESCRIPTION, CODE, VALUE, DESCRIPTION, RECORD_FLAG, VERSION, CREATE_TIME, CREATE_OPERATOR)
values ('02', '路径是否有效', '0', '无效', null, '0', '20131112', to_date('18-11-2013 18:08:35', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into SR_PARAMS_SYS (TYPE_CODE, TYPE_DESCRIPTION, CODE, VALUE, DESCRIPTION, RECORD_FLAG, VERSION, CREATE_TIME, CREATE_OPERATOR)
values ('02', '路径是否有效', '1', '有效', null, '0', '20131112', to_date('16-11-2013 18:08:35', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into SR_PARAMS_SYS (TYPE_CODE, TYPE_DESCRIPTION, CODE, VALUE, DESCRIPTION, RECORD_FLAG, VERSION, CREATE_TIME, CREATE_OPERATOR)
values ('30', '版本标志', '0', '当前参数', null, '0', '20131111', to_date('18-11-2013 18:08:35', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into SR_PARAMS_SYS (TYPE_CODE, TYPE_DESCRIPTION, CODE, VALUE, DESCRIPTION, RECORD_FLAG, VERSION, CREATE_TIME, CREATE_OPERATOR)
values ('30', '版本标志', '3', '草稿参数', null, '0', '20131111', to_date('18-11-2013 18:08:35', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into SR_PARAMS_SYS (TYPE_CODE, TYPE_DESCRIPTION, CODE, VALUE, DESCRIPTION, RECORD_FLAG, VERSION, CREATE_TIME, CREATE_OPERATOR)
values ('30', '版本标志', '2', '历史参数', null, '0', '20131111', to_date('17-11-2013 18:08:35', 'dd-mm-yyyy hh24:mi:ss'), null);
commit;

insert into SR_PARAMS_THRESHOLD (DISTANCE_THRES, STATION_THRES, CHANGE_THRES, TIME_THRES, DESCRIPTION, RECORD_FLAG, VERSION, UPDATE_TIME, UPDATE_OPERATOR, ID)
values (0.2, 3, 4, 360, '标准', '0', '201311260929', to_date('26-11-2013 09:49:09', 'dd-mm-yyyy hh24:mi:ss'), 'lindq', 1);
commit;