create or replace procedure w_up_non_return_action(i_id          in int, --会话id
                                                   i_applybill   in varchar2, --申请非即退的的凭证
                                                   i_applyaction in varchar2, --操作类型
                                                   i_processbom  in varchar2, --当前bom
                                                   o_resultset   out sys_refcursor --定义out参数返回结果集
                                                   )
---------------------------------------------------------------------------------
  --功能：处理非即时退款查询、退款、退款结果
  --参数：
  --创建者：lindq
  --创建日期：20170717
  --版本:1.00
  -------------------------------------------------------------------------------
 as
  tkTime           varchar(8); --凭证时间
  shiftId          varchar(2); --申请处理班次
  lineId           varchar(2); --申请处理车站
  stationId        varchar(2); --申请处理车站
  deviceId         varchar(4); --申请处理设备id
  receiptId        varchar(4); --凭证序号
  devicetypeId     varchar(2); --设备类型
  audt_flag        varchar(1);
  applytime        date;
  
  isNeedBaseinfo   int:= 0; --是否需要基本信息
  --非即时退款操作
  actionQuery      varchar(1):= '1'; --查询
  actionApply      varchar(1):= '2'; --申请退款
  actionFinish     varchar(1):= '3'; --退款完成
  --处理标志
  hdlFlagFinish    varchar(1):= '1'; --退款完成
  hdlFlagUnandle   varchar(1):= '2'; --未处理
  hdlFlagHandle    varchar(1):= '3'; --车票已有退款结果
  hdlFlagError     varchar(1):= '5'; --非法申请
  hdlFlagPermit    varchar(1):= '7'; --许可
  --并发值
  procesingDoing   varchar(1):= '1'; --存在并发
  procesingNodoing varchar(1):= '0'; --没有并发
  
  v_temp_counts    int;
  v_temp_counts1   int;
  v_temp_counts2   int;
  non_return_day   int := 7; --非即时退款等待日
begin
  ---------------------------------基本信息处理-------------------------------------------------------------
  begin
    delete w_acc_st.t#w_st_results_non_rtn;
  
    begin
      --获取凭证信息
      --20170616-01-0101-001-0002
      select substr(i_applybill, 1, 8),
             substr(i_applybill, 10, 2),
             substr(i_applybill, 13, 2),
             substr(i_applybill, 15, 2),
             substr(i_applybill, 18, 3),
             substr(i_applybill, 22, 4),
             '03'
        into tkTime, --运营日
             shiftId, --BOM班次序号
             lineId, --线路编号
             stationId, --车站编号
             deviceId, --设备ID
             receiptId, --申请非即退的凭证ID
             devicetypeId --设备类型
        from dual;
    end;
  
    --查询配置表非即时退款等待日
    select cfg_value
      into non_return_day
      from w_acc_st.w_st_cfg_sys_base
     where cfg_key = 'sys.non_return_day';
  
    --非即时退查询、及申请
    if (actionQuery = i_applyaction or actionApply = i_applyaction) then
      isNeedBaseinfo := 1;
    end if;
  end;
  ---------------------------------基本信息处理结束-------------------------------------------------------------
  begin
    if (isNeedBaseinfo = 1) then
      begin
        insert into w_acc_st.t#w_st_results_non_rtn
          (card_logical_id,
           deposit_fee,
           card_balance_fee,
           actual_return_bala,
           penalty,
           penalty_reason,
           audt,
           hdl_flag,
           apply_datetime)
          select case
                   when length(card_logical_id) > 16 then
                    substr(card_logical_id, length(card_logical_id) - 16 + 1, 16)
                   else
                    card_logical_id
                 end case,
                 deposit_fee * 100 deposit_fee, --押金
                 card_balance_fee * 100 card_balance_fee, --卡片余额
                 actual_return_bala * 100 actual_return_bala, --实际返余额
                 penalty_fee * 100 penalty, --罚金
                 penalty_reason, --惩罚原因
                 AUDIT_FLAG, --审核原因：1-审核通过
                 hdl_flag, --处理结果(清分系统只写2种标识):2-未处理；3-车票已有退款结果
                 apply_datetime --申请时间
            from w_acc_st.w_st_list_non_rtn
           where line_id = lineId
             and station_id = stationId
             and dev_type_id = devicetypeId
             and device_id = deviceId
             and receipt_id = receiptId
             and to_char(apply_datetime, 'yyyymmdd') = tkTime
             and shift_id = shiftId;
      end;
      --数据判断
      select count(card_logical_id)
        into v_temp_counts
        from w_acc_st.t#w_st_results_non_rtn;
      if (v_temp_counts = 1) then
        select audt, apply_datetime
          into audt_flag, applytime
          from w_acc_st.t#w_st_results_non_rtn;
        --未审核或申请时间小于7天,审核标志NULL值，处理标志为未处理
        if (audt_flag is null or audt_flag <> '1' or
           round(to_number(sysdate - applytime)) < non_return_day) then
          update w_acc_st.t#w_st_results_non_rtn
             set hdl_flag           = hdlFlagUnandle,
                 deposit_fee        = 0,
                 card_balance_fee   = 0,
                 actual_return_bala = 0,
                 penalty            = 0,
                 penalty_reason     = '0';
        end if;
      else
        --正常的非即时退款表中不存在，从错误表中查找逻辑卡号,增加非法申请信息
        insert into w_acc_st.t#w_st_results_non_rtn
          (card_logical_id,
           deposit_fee,
           card_balance_fee,
           actual_return_bala,
           penalty,
           penalty_reason,
           audt,
           hdl_flag)
        values
          ('', 0, 0, 0, 0, '', '', hdlFlagError);
      end if;
    end if;
  end;
  ---------------------------------获取凭证的各处理信息结束--------------------------------
  --------------------------------查询-----------------------------------------------------
  begin
    if (i_applyaction = actionQuery) then
      --记录返回状态
      insert into w_acc_st.w_st_flow_non_rtn
        (water_no, id, apply_bill, apply_action, return_flag, return_time)
        select w_acc_st.w_seq_w_st_flow_non_rtn.nextval,
               i_id,
               i_applybill,
               actionQuery,
               hdl_flag,
               sysdate
          from w_acc_st.t#w_st_results_non_rtn; ----返回处理信息
      open o_resultset for
        select card_logical_id,
               deposit_fee,
               card_balance_fee,
               actual_return_bala,
               penalty,
               penalty_reason,
               hdl_flag
          from w_acc_st.t#w_st_results_non_rtn;
    end if;
  end;
  --------------------------------查询结束-----------------------------------------------------
  -----------------------------------申请退款----------------------------------------------------
  begin
    if (i_applyaction = actionApply) then
      begin
        select count(*)
          into v_temp_counts 
          from w_acc_st.w_st_flow_non_rtn
         where apply_bill = i_applybill
           and apply_action = actionApply
           and processing = procesingDoing;--存在并发申请
        select count(*)
          into v_temp_counts1
          from w_acc_st.w_st_flow_non_rtn
         where apply_bill = i_applybill
           and return_flag = hdlFlagFinish;--凭证号已退款
        select count(*)
          into v_temp_counts2
          from w_acc_st.w_st_flow_non_rtn
         where id = i_id
           and apply_bill = i_applybill
           and apply_action = actionQuery
           and return_flag = hdlFlagHandle;--车票已有退款结果（已经清算完成）
           
        --同一连接已查询过，查询返回标志为3且没有并发,返回标志7允许退款
        if (v_temp_counts = 0 and v_temp_counts1 = 0 and v_temp_counts2 > 0) then
          begin
            update w_acc_st.t#w_st_results_non_rtn
               set hdl_flag = hdlFlagPermit;
          end;
        end if;
        --记录返回状态及标识有连接正在处理(增加当前连接的并发锁)
        insert into w_acc_st.w_st_flow_non_rtn
          (water_no,
           id,
           apply_bill,
           apply_action,
           return_flag,
           return_time,
           processing)
          select w_acc_st.W_SEQ_W_ST_FLOW_NON_RTN.nextval,
                 i_id,
                 i_applybill,
                 actionApply,
                 hdl_flag,
                 sysdate,
                 procesingDoing
            from w_acc_st.t#w_st_results_non_rtn;
        --返回处理信息
        open o_resultset for
          select card_logical_id,
                 deposit_fee,
                 card_balance_fee,
                 actual_return_bala,
                 penalty,
                 penalty_reason,
                 hdl_flag
            from w_acc_st.t#w_st_results_non_rtn;
      end;
    end if;
  end;
  -----------------------------------------------申请退款结束----------------------------------------------------
  ----------------------------------------------退款完成通知处理--------------------------------------------------
  begin
    if (i_applyaction = actionFinish) then
      begin
        insert into w_acc_st.T#W_ST_FLAG_NON_RTN
          (return_flag)
          select return_flag
            from w_acc_st.w_st_flow_non_rtn
           where id = i_id
             and apply_bill = i_applybill;
        select count(*)
          into v_temp_counts1
          from w_acc_st.T#W_ST_FLAG_NON_RTN
         where return_flag = hdlFlagHandle;--车票已有退款结果（已经清算完成）
        select count(*)
          into v_temp_counts2
          from w_acc_st.T#W_ST_FLAG_NON_RTN
         where return_flag = hdlFlagPermit;--允许退款
      end;
      if (v_temp_counts1 > 0 and v_temp_counts2 > 0) then
      --判断是否走完查询、申请退款流程，走完了，才更新凭证状态
        begin
          --记录返回状态:完成状态为1已退款
          insert into w_acc_st.w_st_flow_non_rtn
            (water_no,
             id,
             apply_bill,
             apply_action,
             return_flag,
             return_time)
          values
            (w_acc_st.W_SEQ_W_ST_FLOW_NON_RTN.nextval,
             i_id,
             i_applybill,
             actionFinish,
             hdlFlagFinish,
             sysdate);
          --更新凭证处理标志为已完成退款
          update w_acc_st.w_st_list_non_rtn
             set hdl_flag           = hdlFlagFinish,
                 return_line_id     = substr(i_processbom, 1, 2),
                 return_station_id  = substr(i_processbom, 3, 2),
                 return_dev_type_id = substr(i_processbom, 5, 2),
                 return_device_id   = substr(i_processbom, 7, 3)
           where line_id = lineId
             and station_id = stationId
             and dev_type_id = devicetypeId
             and device_id = deviceId
             and receipt_id = receiptId
             and to_char(apply_datetime, 'yyyymmdd') = tkTime
             and shift_id = shiftId;
        end;
      else
        begin
          --记录返回状态 状态为空值，没有更新凭证状态
          insert into w_acc_st.w_st_flow_non_rtn
            (water_no,
             id,
             apply_bill,
             apply_action,
             return_flag,
             return_time)
          values
            (w_acc_st.W_SEQ_W_ST_FLOW_NON_RTN.nextval,
             i_id,
             i_applybill,
             actionFinish,
             '',
             sysdate);
        end;
      end if;
      begin
        --解除当前连接的并发锁
        update w_acc_st.w_st_flow_non_rtn
           set processing = procesingNodoing
         where id = i_id
           and apply_bill = i_applybill
           and apply_action = actionApply;
        --返回结果（无结果返回）
        open o_resultset for
          select card_logical_id,
                 deposit_fee,
                 card_balance_fee,
                 actual_return_bala,
                 penalty,
                 penalty_reason,
                 hdl_flag
            from w_acc_st.t#w_st_results_non_rtn;
      end;
    end if;
  end;
  ----------------------------------------------退款完成通知处理结束--------------------------------------------------
  commit;
  -----------------------------------------------------------------------------------------------------
end w_up_non_return_action;
/
