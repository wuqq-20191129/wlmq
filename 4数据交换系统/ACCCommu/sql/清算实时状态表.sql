-- ����ʵʱ״̬��
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
values ('2017081001', '53', '52', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '������֧����ؽ���');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '10', '-1', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'FTP����ļ��·�');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '13', '10', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�ļ����뼰У��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '20', '13', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�ⲿϵͳ���ݵ���');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '21', '20', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�������������������');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '30', '21', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ݽ��㣨�ϲ����У�');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '33', '30', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ݽ��㣨��Ӫ�̣�');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '36', '33', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ݽ��㣨�����Ӫ�̣�');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '39', '36', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ݽ��㣨���м��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '42', '39', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�ⲿϵͳ����');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '45', '42', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '������ODͳ��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '48', '45', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '����ͳ��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '51', '48', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'LCC����');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '54', '53', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '��ơ��쳣ͳ��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '57', '54', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ͳ�Ʒ���');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '60', '57', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', 'SAM�Ϻš��غŷ���');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '63', '60', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�Ĵ���ͳ��');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '66', '63', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '��������');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '69', '66', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '���ݵ���ʷ');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '80', '69', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '������ơ��������·�');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '81', '80', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '��������������������');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '83', '81', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�ⲿ���ݵ���');

insert into W_ACC_ST.W_ST_SYS_FLOW_CURRENT (BALANCE_WATER_NO, STEP, STEP_PRE, FINISH_FLAG, ERROR_CODE, BEGIN_TIME, END_TIME, OPERATOR_ID, REMARK)
values ('2017081001', '52', '51', '0', '00', to_date('10-08-2017 08:40:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'system', '�ֻ�֧����ؽ���');

COMMIT;
