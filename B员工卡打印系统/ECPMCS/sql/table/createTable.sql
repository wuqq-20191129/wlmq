-- 创建序列
-- 创建打印记录序列
create sequence acc_tk.SEQ_IC_SCP_PRINT_LIST
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1;

-- 创建日志序列
create sequence acc_tk.SEQ_IC_SCP_OPRTLOG
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1;


-- 日志表
create table acc_tk.IC_SCP_OPRTLOG
(
  WATER_NO     NUMBER(18) not null,
  OPER_ID      VARCHAR2(10),
  OPRT_TIME    DATE,
  OPRT_CONTENT VARCHAR2(256),
  OPRT_RESULT  CHAR(1)
);
comment on table acc_tk.IC_SCP_OPRTLOG
  is '票面打印系统日志表';
comment on column acc_tk.IC_SCP_OPRTLOG.WATER_NO
  is '流水';
comment on column acc_tk.IC_SCP_OPRTLOG.OPER_ID
  is '操作员ID';
comment on column acc_tk.IC_SCP_OPRTLOG.OPRT_TIME
  is '操作时间';
comment on column acc_tk.IC_SCP_OPRTLOG.OPRT_CONTENT
  is '操作说明';
comment on column acc_tk.IC_SCP_OPRTLOG.OPRT_RESULT
  is '操作结果';
alter table acc_tk.IC_SCP_OPRTLOG
  add primary key (WATER_NO);


-- 创建打印记录表
create table acc_tk.IC_SCP_PRINT_LIST
(
  ID            VARCHAR2(8) not null,
  NAME          VARCHAR2(30),
  GENDER        CHAR(1) default 1,
  IDENTITY_ID   VARCHAR2(20) default 00000000000000000 not null,
  IDENTITY_TYPE VARCHAR2(2),
  CARD_TYPE     VARCHAR2(4),
  PHOTO_NAME    VARCHAR2(50),
  PRINT_OPER    VARCHAR2(20),
  PRINT_TIME    DATE,
  ISSUE_TIME    DATE,
  REMARK        VARCHAR2(100),
  DEPARTMENT    VARCHAR2(50)
);
comment on table acc_tk.IC_SCP_PRINT_LIST
  is '票面打印系统打印记录表';
comment on column acc_tk.IC_SCP_PRINT_LIST.NAME
  is '姓名';
comment on column acc_tk.IC_SCP_PRINT_LIST.GENDER
  is '性别';
comment on column acc_tk.IC_SCP_PRINT_LIST.IDENTITY_ID
  is '证件号';
comment on column acc_tk.IC_SCP_PRINT_LIST.IDENTITY_TYPE
  is '证件类型1:身份证,2:学生证,3:军人证,4:其他';
comment on column acc_tk.IC_SCP_PRINT_LIST.CARD_TYPE
  is '票卡类型';
comment on column acc_tk.IC_SCP_PRINT_LIST.PHOTO_NAME
  is '相片名称';
comment on column acc_tk.IC_SCP_PRINT_LIST.PRINT_OPER
  is '操作员';
comment on column acc_tk.IC_SCP_PRINT_LIST.PRINT_TIME
  is '打印时间';
comment on column acc_tk.IC_SCP_PRINT_LIST.ISSUE_TIME
  is '发证日期';
comment on column acc_tk.IC_SCP_PRINT_LIST.REMARK
  is '备注';
comment on column acc_tk.IC_SCP_PRINT_LIST.DEPARTMENT
  is '单位';
alter table acc_tk.IC_SCP_PRINT_LIST
  add primary key (ID);


-- 创建配置表
create table acc_tk.IC_SCP_PUB_FLAG
(
  TYPE        VARCHAR2(50) not null,
  CODE        VARCHAR2(20) not null,
  CODE_TEXT   VARCHAR2(50),
  DESCRIPTION VARCHAR2(100)
);
comment on table acc_tk.IC_SCP_PUB_FLAG
  is '票面打印系统配置表';
comment on column acc_tk.IC_SCP_PUB_FLAG.TYPE
  is '类型代码';
comment on column acc_tk.IC_SCP_PUB_FLAG.CODE
  is '代码';
comment on column acc_tk.IC_SCP_PUB_FLAG.CODE_TEXT
  is '代码描述';
comment on column acc_tk.IC_SCP_PUB_FLAG.DESCRIPTION
  is '类型描述';


-- 创建版本记录表
create table acc_tk.IC_SCP_SYS_VERSION
(
  VERSION_NO  VARCHAR2(10) not null,
  OPERATOR_ID VARCHAR2(10),
  VALID_DATE  CHAR(10),
  DEL_DESC    VARCHAR2(255),
  UPDATE_DESC VARCHAR2(255),
  ADD_DESC    VARCHAR2(255),
  NOTE        VARCHAR2(255)
);
comment on table acc_tk.IC_SCP_SYS_VERSION
  is '票面打印系统版本记录表';
comment on column acc_tk.IC_SCP_SYS_VERSION.VERSION_NO
  is '版本号';
comment on column acc_tk.IC_SCP_SYS_VERSION.OPERATOR_ID
  is '操作员';
comment on column acc_tk.IC_SCP_SYS_VERSION.VALID_DATE
  is '更新日期';
comment on column acc_tk.IC_SCP_SYS_VERSION.DEL_DESC
  is '删除描述';
comment on column acc_tk.IC_SCP_SYS_VERSION.UPDATE_DESC
  is '更新描述';
comment on column acc_tk.IC_SCP_SYS_VERSION.ADD_DESC
  is '增加描述';
comment on column acc_tk.IC_SCP_SYS_VERSION.NOTE
  is '登记';


-- 插入初始版本信息
insert into acc_tk.IC_SCP_SYS_VERSION (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.00', 'lindq', sysdate, null, null, '初始版本', null);
commit;


-- 插入基本参数信息
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('1', '3', '军人证', '证件类型');
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('1', '2', '学生证', '证件类型');
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('1', '1', '身份证', '证件类型');
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('1', '4', '其他', '证件类型');
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('2', '2', '女', '性别');
insert into acc_tk.IC_SCP_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('2', '1', '男', '性别');
commit;
