/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2017/9/1 15:51:32                            */
/*==============================================================*/


create sequence w_acc_rl.W_SEQ_W_RL_DISTANCE_OD
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
 cache 10
noorder;

create sequence w_acc_rl.W_SEQ_W_RL_LOG
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
 cache 10
noorder;

create sequence w_acc_rl.W_SEQ_W_RL_PARAMS_THRESHOLD
increment by 1
start with 21
 maxvalue 9999999999999999999999999999
 nominvalue
nocycle
noorder;

/*==============================================================*/
/* Table: W_RL_DISTANCE_CHANGE                                  */
/*==============================================================*/
create table w_acc_rl.W_RL_DISTANCE_CHANGE 
(
   OD_ID                NUMBER,
   PCHANGE_STATION_ID   char(2),
   PASS_LINE_OUT        char(2),
   PASS_LINE_IN         char(2),
   PASS_DISTANCE        NUMBER(13,4),
   NCHANGE_STATION_ID   char(2)
)
pctfree 10
initrans 1
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_DISTANCE_CHANGE is
'����վ��˾��';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.OD_ID is
'OD·��ID';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.PCHANGE_STATION_ID is
'��һ����վ(������վ)';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.PASS_LINE_OUT is
'������·';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.PASS_LINE_IN is
'������·';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.PASS_DISTANCE is
'����������·�˾�';

comment on column w_acc_rl.W_RL_DISTANCE_CHANGE.NCHANGE_STATION_ID is
'��һ����վ(������վ)';

/*==============================================================*/
/* Table: W_RL_DISTANCE_OD                                      */
/*==============================================================*/
create table w_acc_rl.W_RL_DISTANCE_OD 
(
   ID                   NUMBER               not null,
   O_LINE_ID            char(2),
   O_STATION_ID         char(2),
   E_LINE_ID            char(2),
   E_STATION_ID         char(2),
   PASS_STATIONS        char(2),
   PASS_TIME            NUMBER,
   CHANGE_TIMES         NUMBER,
   STATIONS_NUM         NUMBER,
   DISTANCE             NUMBER(13,4),
   VERSION              VARCHAR2(20),
   RECORD_FLAG          CHAR,
   CREATE_TIME          DATE,
   CREATE_OPERATOR      VARCHAR2(20),
   DISTANCE_PERCENT     NUMBER(8,6)          default 0,
   DISTANCE_BASE        NUMBER(13,4)         default 0,
   constraint PK_W_SR_OD_DISTANCE primary key (ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_DISTANCE_OD is
'OD·����';

comment on column w_acc_rl.W_RL_DISTANCE_OD.ID is
'���';

comment on column w_acc_rl.W_RL_DISTANCE_OD.O_LINE_ID is
'��ʼվ';

comment on column w_acc_rl.W_RL_DISTANCE_OD.O_STATION_ID is
'��ʼ��·';

comment on column w_acc_rl.W_RL_DISTANCE_OD.E_LINE_ID is
'����վ';

comment on column w_acc_rl.W_RL_DISTANCE_OD.E_STATION_ID is
'������·';

comment on column w_acc_rl.W_RL_DISTANCE_OD.PASS_STATIONS is
'����վ��';

comment on column w_acc_rl.W_RL_DISTANCE_OD.PASS_TIME is
'�˳���ʱ��';

comment on column w_acc_rl.W_RL_DISTANCE_OD.CHANGE_TIMES is
'�����ܴ���';

comment on column w_acc_rl.W_RL_DISTANCE_OD.STATIONS_NUM is
'��վ��';

comment on column w_acc_rl.W_RL_DISTANCE_OD.DISTANCE is
'�������';

comment on column w_acc_rl.W_RL_DISTANCE_OD.VERSION is
'�汾';

comment on column w_acc_rl.W_RL_DISTANCE_OD.RECORD_FLAG is
'��¼��־';

comment on column w_acc_rl.W_RL_DISTANCE_OD.CREATE_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_DISTANCE_OD.CREATE_OPERATOR is
'������';

comment on column w_acc_rl.W_RL_DISTANCE_OD.DISTANCE_PERCENT is
'��ʱ��·��̯����';

comment on column w_acc_rl.W_RL_DISTANCE_OD.DISTANCE_BASE is
'��ʱ��̯����';

/*==============================================================*/
/* Table: W_RL_LOG                                              */
/*==============================================================*/
create table w_acc_rl.W_RL_LOG 
(
   SERIALNO             NUMBER(18),
   OPERATOR_ID          VARCHAR2(8)          not null,
   OP_TIME              DATE                 not null,
   MODULE_ID            VARCHAR2(6)          not null,
   OPER_TYPE            VARCHAR2(50)         not null,
   DESCRIPTION          VARCHAR2(200),
   SYS_TYPE             VARCHAR2(2)          default '0'
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_LOG is
'��ֹ�����־��';

comment on column w_acc_rl.W_RL_LOG.SERIALNO is
'��ˮ��';

comment on column w_acc_rl.W_RL_LOG.OPERATOR_ID is
'����Ա';

comment on column w_acc_rl.W_RL_LOG.OP_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_LOG.MODULE_ID is
'ģ��';

comment on column w_acc_rl.W_RL_LOG.OPER_TYPE is
'��������';

comment on column w_acc_rl.W_RL_LOG.DESCRIPTION is
'����';

comment on column w_acc_rl.W_RL_LOG.SYS_TYPE is
'ϵͳ����';

/*==============================================================*/
/* Table: W_RL_PARAMS_STATION                                   */
/*==============================================================*/
create table w_acc_rl.W_RL_PARAMS_STATION 
(
   P_STATION_ID         CHAR(2)              not null,
   N_STATION_ID         CHAR(2)              not null,
   LINE_ID              CHAR(2)              not null,
   RECORD_FLAG          CHAR(1),
   VERSION              VARCHAR2(20)         not null,
   MILEAGE              NUMBER(13,4),
   CREATE_TIME          DATE,
   CREATE_OPERATOR      VARCHAR2(20),
   N_T_STATION_ID       CHAR(2)              not null,
   constraint PROMARY_SR_PARAMS_STATION primary key (LINE_ID, P_STATION_ID, N_STATION_ID, VERSION)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_PARAMS_STATION is
'��·վ�����������';

comment on column w_acc_rl.W_RL_PARAMS_STATION.P_STATION_ID is
'��ǰվ';

comment on column w_acc_rl.W_RL_PARAMS_STATION.N_STATION_ID is
'��һվ';

comment on column w_acc_rl.W_RL_PARAMS_STATION.LINE_ID is
'��·';

comment on column w_acc_rl.W_RL_PARAMS_STATION.RECORD_FLAG is
'���״̬ 0:��Ч״̬';

comment on column w_acc_rl.W_RL_PARAMS_STATION.VERSION is
'�汾';

comment on column w_acc_rl.W_RL_PARAMS_STATION.MILEAGE is
'�������km��';

comment on column w_acc_rl.W_RL_PARAMS_STATION.CREATE_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_PARAMS_STATION.CREATE_OPERATOR is
'������';

comment on column w_acc_rl.W_RL_PARAMS_STATION.N_T_STATION_ID is
'��һ����վ';

/*==============================================================*/
/* Table: W_RL_PARAMS_SYS                                       */
/*==============================================================*/
create table w_acc_rl.W_RL_PARAMS_SYS 
(
   TYPE_CODE            VARCHAR2(2)          not null,
   TYPE_DESCRIPTION     VARCHAR2(100),
   CODE                 VARCHAR2(2)          not null,
   VALUE                VARCHAR2(100),
   DESCRIPTION          VARCHAR2(100),
   RECORD_FLAG          CHAR(1)              not null,
   VERSION              VARCHAR2(20)         not null,
   CREATE_TIME          DATE,
   CREATE_OPERATOR      VARCHAR2(20),
   constraint PRIMARY_SR_PARAMS_SYS primary key (TYPE_CODE, CODE, VERSION, RECORD_FLAG)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_PARAMS_SYS is
'ϵͳ������';

comment on column w_acc_rl.W_RL_PARAMS_SYS.TYPE_CODE is
'���ʹ���';

comment on column w_acc_rl.W_RL_PARAMS_SYS.TYPE_DESCRIPTION is
'��������';

comment on column w_acc_rl.W_RL_PARAMS_SYS.CODE is
'��������';

comment on column w_acc_rl.W_RL_PARAMS_SYS.VALUE is
'����ֵ';

comment on column w_acc_rl.W_RL_PARAMS_SYS.DESCRIPTION is
'��������';

comment on column w_acc_rl.W_RL_PARAMS_SYS.RECORD_FLAG is
'���״̬ 0:��Ч״̬';

comment on column w_acc_rl.W_RL_PARAMS_SYS.VERSION is
'�汾';

comment on column w_acc_rl.W_RL_PARAMS_SYS.CREATE_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_PARAMS_SYS.CREATE_OPERATOR is
'������';

/*==============================================================*/
/* Table: W_RL_PARAMS_THRESHOLD                                 */
/*==============================================================*/
create table w_acc_rl.W_RL_PARAMS_THRESHOLD 
(
   DISTANCE_THRES       NUMBER(8,4),
   STATION_THRES        NUMBER,
   CHANGE_THRES         NUMBER,
   TIME_THRES           NUMBER,
   DESCRIPTION          VARCHAR2(100),
   RECORD_FLAG          CHAR(1)              not null,
   VERSION              VARCHAR2(20)         not null,
   UPDATE_TIME          DATE,
   UPDATE_OPERATOR      VARCHAR2(20),
   ID                   NUMBER               not null,
   constraint SR_PARAMS_THRESHOLD_PKEY primary key (ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_PARAMS_THRESHOLD is
'��ֵ������';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.DISTANCE_THRES is
'��̲������ֵ';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.STATION_THRES is
'վ��ֵ';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.CHANGE_THRES is
'���˴�����ֵ';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.TIME_THRES is
'�˳�ʱ�䣨s����ֵ';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.DESCRIPTION is
'����';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.RECORD_FLAG is
'��ʷ״̬';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.VERSION is
'�汾';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.UPDATE_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.UPDATE_OPERATOR is
'������';

comment on column w_acc_rl.W_RL_PARAMS_THRESHOLD.ID is
'���';

/*==============================================================*/
/* Table: W_RL_PROPORTION                                       */
/*==============================================================*/
create table w_acc_rl.W_RL_PROPORTION 
(
   VERSION              VARCHAR2(20)         not null,
   RECORD_FLAG          CHAR(1)              not null,
   O_STATION_ID         char(2)              not null,
   O_LINE_ID            char(2)              not null,
   D_STATION_ID         char(2)              not null,
   D_LINE_ID            char(2)              not null,
   DISPART_LINE_ID      char(2)              not null,
   IN_PERCENT           NUMBER(8,6),
   CREATE_TIME          DATE,
   CREATE_OPERATOR      VARCHAR2(20),
   constraint PK_W_RL_PROPORTION primary key (O_STATION_ID, D_STATION_ID, DISPART_LINE_ID, VERSION, RECORD_FLAG, O_LINE_ID, D_LINE_ID)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_PROPORTION is
'OD���Ȩ�ز�ѯ���';

comment on column w_acc_rl.W_RL_PROPORTION.VERSION is
'�汾';

comment on column w_acc_rl.W_RL_PROPORTION.RECORD_FLAG is
'���״̬ 0:��Ч״̬';

comment on column w_acc_rl.W_RL_PROPORTION.O_STATION_ID is
'��ʼվ��';

comment on column w_acc_rl.W_RL_PROPORTION.O_LINE_ID is
'��ʼ·��';

comment on column w_acc_rl.W_RL_PROPORTION.D_STATION_ID is
'����վ��';

comment on column w_acc_rl.W_RL_PROPORTION.D_LINE_ID is
'������·';

comment on column w_acc_rl.W_RL_PROPORTION.DISPART_LINE_ID is
'Ȩ��·��';

comment on column w_acc_rl.W_RL_PROPORTION.IN_PERCENT is
'Ȩ�ر���';

comment on column w_acc_rl.W_RL_PROPORTION.CREATE_TIME is
'����ʱ��';

comment on column w_acc_rl.W_RL_PROPORTION.CREATE_OPERATOR is
'������';

/*==============================================================*/
/* Table: W_RL_SYS_VERSION                                      */
/*==============================================================*/
create table w_acc_rl.W_RL_SYS_VERSION 
(
   VERSION_NO           VARCHAR2(10)         not null,
   OPERATOR_ID          VARCHAR2(10),
   VALID_DATE           CHAR(10),
   DEL_DESC             VARCHAR2(255),
   UPDATE_DESC          VARCHAR2(255),
   ADD_DESC             VARCHAR2(255),
   NOTE                 VARCHAR2(255),
   constraint SR_VERSION_PK_1 primary key (VERSION_NO)
         using index
       pctfree 10
       initrans 2
       storage
       (
           initial 64K
           next 1024K
           minextents 1
           maxextents unlimited
       )
       tablespace W_TBS_ST
        logging
)
pctfree 10
initrans 1
storage
(
    initial 64K
    next 1024K
    minextents 1
    maxextents unlimited
)
tablespace W_TBS_ST
logging
monitoring
 noparallel;

comment on table w_acc_rl.W_RL_SYS_VERSION is
'ϵͳ�汾��¼��';

/*==============================================================*/
/* Table: W_T#RL_MIN_AVG_DISTANCE                               */
/*==============================================================*/
create global temporary table w_acc_rl.W_T#RL_MIN_AVG_DISTANCE 
(
   O_LINE_ID            char(2),
   O_STATION_ID         char(2),
   E_LINE_ID            char(2),
   E_STATION_ID         char(2),
   MIN_DISTANCE         NUMBER(13,4),
   AVG_DISTANCE         NUMBER(13,4)
)
noparallel;

comment on table w_acc_rl.W_T#RL_MIN_AVG_DISTANCE is
'��ֹ���Ȩ�ؼ�����ʱ��';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.O_LINE_ID is
'��ʼ��·';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.O_STATION_ID is
'��ʼվ��';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.E_LINE_ID is
'������·';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.E_STATION_ID is
'����վ��';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.MIN_DISTANCE is
'��С�˾�';

comment on column w_acc_rl.W_T#RL_MIN_AVG_DISTANCE.AVG_DISTANCE is
'ƽ���˾�';

