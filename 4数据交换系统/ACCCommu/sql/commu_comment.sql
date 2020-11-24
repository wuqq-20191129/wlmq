-- Add comments to the columns 
comment on column W_CM_TRAFFIC_TVM.message_sequ
  is '消息序列号';
comment on column W_CM_TRAFFIC_TVM.traffic_datetime
  is '时间';
comment on column W_CM_TRAFFIC_TVM.line_id
  is '线路代码';
comment on column W_CM_TRAFFIC_TVM.station_id
  is '车站代码';
comment on column W_CM_TRAFFIC_TVM.card_main_code
  is '票卡主类型';
comment on column W_CM_TRAFFIC_TVM.card_sub_code
  is '票卡子类型';
comment on column W_CM_TRAFFIC_TVM.traffic
  is '总客流';
  

-- Add comments to the columns 
comment on column W_CM_TRAFFIC_DETAIL.message_sequ
  is '消息序列号';
comment on column W_CM_TRAFFIC_DETAIL.line_id
  is '线路代码';
comment on column W_CM_TRAFFIC_DETAIL.station_id
  is '车站代码';
comment on column W_CM_TRAFFIC_DETAIL.card_main_code
  is '票卡主类型';
comment on column W_CM_TRAFFIC_DETAIL.card_sub_code
  is '票卡子类型';
comment on column W_CM_TRAFFIC_DETAIL.flag
  is '标志';
comment on column W_CM_TRAFFIC_DETAIL.traffic_datetime
  is '时间';
comment on column W_CM_TRAFFIC_DETAIL.traffic
  is '客流数据';
  

-- Add comments to the columns 
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.line_id
  is '线路代码';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.station_id
  is '车站代码';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.traffic_datetime
  is '时间';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.card_main_type
  is '票卡主类型';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.card_sub_type
  is '票卡子类型';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.flag
  is '标志';
comment on column W_CM_TRAFFIC_AFC_MIN_TOTAL.traffic
  is '客流数据';
  
-- Add comments to the columns 
comment on column W_CM_TRAFFIC_AFC_MIN.line_id
  is '线路代码';
comment on column W_CM_TRAFFIC_AFC_MIN.station_id
  is '车站代码';
comment on column W_CM_TRAFFIC_AFC_MIN.traffic_datetime
  is '时间';
comment on column W_CM_TRAFFIC_AFC_MIN.card_main_type
  is '票卡主类型';
comment on column W_CM_TRAFFIC_AFC_MIN.card_sub_type
  is '票卡子类型';
comment on column W_CM_TRAFFIC_AFC_MIN.flag
  is '标志';
comment on column W_CM_TRAFFIC_AFC_MIN.traffic
  is '客流数据';
  
-- Add comments to the columns 
comment on column W_CM_TRAFFIC.message_sequ
  is '消息序列号';
comment on column W_CM_TRAFFIC.line_id
  is '线路代码';
comment on column W_CM_TRAFFIC.station_id
  is '车站代码';
comment on column W_CM_TRAFFIC.traffic_datetime
  is '时间';
comment on column W_CM_TRAFFIC.traffic_type
  is '客流类型0:进站,1:出站';
comment on column W_CM_TRAFFIC.invalid_ticket
  is '检测到的无效票卡数';
comment on column W_CM_TRAFFIC.refuse_ticket
  is '拒绝票卡数';
comment on column W_CM_TRAFFIC.blacklist_ticket
  is '捕捉到的黑名单数';
comment on column W_CM_TRAFFIC.total_traffic
  is '总客流';
  
-- Add comments to the columns 
comment on column W_CM_TK_FILE_RECV.状态
  is '0：未处理
1：正在处理
2：文件格式有问题
3：已处理';


-- Add comments to the table 
comment on table W_CM_SEQ_TK_FILE
  is '票库通讯接口文件序号记录表';
-- Add comments to the columns 
comment on column W_CM_SEQ_TK_FILE.file_type
  is '文件类型';
comment on column W_CM_SEQ_TK_FILE.seq
  is '当前序号';
comment on column W_CM_SEQ_TK_FILE.alter_day
  is '修改日期';
comment on column W_CM_SEQ_TK_FILE.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_QUE_MESSAGE.message_id
  is '消息id';
comment on column W_CM_QUE_MESSAGE.message_time
  is '消息时间';
comment on column W_CM_QUE_MESSAGE.line_id
  is '线路代码';
comment on column W_CM_QUE_MESSAGE.station_id
  is '车站代码';
comment on column W_CM_QUE_MESSAGE.ip_address
  is 'ip地址';
comment on column W_CM_QUE_MESSAGE.message
  is '消息';
comment on column W_CM_QUE_MESSAGE.process_flag
  is '处理标志0:未处理1:处理';
comment on column W_CM_QUE_MESSAGE.is_para_inform_msg
  is '是否参数通知消息';
comment on column W_CM_QUE_MESSAGE.para_inform_water_no
  is '参数通知流水号';
comment on column W_CM_QUE_MESSAGE.message_type
  is '消息类型';
comment on column W_CM_QUE_MESSAGE.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_LOG_RECV_SEND.water_no
  is '流水号';
comment on column W_CM_LOG_RECV_SEND.datetime_rec
  is '接收时间';
comment on column W_CM_LOG_RECV_SEND.ip
  is 'ip地址';
comment on column W_CM_LOG_RECV_SEND.type
  is '类型0:接收,1:发送';
comment on column W_CM_LOG_RECV_SEND.message_code
  is '消息代号';
comment on column W_CM_LOG_RECV_SEND.message_sequ
  is '消息顺序号';
comment on column W_CM_LOG_RECV_SEND.message
  is '消息';
comment on column W_CM_LOG_RECV_SEND.result
  is '结果0:成功,1:失败,2:消息错误';
  
 -- Add comments to the columns 
 /*
comment on column W_CM_LOG_NON_RETURN.water_no
  is '流水号';
comment on column W_CM_LOG_NON_RETURN.datetime_rec
  is '接收时间';
comment on column W_CM_LOG_NON_RETURN.ip
  is 'ip地址';
comment on column W_CM_LOG_NON_RETURN.line_id
  is '线路代码';
comment on column W_CM_LOG_NON_RETURN.station_id
  is '站点代码';
comment on column W_CM_LOG_NON_RETURN.dev_type_id
  is '设备类型';
comment on column W_CM_LOG_NON_RETURN.device_id
  is '设备代码';
comment on column W_CM_LOG_NON_RETURN.tk_time
  is '日期时间';
comment on column W_CM_LOG_NON_RETURN.receipt_id
  is '凭证id';
comment on column W_CM_LOG_NON_RETURN.shift_id
  is '班次id';
comment on column W_CM_LOG_NON_RETURN.type
  is '类型';
comment on column W_CM_LOG_NON_RETURN.flag
  is '标志';
comment on column W_CM_LOG_NON_RETURN.cline_id
  is 'sc线路代码';
comment on column W_CM_LOG_NON_RETURN.cstation_id
  is 'sc站点代码';
comment on column W_CM_LOG_NON_RETURN.cdev_type_id
  is 'sc设备类型';
comment on column W_CM_LOG_NON_RETURN.cdevice_id
  is 'sc设备代码';
  */
  
-- Add comments to the columns 
comment on column W_CM_LOG_FTP.water_no
  is '流水号';
comment on column W_CM_LOG_FTP.datetime_ftp
  is 'ftp时间';
comment on column W_CM_LOG_FTP.ip
  is 'ip地址';
comment on column W_CM_LOG_FTP.filename
  is '文件名称';
comment on column W_CM_LOG_FTP.start_time
  is '开始时间';
comment on column W_CM_LOG_FTP.spend_time
  is '耗时';
comment on column W_CM_LOG_FTP.result
  is '结果';
comment on column W_CM_LOG_FTP.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_LOG_CONNECT.water_no
  is '流水号';
comment on column W_CM_LOG_CONNECT.connect_datetime
  is '连接时间';
comment on column W_CM_LOG_CONNECT.connect_ip
  is '连接ip';
comment on column W_CM_LOG_CONNECT.connect_result
  is '连接结果0:成功,1:不成功,2:断开连接';
comment on column W_CM_LOG_CONNECT.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_LOG_COMMU.water_no
  is '流水号';
comment on column W_CM_LOG_COMMU.message_id
  is '消息代码';
comment on column W_CM_LOG_COMMU.message_name
  is '消息名称';
comment on column W_CM_LOG_COMMU.message_from
  is '来源';
comment on column W_CM_LOG_COMMU.start_time
  is '开始时间';
comment on column W_CM_LOG_COMMU.end_time
  is '结束时间';
comment on column W_CM_LOG_COMMU.use_time
  is '处理时间';
comment on column W_CM_LOG_COMMU.result
  is '处理结果';
comment on column W_CM_LOG_COMMU.hdl_thread
  is '线程标志';
comment on column W_CM_LOG_COMMU.sys_level
  is '系统级别';
comment on column W_CM_LOG_COMMU.remark
  is '备注';
  
-- Add comments to the table 
comment on table W_CM_LOG_CLEAR_TABLE
  is '表数据清理配置日志表';
  
-- Add comments to the table 
comment on table W_CM_IDX_HISTORY
  is '中间统计表数据历史索引表';
  
-- Add comments to the columns 
comment on column W_CM_FILE_RECV.water_no
  is '流水号';
comment on column W_CM_FILE_RECV.file_name
  is '文件名';
comment on column W_CM_FILE_RECV.file_type
  is '文件类型';
comment on column W_CM_FILE_RECV.new_filename
  is '异常处理后文件名';
comment on column W_CM_FILE_RECV.file_path
  is '接收文件目录';
comment on column W_CM_FILE_RECV.his_path
  is '历史文件目录';
comment on column W_CM_FILE_RECV.err_path
  is '错误文件目录';
comment on column W_CM_FILE_RECV.status
  is '0:未处理1:正在处理2:文件格式有问题3:文件数据入库异常4:已处理5:文件不存在';
comment on column W_CM_FILE_RECV.remark
  is '备注';
comment on column W_CM_FILE_RECV.flag
  is 'st:清算tk:票务cm:通信';
comment on column W_CM_FILE_RECV.insert_time
  is '插入时间';
comment on column W_CM_FILE_RECV.update_time
  is '更新时间';
comment on column W_CM_FILE_RECV.handle_path
  is '正在处理的文件目录';
  

-- Add comments to the table 
comment on table W_CM_ERR_PRM_BLACK_SEC_DOWN
  is '黑名单段参数下发错误记录表';
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.begin_logical_id
  is '开始逻辑卡号';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.end_logical_id
  is '结束逻辑卡号';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.card_type
  is '1:地铁卡,2:公交卡';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.remark
  is '备注';
comment on column W_CM_ERR_PRM_BLACK_SEC_DOWN.create_datetime
  is '创建时间';
  
  
-- Add comments to the table 
comment on table W_CM_ERR_PRM_BLACK_DOWN
  is '黑名单参数下发错误记录表';
-- Add comments to the columns 
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_logical_id
  is '逻辑卡号';
comment on column W_CM_ERR_PRM_BLACK_DOWN.card_type
  is '1:地铁卡,2:公交卡';
comment on column W_CM_ERR_PRM_BLACK_DOWN.remark
  is '备注';
comment on column W_CM_ERR_PRM_BLACK_DOWN.create_datetime
  is '创建时间';
  
-- Add comments to the columns 
comment on column W_CM_DEV_PARA_VER_HIS.water_no
  is '流水号';
comment on column W_CM_DEV_PARA_VER_HIS.line_id
  is '线路代码';
comment on column W_CM_DEV_PARA_VER_HIS.station_id
  is '车站代码';
comment on column W_CM_DEV_PARA_VER_HIS.dev_type_id
  is '设备类型';
comment on column W_CM_DEV_PARA_VER_HIS.device_id
  is '设备代码';
comment on column W_CM_DEV_PARA_VER_HIS.parm_type_id
  is '参数类型';
comment on column W_CM_DEV_PARA_VER_HIS.record_flag
  is '记录标识0:当前版本,1:未来版本,2:历史版本,3:草稿版本,4:已做删除标记';
comment on column W_CM_DEV_PARA_VER_HIS.version_no
  is '参数版本号';
comment on column W_CM_DEV_PARA_VER_HIS.report_date
  is '报告时间';
comment on column W_CM_DEV_PARA_VER_HIS.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_DEV_PARA_VER_CUR.line_id
  is '线路代码';
comment on column W_CM_DEV_PARA_VER_CUR.station_id
  is '车站代码';
comment on column W_CM_DEV_PARA_VER_CUR.dev_type_id
  is '设备类型';
comment on column W_CM_DEV_PARA_VER_CUR.device_id
  is '设备代码';
comment on column W_CM_DEV_PARA_VER_CUR.parm_type_id
  is '参数类型';
comment on column W_CM_DEV_PARA_VER_CUR.record_flag
  is '记录标识0:当前版本,1:未来版本,2:历史版本,3:草稿版本,4:已做删除标记';
comment on column W_CM_DEV_PARA_VER_CUR.version_no
  is '参数版本号';
comment on column W_CM_DEV_PARA_VER_CUR.report_date
  is '报告时间';
comment on column W_CM_DEV_PARA_VER_CUR.remark
  is '备注';
  
-- Add comments to the columns 
comment on column W_CM_DEV_EVENT.event_datetime
  is '事件时间';
comment on column W_CM_DEV_EVENT.line_id
  is '线路代码';
comment on column W_CM_DEV_EVENT.station_id
  is '车站代码';
comment on column W_CM_DEV_EVENT.dev_type_id
  is '设备类型';
comment on column W_CM_DEV_EVENT.device_id
  is '设备代码';
comment on column W_CM_DEV_EVENT.event_id
  is '事件id';
comment on column W_CM_DEV_EVENT.event_argument
  is '事件参数';
  
-- Add comments to the columns 
comment on column W_CM_DEV_ERROR_STATUS.line_id
  is '线路代码';
comment on column W_CM_DEV_ERROR_STATUS.station_id
  is '车站代码';
comment on column W_CM_DEV_ERROR_STATUS.dev_type_id
  is '设备类型';
comment on column W_CM_DEV_ERROR_STATUS.device_id
  is '设备代码';
comment on column W_CM_DEV_ERROR_STATUS.status_id
  is '状态id';
comment on column W_CM_DEV_ERROR_STATUS.status_value
  is '状态值';
comment on column W_CM_DEV_ERROR_STATUS.status_datetime
  is '状态上传时间';
  
-- Add comments to the columns 
comment on column W_CM_DEV_CURRENT_STATUS.line_id
  is '线路代码';
comment on column W_CM_DEV_CURRENT_STATUS.station_id
  is '车站代码';
comment on column W_CM_DEV_CURRENT_STATUS.dev_type_id
  is '设备类型';
comment on column W_CM_DEV_CURRENT_STATUS.device_id
  is '设备代码';
comment on column W_CM_DEV_CURRENT_STATUS.status_value
  is '设备状态0 正常服务，1 停止运营，2 维修，3 故障，4 通讯中断';
comment on column W_CM_DEV_CURRENT_STATUS.status_datetime
  is '状态上传时间';
  
-- Add comments to the columns 
comment on column W_CM_CFG_SYS.config_name
  is '变量名';
comment on column W_CM_CFG_SYS.config_value
  is '变量值';
comment on column W_CM_CFG_SYS.remark
  is '备注';
  
-- Add comments to the table 
comment on table W_CM_CFG_CLEAR_TABLE
  is '表数据清理配置表';
