comment on column W_CM_QUE_MESSAGE.message_type_sub
  is '消息子类型：01:审计文件；02：错误文件；03：配票数据文件；';
  
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:地铁卡,2:公交卡,3:交通部卡';
  
  -- Add comments to the columns 
comment on column w_acc_st.w_ST_LIST_SIGN_CARD.hdl_flag
  is '0:未做卡,1:正在做卡,2:已经做卡';

comment on table W_ST_LIST_REPORT_LOSS
  is '记名卡挂失状态表';
-- Add comments to the columns 
comment on column W_ST_LIST_REPORT_LOSS.water_no
  is '流水号';
comment on column W_ST_LIST_REPORT_LOSS.apply_bill
  is '凭证ID';
comment on column W_ST_LIST_REPORT_LOSS.create_time
  is '创建时间';
comment on column W_ST_LIST_REPORT_LOSS.busniss_type
  is '业务类型';
comment on column W_ST_LIST_REPORT_LOSS.identify_type
  is '证件类型(1:身份证;2:学生证;3:军人证;4:老人卡;5:员工卡;9:其他)';
comment on column W_ST_LIST_REPORT_LOSS.identity_id
  is '证件号码';
comment on column W_ST_LIST_REPORT_LOSS.line_id
  is '线路';
comment on column W_ST_LIST_REPORT_LOSS.station_id
  is '车站';
comment on column W_ST_LIST_REPORT_LOSS.dev_type_id
  is '设备类型';
comment on column W_ST_LIST_REPORT_LOSS.device_id
  is '设备ID';
comment on column W_ST_LIST_REPORT_LOSS.apply_name
  is '姓名';
comment on column W_ST_LIST_REPORT_LOSS.apply_sex
  is '性别';
comment on column W_ST_LIST_REPORT_LOSS.expired_date
  is '有效日期';
comment on column W_ST_LIST_REPORT_LOSS.tel_no
  is '电话';
comment on column W_ST_LIST_REPORT_LOSS.fax
  is '传真';
comment on column W_ST_LIST_REPORT_LOSS.address
  is '地址';
comment on column W_ST_LIST_REPORT_LOSS.operator_id
  is '操作员';
comment on column W_ST_LIST_REPORT_LOSS.apply_datetime
  is '申请时间';
comment on column W_ST_LIST_REPORT_LOSS.shift_id
  is '班次';
comment on column W_ST_LIST_REPORT_LOSS.card_app_flag
  is '卡应用标志';
comment on column W_ST_LIST_REPORT_LOSS.card_main_id
  is '票卡主类型';
comment on column W_ST_LIST_REPORT_LOSS.card_sub_id
  is '票卡子类型';
comment on column W_ST_LIST_REPORT_LOSS.card_logical_id
  is '逻辑卡号';
comment on column W_ST_LIST_REPORT_LOSS.card_physical_id
  is '物理卡号';
comment on column W_ST_LIST_REPORT_LOSS.card_print_id
  is '打印号';
comment on column W_ST_LIST_REPORT_LOSS.balance_water_no
  is '清算流水号';

-- Grant/Revoke object privileges 
grant select ON w_acc_st.w_op_prm_white_list_moc to W_ACC_CM_APP;
grant select ON w_acc_st.w_OP_PRM_BLACK_LIST_MOC to W_ACC_CM_APP;


-- Grant/Revoke object privileges 
grant select ON w_acc_st.W_SEQ_OP_CM_LOG_PARA_FLE_CHK to W_ACC_CM_APP;
grant select ON w_acc_st.W_SEQ_OP_CM_LOG_PARA_FLE_CHK to W_ACC_CM_APP;
