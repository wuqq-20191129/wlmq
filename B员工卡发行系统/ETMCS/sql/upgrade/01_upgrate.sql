-- 添加工卡修改功能
insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7303', '票卡修改', 'com.goldsign.etmcs.ui.panel.EditCardPanel', '7303.png', '73', '73', '0', '07', '', 'M');

commit;

alter table acc_tk.ic_et_issue
add update_oper VARCHAR2(10);
alter table acc_tk.ic_et_issue
add update_time DATE;

comment on table acc_tk.ic_et_issue
  is '员工发行系统记录表';
comment on column acc_tk.ic_et_issue.update_oper
  is '更新操作员';
comment on column acc_tk.ic_et_issue.update_time
  is '更新时间';


-- 创建版本记录表
create table acc_tk.ic_et_sys_version
(
  VERSION_NO  VARCHAR2(10) not null,
  OPERATOR_ID VARCHAR2(10),
  VALID_DATE  CHAR(10),
  DEL_DESC    VARCHAR2(255),
  UPDATE_DESC VARCHAR2(255),
  ADD_DESC    VARCHAR2(255),
  NOTE        VARCHAR2(255)
);
comment on table acc_tk.ic_et_sys_version
  is '员工票发行系统版本记录表';
comment on column acc_tk.ic_et_sys_version.VERSION_NO
  is '版本号';
comment on column acc_tk.ic_et_sys_version.OPERATOR_ID
  is '操作员';
comment on column acc_tk.ic_et_sys_version.VALID_DATE
  is '更新日期';
comment on column acc_tk.ic_et_sys_version.DEL_DESC
  is '删除描述';
comment on column acc_tk.ic_et_sys_version.UPDATE_DESC
  is '更新描述';
comment on column acc_tk.ic_et_sys_version.ADD_DESC
  is '增加描述';
comment on column acc_tk.ic_et_sys_version.NOTE
  is '登记';

-- 插入初始版本信息
insert into acc_tk.ic_et_sys_version (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.0', 'lindq', sysdate, null, null, '初始版本', null);
insert into acc_tk.ic_et_sys_version (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.1', 'lindq', sysdate, null, '更改通讯系统取密钥，员工卡信息修改，一工号多卡限制开放。', null, null);
commit;

-- 员工卡发行系统配置信息修改 - 添加老人证、员工证，更新“其他”为9
update acc_tk.IC_ET_PUB_FLAG set code_text='老人证' where type ='1' and code='4';
insert into acc_tk.IC_ET_PUB_FLAG values ( 1,9 ,'其他', '证件类型');
insert into acc_tk.IC_ET_PUB_FLAG values ( 1,5 ,'员工证', '证件类型');
commit;