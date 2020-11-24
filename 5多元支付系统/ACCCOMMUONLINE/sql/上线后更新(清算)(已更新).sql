-- Create table
create table w_acc_ol.W_OL_STS_ACC
(
  water_no           NUMBER(18) not null,
  squad_day          VARCHAR2(10) not null,
  line_id            CHAR(2),
  station_id         CHAR(2) default 00,
  lc_charge_fee      NUMBER(18,2) default 0,
  lc_charge_num      NUMBER(18) default 0,
  lc_return_fee      NUMBER(18,2) default 0,
  lc_return_num      NUMBER(18) default 0,
  air_sale_num       NUMBER(18) default 0,
  air_charge_fee     NUMBER(18,2) default 0,
  air_charge_num     NUMBER(18) default 0,
  air_return_fee     NUMBER(18,2) default 0,
  air_return_num     NUMBER(18) default 0,
  qrcode_hce_times   NUMBER(18) default 0,
  qrcode_hce_num     NUMBER(18) default 0,
  qrcode_hce_fee     NUMBER(18,2) default 0,
  qrcode_hce_c_times NUMBER(18) default 0,
  qrcode_hce_c_num   NUMBER(18) default 0,
  qrcode_hce_c_fee   NUMBER(18,2) default 0,
  qrcode_lc_times    NUMBER(18) default 0,
  qrcode_lc_num      NUMBER(18) default 0,
  qrcode_lc_fee      NUMBER(18,2) default 0,
  qrpay_hce_times    NUMBER(18) default 0,
  qrpay_hce_fee      NUMBER(18,2) default 0,
  qrpay_hce_c_times  NUMBER(18) default 0,
  qrpay_hce_c_fee    NUMBER(18,2) default 0,
  qrpay_lc_times     NUMBER(18) default 0,
  qrpay_lc_num       NUMBER(18) default 0,
  qrpay_lc_fee       NUMBER(18,2) default 0
);
comment on table w_acc_ol.W_OL_STS_ACC
  is '��Ԫ֧����ͳ�Ʊ�';
comment on column w_acc_ol.W_OL_STS_ACC.water_no
  is '���к�';
comment on column W_OL_STS_ACC.squad_day
  is '��Ӫ��';
comment on column w_acc_ol.W_OL_STS_ACC.line_id
  is '��·';
comment on column w_acc_ol.W_OL_STS_ACC.station_id
  is '��վ';
comment on column w_acc_ol.W_OL_STS_ACC.lc_charge_fee
  is '�ն˳�ֵ���';
comment on column w_acc_ol.W_OL_STS_ACC.lc_charge_num
  is '�ն˳�ֵ����';
comment on column w_acc_ol.W_OL_STS_ACC.lc_return_fee
  is '�ն˳�ֵ�������';
comment on column w_acc_ol.W_OL_STS_ACC.lc_return_num
  is '�ն˳�ֵ��������';
comment on column w_acc_ol.W_OL_STS_ACC.air_sale_num
  is '�շ�����';
comment on column w_acc_ol.W_OL_STS_ACC.air_charge_fee
  is '�ճ���';
comment on column w_acc_ol.W_OL_STS_ACC.air_charge_num
  is '�ճ����';
comment on column w_acc_ol.W_OL_STS_ACC.air_return_fee
  is '�ճ䳷�����';
comment on column w_acc_ol.W_OL_STS_ACC.air_return_num
  is '�ճ䳷������';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_hce_times
  is 'HCE��ά�붩����';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_hce_fee
  is 'HCE��ά����';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_hce_c_times
  is 'HCE��ά��ȡ��������';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_hce_c_fee
  is 'HCE��ά��ȡ�����';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_lc_times
  is '�ն˶�ά��ȡƱ������';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_lc_num
  is '�ն˶�ά��ȡƱƱ��';
comment on column w_acc_ol.W_OL_STS_ACC.qrcode_lc_fee
  is '�ն˶�ά��ȡƱ���';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_hce_times
  is 'HCE֧��������';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_hce_fee
  is 'HCE֧�����';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_hce_c_times
  is 'HCE֧��ȡ��������';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_hce_c_fee
  is 'HCE֧��ȡ�����';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_lc_times
  is '֧����ά���ն˶�����';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_lc_num
  is '֧����ά���ն�Ʊ��';
comment on column w_acc_ol.W_OL_STS_ACC.qrpay_lc_fee
  is '֧����ά���ն˽��';
grant select, insert, update, delete on w_acc_ol.W_OL_STS_ACC to W_ACC_OL_APP;
grant select on w_acc_ol.W_OL_STS_ACC to W_ACC_OL_DEV;
grant select on w_acc_ol.W_OL_STS_ACC to W_ACC_ST_RP_APP;


-- Create sequence 
create sequence w_acc_ol.W_SEQ_W_OL_STS_ACC
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;
grant select on w_acc_ol.W_SEQ_W_OL_STS_ACC to W_ACC_OL_APP;
