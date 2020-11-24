-- 清算实时状态表
create table W_ACC_ST.W_ST_SYS_FLOW_CURRENT
(
  balance_water_no CHAR(10) not null,
  step             CHAR(2) not null,
  step_pre         CHAR(2),
  finish_flag      CHAR(1),
  error_code       CHAR(2),
  begin_time       DATE,
  end_time         DATE,
  operator_id      CHAR(6),
  remark           VARCHAR2(50)
);
-- Grant/Revoke object privileges 
grant select, insert, update, delete on W_ACC_ST.W_ST_SYS_FLOW_CURRENT to W_ACC_ST_APP;
grant select on W_ACC_ST.W_ST_SYS_FLOW_CURRENT to W_ACC_ST_DEV;
grant select on W_ACC_ST.W_ST_SYS_FLOW_CURRENT to W_ACC_ST_RPT;


insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '53', '52', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '互联网支付相关结算');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '10', '-1', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'FTP审计文件下发');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '13', '10', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '文件导入及校验');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '20', '13', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '外部系统数据导出');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '21', '20', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '导入地铁公交消费数据');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '30', '21', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '数据结算（合并队列）');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '33', '30', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '数据结算（运营商）');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '36', '33', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '数据结算（最大运营商）');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '39', '36', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '数据结算（导中间表）');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '42', '39', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '外部系统对账');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '45', '42', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '客流、OD统计');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '48', '45', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '收益统计');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '51', '48', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'LCC对账');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '54', '53', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '审计、异常统计');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '57', '54', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '余额统计分析');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '60', '57', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'SAM断号、重号分析');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '63', '60', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '寄存器统计');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '66', '63', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '其他处理');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '69', '66', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '数据导历史');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '80', '69', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '错误审计、黑名单下发');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '81', '80', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '导出地铁公交结算数据');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '83', '81', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '外部数据导入');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '52', '51', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '手机支付相关结算');

COMMIT;
