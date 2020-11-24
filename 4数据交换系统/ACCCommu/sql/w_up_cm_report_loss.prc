create or replace procedure w_up_cm_report_loss(i_busnissType in varchar2, --业务类型
                                              i_idCardType  in varchar2, --证件类型
                                              i_idNumber    in varchar2, --证件号
                                              i_applybill   in varchar2, --凭证ID
                                              i_applyaction in varchar2, --操作类型
                                              i_currentBom  in varchar2, --设备编号
                                              i_cardType    in varchar2, --票卡类型
                                              i_cardLogicalID in varchar2, --票卡逻辑卡号
                                              o_result     out varchar2 --定义out参数返回结果代码
                                              )
---------------------------------------------------------------------------------
  --功能：挂失、解挂、挂失卡补卡
  --作者：lindaquan
  --创建日期：20170717
  --版本:1.00
---------------------------------------------------------------------------------
 as
  resultPermit    char(2) :='00'; --00:允许申请
  resultNoID      char(2) :='01'; --01:证件不存在
  resultExpID     char(2) :='02'; --02:证件已过期
  resultBlackCard char(2) :='03'; --03:黑名单卡
  resultRepApply  char(2) :='04'; --04:重复申请
  resultNoMake    char(2) :='05'; --05:正常申请,未制卡
  resultRefund    char(2) :='06'; --06:已申请退款
  resultReMake    char(2) :='07'; --07:已申请补卡
  resultNoLoss    char(2) :='10'; --10:无挂失,不可解挂/补卡申请

  bReportLoss       char(1) :='1';  --业务类型：挂失
  bReportLossOf     char(1) :='2';  --业务类型：解挂
  bReportLossFill   char(1) :='3';  --业务类型：挂失补卡申请

  --applyQuery      char(1) :='1';  --处理方式：查询
  applyResult     char(1) :='2';  --处理方式：办理成功通知

  lineID          varchar2(2):='';
  stationID       varchar2(2):='';
  cardMainID      varchar2(2):='';
  cardSubID       varchar2(2):='';
  countTemp       integer :=0;--临时统计
  isMake          varchar2(2) :='-1';--是否已经制卡
  isExpiredID       char(1);--证件是否失效 1有效，0失效
  waitDay           integer :=0;--非即时退款等待日
  tmpBusnissType    char(1) :='1';--申请类型，1：正常申请2：补卡申请
  tmpsql            varchar2(1024);--临时SQL语句

begin

  select substr(i_currentBom, 1, 2),
         substr(i_currentBom, 3, 2)
    into lineID,
         stationID
    from dual;
    
  select substr(i_cardType, 1, 2),
         substr(i_cardType, 3, 2)
    into cardMainID,
         cardSubID
    from dual;
    
  ---------办理成功通知开始------------------
  begin
    if i_applyaction=applyResult then
      insert into w_st_cm_result_report_loss
        (water_no, apply_bill, create_time, busniss_type, identify_type, identity_id, card_logical_id, line_id, station_id, card_main_id, card_sub_id)
      values
        (w_acc_st.w_seq_st_cm_result_report_loss.nextval, i_applybill, sysdate, i_busnissType, i_idCardType, i_idNumber, i_cardLogicalID, lineID, stationID, cardMainID, cardSubID);

      --插入查询记录
      insert into w_acc_st.w_st_cm_log_report_loss
        (water_no, apply_bill, apply_action, return_flag, return_time)
      values
        (w_acc_st.w_seq_w_st_cm_log_report_loss.nextval, i_applybill, i_applyaction, resultPermit, sysdate);
      commit;
      return;
    end if;
  end;
  ---------办理成功通知结束------------------


  ---------查询开始------------------
  --结果：00:允许申请;01:证件不存在;02:证件已过期;03:黑名单卡;04:重复申请;
        --05:正常申请,未制卡;06:已申请退款/补卡（超过非即时退款等待日）;10:无挂失,不可解挂/补卡申请
  --0.查询通讯记录表是否存在成功的挂失或解挂记录（w_st_cm_result_report_loss）
  --1.查询清算记录表是否存在挂失或解挂记录（04:重复申请;10:无挂失,不可解挂,不可补卡申请）
  --2.查询记名卡申请表w_st_list_sign_card（01:证件不存在;02:证件已过期;w_st_list_sign_card表中hdl_flag = '2'）
  --3.查询证件对照记名卡是否ES生产（w_st_list_sign_card表中hdl_flag <> '2';05:已申请,未制卡）
  --4.挂失时，查询是否黑名单(03:黑名单卡)

  --初始结果00:允许申请
  o_result := resultPermit;

  --查询证件号
  select count(1) into countTemp from w_acc_st.w_st_list_sign_card
           where identity_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber));
  if countTemp>0 then
  select hdl_flag,(case when expired_date>=sysdate then 1 else 0 end),app_business_type
         into isMake,isExpiredID,tmpBusnissType from (
         select hdl_flag,expired_date,apply_datetime,app_business_type,rownum from w_acc_st.w_st_list_sign_card
           where identity_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber))
           order by apply_datetime desc)
  where rownum = 1;
  end if;

  --01:证件不存在
  if isMake='-1' then
     o_result := resultNoID;
  end if;

  --02:证件已过期
  if isExpiredID=0 then
     o_result := resultExpID;
  end if;

  --04:重复申请
  --初始结果00:允许申请时才执行
  if o_result = resultPermit then
    countTemp :=0;
    --当天
    select count(1) into countTemp from w_acc_st.w_st_cm_result_report_loss
    where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber))
    and substr(apply_bill,1,8)=to_char(sysdate,'yyyymmdd') and trim(card_logical_id)=trim(i_cardLogicalID);
    --多天
    if countTemp=0 then
        select count(1) into countTemp from w_acc_st.w_st_list_report_loss
        where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    --解挂记录待处理表
    if countTemp=0 and i_busnissType<>bReportLossFill then
        select count(1) into countTemp from w_acc_st.w_st_list_report_loss_pend
        where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    --补卡申请待处理表
    if countTemp=0 and i_busnissType<>bReportLossOf  then
        select count(1) into countTemp from w_acc_st.w_st_list_sign_card_pend
        where identity_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    if countTemp>0 then
       o_result := resultRepApply;
    end if;
  end if;

  --初始结果00:允许申请时才执行
  if o_result = resultPermit and i_busnissType = bReportLoss then
      --05:正常申请,未制卡
      if isMake<>'2' and isMake<>'-1' and tmpBusnissType='1' then
         o_result := resultNoMake;
      else

        --03:黑名单卡
        --是否对应黑名单记录
        select count(1) into countTemp from w_acc_st.w_op_prm_black_list_mtr where card_logical_id=trim(i_cardLogicalID);
        --黑名单段
        select countTemp+count(1) into countTemp from w_acc_st.w_op_prm_black_list_mtr_sec where begin_logical_id<=trim(i_cardLogicalID) and end_logical_id>=trim(i_cardLogicalID);
        if countTemp>0 then
           o_result := resultBlackCard;
        end if;

    end if;
  end if;

  --补卡、解挂
  if o_result = resultPermit and i_busnissType<>bReportLoss then
    --10:无挂失,不可解挂,不可补卡申请
    countTemp :=0;
    --当天
    select count(1) into countTemp from w_acc_st.w_st_cm_result_report_loss
    where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber))
    and substr(apply_bill,1,8)=to_char(sysdate,'yyyymmdd') and trim(card_logical_id)=trim(i_cardLogicalID);
    --多天
    if countTemp=0 then
      select count(1) into countTemp from w_acc_st.w_st_list_report_loss
      where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    if countTemp=0 then
       o_result := resultNoLoss;
    else

      --非即时退款等待日
      select cfg_value into waitDay from w_acc_st.w_st_cfg_sys_base where cfg_key='sys.non_return_day' and record_flag='0';
      --06:已申请退款/补卡(有挂失记录即可解挂和补卡，超出等待日不允许解挂和补卡)
      countTemp :=0;
      select count(1) into countTemp from w_acc_st.w_st_list_report_loss
      where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID)
      and trunc(APPLY_DATETIME)+waitDay < trunc(sysdate);
      if countTemp>0 then
         o_result := resultRefund;
      end if;

    end if;
  end if;


  --插入查询记录
  insert into w_acc_st.w_st_cm_log_report_loss
    (water_no, apply_bill, apply_action, return_flag, return_time)
  values
    (w_acc_st.w_seq_w_st_cm_log_report_loss.nextval, i_applybill, i_applyaction, o_result, sysdate);
  commit;

  ---------查询结束------------------
end w_up_cm_report_loss;
/
