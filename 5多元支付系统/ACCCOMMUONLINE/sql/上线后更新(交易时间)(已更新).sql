-- Add/modify columns 
alter table w_acc_ol.W_OL_QRCODE_AFC add deal_time VARCHAR2(14);
comment on column w_acc_ol.W_OL_QRCODE_AFC.deal_time is '����ʱ��';
  
alter table w_acc_ol.w_ol_qrcode_cancel add deal_time VARCHAR2(14);
comment on column w_acc_ol.w_ol_qrcode_cancel.deal_time is '����ȡ��ʱ��';

alter table w_acc_ol.w_ol_qrcode_tkstatus add deal_time VARCHAR2(14);
comment on column w_acc_ol.w_ol_qrcode_tkstatus.deal_time is 'ȡƱʱ��';

alter table w_acc_ol.W_OL_QRCODE_ORDER add deal_time VARCHAR2(14);
comment on column w_acc_ol.W_OL_QRCODE_ORDER.deal_time is '����ʱ��';

-- Add comments to the columns 
comment on column w_acc_ol.W_OL_QRPAY_APP.return_code
  is '��Ӧ�� 00:����;01:ϵͳ��������г����쳣02:�������˿�,�ѳ�Ʊ;03:��������ʧ��04:����δ֧��05:�������˿�';
