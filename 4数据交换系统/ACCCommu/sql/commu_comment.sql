-- Add comments to the columns 
comment on column W_CM_TRAFFIC_TVM.message_sequ
  is '��Ϣ���к�';
comment on column W_CM_TRAFFIC_TVM.traffic_datetime
  is 'ʱ��';
comment on column W_CM_TRAFFIC_TVM.line_id
  is '��·����';
comment on column W_CM_TRAFFIC_TVM.station_id
  is '��վ����';
comment on column W_CM_TRAFFIC_TVM.card_main_code
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_TVM.card_sub_code
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_TVM.traffic
  is '�ܿ���';
  

-- Add comments to the columns 
comment on column W_CM_TRAFFIC_DETAIL.message_sequ
  is '��Ϣ���к�';
comment on column W_CM_TRAFFIC_DETAIL.line_id
  is '��·����';
comment on column W_CM_TRAFFIC_DETAIL.station_id
  is '��վ����';
comment on column W_CM_TRAFFIC_DETAIL.card_main_code
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_DETAIL.card_sub_code
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_DETAIL.flag
  is '��־';
comment on column W_CM_TRAFFIC_DETAIL.traffic_datetime
  is 'ʱ��';
comment on column W_CM_TRAFFIC_DETAIL.traffic
  is '��������';
  

-- Add comments to the columns 
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.line_id
  is '��·����';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.station_id
  is '��վ����';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.traffic_datetime
  is 'ʱ��';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.card_main_type
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.card_sub_type
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.flag
  is '��־';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.traffic
  is '��������';
  
-- Add comments to the columns 
comment on column W_CM_TRAFFIC_AFC_MIN.line_id
  is '��·����';
comment on column W_CM_TRAFFIC_AFC_MIN.station_id
  is '��վ����';
comment on column W_CM_TRAFFIC_AFC_MIN.traffic_datetime
  is 'ʱ��';
comment on column W_CM_TRAFFIC_AFC_MIN.card_main_type
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_AFC_MIN.card_sub_type
  is 'Ʊ��������';
comment on column W_CM_TRAFFIC_AFC_MIN.flag
  is '��־';
comment on column W_CM_TRAFFIC_AFC_MIN.traffic
  is '��������';
  
-- Add comments to the columns 
comment on column W_CM_TRAFFIC.message_sequ
  is '��Ϣ���к�';
comment on column W_CM_TRAFFIC.line_id
  is '��·����';
comment on column W_CM_TRAFFIC.station_id
  is '��վ����';
comment on column W_CM_TRAFFIC.traffic_datetime
  is 'ʱ��';
comment on column W_CM_TRAFFIC.traffic_type
  is '��������0:��վ,1:��վ';
comment on column W_CM_TRAFFIC.invalid_ticket
  is '��⵽����ЧƱ����';
comment on column W_CM_TRAFFIC.refuse_ticket
  is '�ܾ�Ʊ����';
comment on column W_CM_TRAFFIC.blacklist_ticket
  is '��׽���ĺ�������';
comment on column W_CM_TRAFFIC.total_traffic
  is '�ܿ���';
  
-- Add comments to the columns 
comment on column W_CM_TK_FILE_RECV.״̬
  is '0��δ����
1�����ڴ���
2���ļ���ʽ������
3���Ѵ���';


-- Add comments to the table 
comment on table W_CM_SEQ_TK_FILE
  is 'Ʊ��ͨѶ�ӿ��ļ���ż�¼��';
-- Add comments to the columns 
comment on column W_CM_SEQ_TK_FILE.file_type
  is '�ļ�����';
comment on column W_CM_SEQ_TK_FILE.seq
  is '��ǰ���';
comment on column W_CM_SEQ_TK_FILE.alter_day
  is '�޸�����';
comment on column W_CM_SEQ_TK_FILE.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_QUE_MESSAGE.message_id
  is '��Ϣid';
comment on column W_CM_QUE_MESSAGE.message_time
  is '��Ϣʱ��';
comment on column W_CM_QUE_MESSAGE.line_id
  is '��·����';
comment on column W_CM_QUE_MESSAGE.station_id
  is '��վ����';
comment on column W_CM_QUE_MESSAGE.ip_address
  is 'ip��ַ';
comment on column W_CM_QUE_MESSAGE.message
  is '��Ϣ';
comment on column W_CM_QUE_MESSAGE.process_flag
  is '�����־0:δ����1:����';
comment on column W_CM_QUE_MESSAGE.is_para_inform_msg
  is '�Ƿ����֪ͨ��Ϣ';
comment on column W_CM_QUE_MESSAGE.para_inform_water_no
  is '����֪ͨ��ˮ��';
comment on column W_CM_QUE_MESSAGE.message_type
  is '��Ϣ����';
comment on column W_CM_QUE_MESSAGE.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_LOG_RECV_SEND.water_no
  is '��ˮ��';
comment on column W_CM_LOG_RECV_SEND.datetime_rec
  is '����ʱ��';
comment on column W_CM_LOG_RECV_SEND.ip
  is 'ip��ַ';
comment on column W_CM_LOG_RECV_SEND.type
  is '����0:����,1:����';
comment on column W_CM_LOG_RECV_SEND.message_code
  is '��Ϣ����';
comment on column W_CM_LOG_RECV_SEND.message_sequ
  is '��Ϣ˳���';
comment on column W_CM_LOG_RECV_SEND.message
  is '��Ϣ';
comment on column W_CM_LOG_RECV_SEND.result
  is '���0:�ɹ�,1:ʧ��,2:��Ϣ����';
  
 -- Add comments to the columns 
 /*
comment on column W_CM_LOG_NON_RETURN.water_no
  is '��ˮ��';
comment on column W_CM_LOG_NON_RETURN.datetime_rec
  is '����ʱ��';
comment on column W_CM_LOG_NON_RETURN.ip
  is 'ip��ַ';
comment on column W_CM_LOG_NON_RETURN.line_id
  is '��·����';
comment on column W_CM_LOG_NON_RETURN.station_id
  is 'վ�����';
comment on column W_CM_LOG_NON_RETURN.dev_type_id
  is '�豸����';
comment on column W_CM_LOG_NON_RETURN.device_id
  is '�豸����';
comment on column W_CM_LOG_NON_RETURN.tk_time
  is '����ʱ��';
comment on column W_CM_LOG_NON_RETURN.receipt_id
  is 'ƾ֤id';
comment on column W_CM_LOG_NON_RETURN.shift_id
  is '���id';
comment on column W_CM_LOG_NON_RETURN.type
  is '����';
comment on column W_CM_LOG_NON_RETURN.flag
  is '��־';
comment on column W_CM_LOG_NON_RETURN.cline_id
  is 'sc��·����';
comment on column W_CM_LOG_NON_RETURN.cstation_id
  is 'scվ�����';
comment on column W_CM_LOG_NON_RETURN.cdev_type_id
  is 'sc�豸����';
comment on column W_CM_LOG_NON_RETURN.cdevice_id
  is 'sc�豸����';
  */
  
-- Add comments to the columns 
comment on column W_CM_LOG_FTP.water_no
  is '��ˮ��';
comment on column W_CM_LOG_FTP.datetime_ftp
  is 'ftpʱ��';
comment on column W_CM_LOG_FTP.ip
  is 'ip��ַ';
comment on column W_CM_LOG_FTP.filename
  is '�ļ�����';
comment on column W_CM_LOG_FTP.start_time
  is '��ʼʱ��';
comment on column W_CM_LOG_FTP.spend_time
  is '��ʱ';
comment on column W_CM_LOG_FTP.result
  is '���';
comment on column W_CM_LOG_FTP.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_LOG_CONNECT.water_no
  is '��ˮ��';
comment on column W_CM_LOG_CONNECT.connect_datetime
  is '����ʱ��';
comment on column W_CM_LOG_CONNECT.connect_ip
  is '����ip';
comment on column W_CM_LOG_CONNECT.connect_result
  is '���ӽ��0:�ɹ�,1:���ɹ�,2:�Ͽ�����';
comment on column W_CM_LOG_CONNECT.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_LOG_COMMU.water_no
  is '��ˮ��';
comment on column W_CM_LOG_COMMU.message_id
  is '��Ϣ����';
comment on column W_CM_LOG_COMMU.message_name
  is '��Ϣ����';
comment on column W_CM_LOG_COMMU.message_from
  is '��Դ';
comment on column W_CM_LOG_COMMU.start_time
  is '��ʼʱ��';
comment on column W_CM_LOG_COMMU.end_time
  is '����ʱ��';
comment on column W_CM_LOG_COMMU.use_time
  is '����ʱ��';
comment on column W_CM_LOG_COMMU.result
  is '������';
comment on column W_CM_LOG_COMMU.hdl_thread
  is '�̱߳�־';
comment on column W_CM_LOG_COMMU.sys_level
  is 'ϵͳ����';
comment on column W_CM_LOG_COMMU.remark
  is '��ע';
  
-- Add comments to the table 
comment on table W_CM_LOG_CLEAR_TABLE
  is '����������������־��';
  
-- Add comments to the table 
comment on table W_CM_IDX_HISTORY
  is '�м�ͳ�Ʊ�������ʷ������';
  
-- Add comments to the columns 
comment on column W_CM_FILE_RECV.water_no
  is '��ˮ��';
comment on column W_CM_FILE_RECV.file_name
  is '�ļ���';
comment on column W_CM_FILE_RECV.file_type
  is '�ļ�����';
comment on column W_CM_FILE_RECV.new_filename
  is '�쳣������ļ���';
comment on column W_CM_FILE_RECV.file_path
  is '�����ļ�Ŀ¼';
comment on column W_CM_FILE_RECV.his_path
  is '��ʷ�ļ�Ŀ¼';
comment on column W_CM_FILE_RECV.err_path
  is '�����ļ�Ŀ¼';
comment on column W_CM_FILE_RECV.status
  is '0:δ����1:���ڴ���2:�ļ���ʽ������3:�ļ���������쳣4:�Ѵ���5:�ļ�������';
comment on column W_CM_FILE_RECV.remark
  is '��ע';
comment on column W_CM_FILE_RECV.flag
  is 'st:����tk:Ʊ��cm:ͨ��';
comment on column W_CM_FILE_RECV.insert_time
  is '����ʱ��';
comment on column W_CM_FILE_RECV.update_time
  is '����ʱ��';
comment on column W_CM_FILE_RECV.handle_path
  is '���ڴ�����ļ�Ŀ¼';
  

-- Add comments to the table 
comment on table W_CM_ERR_PRM_BLACK_SEC_DOWN
  is '�������β����·������¼��';
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.begin_logical_id
  is '��ʼ�߼�����';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.end_logical_id
  is '�����߼�����';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.card_type
  is '1:������,2:������';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.remark
  is '��ע';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.create_datetime
  is '����ʱ��';
  
  
-- Add comments to the table 
comment on table W_CM_ERR_PRM_BLACK_DOWN
  is '�����������·������¼��';
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_logical_id
  is '�߼�����';
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:������,2:������';
comment on column W_CM_ERR_PRM_BLACK_DOWN.remark
  is '��ע';
comment on column W_CM_ERR_PRM_BLACK_DOWN.create_datetime
  is '����ʱ��';
  
-- Add comments to the columns 
comment on column W_CM_DEV_PARA_VER_HIS.water_no
  is '��ˮ��';
comment on column W_CM_DEV_PARA_VER_HIS.line_id
  is '��·����';
comment on column W_CM_DEV_PARA_VER_HIS.station_id
  is '��վ����';
comment on column W_CM_DEV_PARA_VER_HIS.dev_type_id
  is '�豸����';
comment on column W_CM_DEV_PARA_VER_HIS.device_id
  is '�豸����';
comment on column W_CM_DEV_PARA_VER_HIS.parm_type_id
  is '��������';
comment on column W_CM_DEV_PARA_VER_HIS.record_flag
  is '��¼��ʶ0:��ǰ�汾,1:δ���汾,2:��ʷ�汾,3:�ݸ�汾,4:����ɾ�����';
comment on column W_CM_DEV_PARA_VER_HIS.version_no
  is '�����汾��';
comment on column W_CM_DEV_PARA_VER_HIS.report_date
  is '����ʱ��';
comment on column W_CM_DEV_PARA_VER_HIS.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_DEV_PARA_VER_CUR.line_id
  is '��·����';
comment on column W_CM_DEV_PARA_VER_CUR.station_id
  is '��վ����';
comment on column W_CM_DEV_PARA_VER_CUR.dev_type_id
  is '�豸����';
comment on column W_CM_DEV_PARA_VER_CUR.device_id
  is '�豸����';
comment on column W_CM_DEV_PARA_VER_CUR.parm_type_id
  is '��������';
comment on column W_CM_DEV_PARA_VER_CUR.record_flag
  is '��¼��ʶ0:��ǰ�汾,1:δ���汾,2:��ʷ�汾,3:�ݸ�汾,4:����ɾ�����';
comment on column W_CM_DEV_PARA_VER_CUR.version_no
  is '�����汾��';
comment on column W_CM_DEV_PARA_VER_CUR.report_date
  is '����ʱ��';
comment on column W_CM_DEV_PARA_VER_CUR.remark
  is '��ע';
  
-- Add comments to the columns 
comment on column W_CM_DEV_EVENT.event_datetime
  is '�¼�ʱ��';
comment on column W_CM_DEV_EVENT.line_id
  is '��·����';
comment on column W_CM_DEV_EVENT.station_id
  is '��վ����';
comment on column W_CM_DEV_EVENT.dev_type_id
  is '�豸����';
comment on column W_CM_DEV_EVENT.device_id
  is '�豸����';
comment on column W_CM_DEV_EVENT.event_id
  is '�¼�id';
comment on column W_CM_DEV_EVENT.event_argument
  is '�¼�����';
  
-- Add comments to the columns 
comment on column W_CM_DEV_ERROR_STATUS.line_id
  is '��·����';
comment on column W_CM_DEV_ERROR_STATUS.station_id
  is '��վ����';
comment on column W_CM_DEV_ERROR_STATUS.dev_type_id
  is '�豸����';
comment on column W_CM_DEV_ERROR_STATUS.device_id
  is '�豸����';
comment on column W_CM_DEV_ERROR_STATUS.status_id
  is '״̬id';
comment on column W_CM_DEV_ERROR_STATUS.status_value
  is '״ֵ̬';
comment on column W_CM_DEV_ERROR_STATUS.status_datetime
  is '״̬�ϴ�ʱ��';
  
-- Add comments to the columns 
comment on column W_CM_DEV_CURRENT_STATUS.line_id
  is '��·����';
comment on column W_CM_DEV_CURRENT_STATUS.station_id
  is '��վ����';
comment on column W_CM_DEV_CURRENT_STATUS.dev_type_id
  is '�豸����';
comment on column W_CM_DEV_CURRENT_STATUS.device_id
  is '�豸����';
comment on column W_CM_DEV_CURRENT_STATUS.status_value
  is '�豸״̬0 ��������1 ֹͣ��Ӫ��2 ά�ޣ�3 ���ϣ�4 ͨѶ�ж�';
comment on column W_CM_DEV_CURRENT_STATUS.status_datetime
  is '״̬�ϴ�ʱ��';
  
-- Add comments to the columns 
comment on column W_CM_CFG_SYS.config_name
  is '������';
comment on column W_CM_CFG_SYS.config_value
  is '����ֵ';
comment on column W_CM_CFG_SYS.remark
  is '��ע';
  
-- Add comments to the table 
comment on table W_CM_CFG_CLEAR_TABLE
  is '�������������ñ�';
