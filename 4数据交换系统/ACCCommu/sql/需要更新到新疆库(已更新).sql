comment on column W_CM_QUE_MESSAGE.message_type_sub
  is '��Ϣ�����ͣ�01:����ļ���02�������ļ���03����Ʊ�����ļ���';
  
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:������,2:������,3:��ͨ����';
  
  -- Add comments to the columns 
comment on column w_acc_st.w_ST_LIST_SIGN_CARD.hdl_flag
  is '0:δ����,1:��������,2:�Ѿ�����';

comment on table W_ST_LIST_REPORT_LOSS
  is '��������ʧ״̬��';
-- Add comments to the columns 
comment on column W_ST_LIST_REPORT_LOSS.water_no
  is '��ˮ��';
comment on column W_ST_LIST_REPORT_LOSS.apply_bill
  is 'ƾ֤ID';
comment on column W_ST_LIST_REPORT_LOSS.create_time
  is '����ʱ��';
comment on column W_ST_LIST_REPORT_LOSS.busniss_type
  is 'ҵ������';
comment on column W_ST_LIST_REPORT_LOSS.identify_type
  is '֤������(1:���֤;2:ѧ��֤;3:����֤;4:���˿�;5:Ա����;9:����)';
comment on column W_ST_LIST_REPORT_LOSS.identity_id
  is '֤������';
comment on column W_ST_LIST_REPORT_LOSS.line_id
  is '��·';
comment on column W_ST_LIST_REPORT_LOSS.station_id
  is '��վ';
comment on column W_ST_LIST_REPORT_LOSS.dev_type_id
  is '�豸����';
comment on column W_ST_LIST_REPORT_LOSS.device_id
  is '�豸ID';
comment on column W_ST_LIST_REPORT_LOSS.apply_name
  is '����';
comment on column W_ST_LIST_REPORT_LOSS.apply_sex
  is '�Ա�';
comment on column W_ST_LIST_REPORT_LOSS.expired_date
  is '��Ч����';
comment on column W_ST_LIST_REPORT_LOSS.tel_no
  is '�绰';
comment on column W_ST_LIST_REPORT_LOSS.fax
  is '����';
comment on column W_ST_LIST_REPORT_LOSS.address
  is '��ַ';
comment on column W_ST_LIST_REPORT_LOSS.operator_id
  is '����Ա';
comment on column W_ST_LIST_REPORT_LOSS.apply_datetime
  is '����ʱ��';
comment on column W_ST_LIST_REPORT_LOSS.shift_id
  is '���';
comment on column W_ST_LIST_REPORT_LOSS.card_app_flag
  is '��Ӧ�ñ�־';
comment on column W_ST_LIST_REPORT_LOSS.card_main_id
  is 'Ʊ��������';
comment on column W_ST_LIST_REPORT_LOSS.card_sub_id
  is 'Ʊ��������';
comment on column W_ST_LIST_REPORT_LOSS.card_logical_id
  is '�߼�����';
comment on column W_ST_LIST_REPORT_LOSS.card_physical_id
  is '������';
comment on column W_ST_LIST_REPORT_LOSS.card_print_id
  is '��ӡ��';
comment on column W_ST_LIST_REPORT_LOSS.balance_water_no
  is '������ˮ��';

-- Grant/Revoke object privileges 
grant select ON w_acc_st.w_op_prm_white_list_moc to W_ACC_CM_APP;
grant select ON w_acc_st.w_OP_PRM_BLACK_LIST_MOC to W_ACC_CM_APP;


-- Grant/Revoke object privileges 
grant select ON w_acc_st.W_SEQ_OP_CM_LOG_PARA_FLE_CHK to W_ACC_CM_APP;
grant select ON w_acc_st.W_SEQ_OP_CM_LOG_PARA_FLE_CHK to W_ACC_CM_APP;
