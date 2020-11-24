create or replace procedure w_up_cm_report_loss(i_busnissType in varchar2, --ҵ������
                                              i_idCardType  in varchar2, --֤������
                                              i_idNumber    in varchar2, --֤����
                                              i_applybill   in varchar2, --ƾ֤ID
                                              i_applyaction in varchar2, --��������
                                              i_currentBom  in varchar2, --�豸���
                                              i_cardType    in varchar2, --Ʊ������
                                              i_cardLogicalID in varchar2, --Ʊ���߼�����
                                              o_result     out varchar2 --����out�������ؽ������
                                              )
---------------------------------------------------------------------------------
  --���ܣ���ʧ����ҡ���ʧ������
  --���ߣ�lindaquan
  --�������ڣ�20170717
  --�汾:1.00
---------------------------------------------------------------------------------
 as
  resultPermit    char(2) :='00'; --00:��������
  resultNoID      char(2) :='01'; --01:֤��������
  resultExpID     char(2) :='02'; --02:֤���ѹ���
  resultBlackCard char(2) :='03'; --03:��������
  resultRepApply  char(2) :='04'; --04:�ظ�����
  resultNoMake    char(2) :='05'; --05:��������,δ�ƿ�
  resultRefund    char(2) :='06'; --06:�������˿�
  resultReMake    char(2) :='07'; --07:�����벹��
  resultNoLoss    char(2) :='10'; --10:�޹�ʧ,���ɽ��/��������

  bReportLoss       char(1) :='1';  --ҵ�����ͣ���ʧ
  bReportLossOf     char(1) :='2';  --ҵ�����ͣ����
  bReportLossFill   char(1) :='3';  --ҵ�����ͣ���ʧ��������

  --applyQuery      char(1) :='1';  --����ʽ����ѯ
  applyResult     char(1) :='2';  --����ʽ������ɹ�֪ͨ

  lineID          varchar2(2):='';
  stationID       varchar2(2):='';
  cardMainID      varchar2(2):='';
  cardSubID       varchar2(2):='';
  countTemp       integer :=0;--��ʱͳ��
  isMake          varchar2(2) :='-1';--�Ƿ��Ѿ��ƿ�
  isExpiredID       char(1);--֤���Ƿ�ʧЧ 1��Ч��0ʧЧ
  waitDay           integer :=0;--�Ǽ�ʱ�˿�ȴ���
  tmpBusnissType    char(1) :='1';--�������ͣ�1����������2����������
  tmpsql            varchar2(1024);--��ʱSQL���

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
    
  ---------����ɹ�֪ͨ��ʼ------------------
  begin
    if i_applyaction=applyResult then
      insert into w_st_cm_result_report_loss
        (water_no, apply_bill, create_time, busniss_type, identify_type, identity_id, card_logical_id, line_id, station_id, card_main_id, card_sub_id)
      values
        (w_acc_st.w_seq_st_cm_result_report_loss.nextval, i_applybill, sysdate, i_busnissType, i_idCardType, i_idNumber, i_cardLogicalID, lineID, stationID, cardMainID, cardSubID);

      --�����ѯ��¼
      insert into w_acc_st.w_st_cm_log_report_loss
        (water_no, apply_bill, apply_action, return_flag, return_time)
      values
        (w_acc_st.w_seq_w_st_cm_log_report_loss.nextval, i_applybill, i_applyaction, resultPermit, sysdate);
      commit;
      return;
    end if;
  end;
  ---------����ɹ�֪ͨ����------------------


  ---------��ѯ��ʼ------------------
  --�����00:��������;01:֤��������;02:֤���ѹ���;03:��������;04:�ظ�����;
        --05:��������,δ�ƿ�;06:�������˿�/�����������Ǽ�ʱ�˿�ȴ��գ�;10:�޹�ʧ,���ɽ��/��������
  --0.��ѯͨѶ��¼���Ƿ���ڳɹ��Ĺ�ʧ���Ҽ�¼��w_st_cm_result_report_loss��
  --1.��ѯ�����¼���Ƿ���ڹ�ʧ���Ҽ�¼��04:�ظ�����;10:�޹�ʧ,���ɽ��,���ɲ������룩
  --2.��ѯ�����������w_st_list_sign_card��01:֤��������;02:֤���ѹ���;w_st_list_sign_card����hdl_flag = '2'��
  --3.��ѯ֤�����ռ������Ƿ�ES������w_st_list_sign_card����hdl_flag <> '2';05:������,δ�ƿ���
  --4.��ʧʱ����ѯ�Ƿ������(03:��������)

  --��ʼ���00:��������
  o_result := resultPermit;

  --��ѯ֤����
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

  --01:֤��������
  if isMake='-1' then
     o_result := resultNoID;
  end if;

  --02:֤���ѹ���
  if isExpiredID=0 then
     o_result := resultExpID;
  end if;

  --04:�ظ�����
  --��ʼ���00:��������ʱ��ִ��
  if o_result = resultPermit then
    countTemp :=0;
    --����
    select count(1) into countTemp from w_acc_st.w_st_cm_result_report_loss
    where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber))
    and substr(apply_bill,1,8)=to_char(sysdate,'yyyymmdd') and trim(card_logical_id)=trim(i_cardLogicalID);
    --����
    if countTemp=0 then
        select count(1) into countTemp from w_acc_st.w_st_list_report_loss
        where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    --��Ҽ�¼�������
    if countTemp=0 and i_busnissType<>bReportLossFill then
        select count(1) into countTemp from w_acc_st.w_st_list_report_loss_pend
        where busniss_type = i_busnissType and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    --��������������
    if countTemp=0 and i_busnissType<>bReportLossOf  then
        select count(1) into countTemp from w_acc_st.w_st_list_sign_card_pend
        where identity_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    if countTemp>0 then
       o_result := resultRepApply;
    end if;
  end if;

  --��ʼ���00:��������ʱ��ִ��
  if o_result = resultPermit and i_busnissType = bReportLoss then
      --05:��������,δ�ƿ�
      if isMake<>'2' and isMake<>'-1' and tmpBusnissType='1' then
         o_result := resultNoMake;
      else

        --03:��������
        --�Ƿ��Ӧ��������¼
        select count(1) into countTemp from w_acc_st.w_op_prm_black_list_mtr where card_logical_id=trim(i_cardLogicalID);
        --��������
        select countTemp+count(1) into countTemp from w_acc_st.w_op_prm_black_list_mtr_sec where begin_logical_id<=trim(i_cardLogicalID) and end_logical_id>=trim(i_cardLogicalID);
        if countTemp>0 then
           o_result := resultBlackCard;
        end if;

    end if;
  end if;

  --���������
  if o_result = resultPermit and i_busnissType<>bReportLoss then
    --10:�޹�ʧ,���ɽ��,���ɲ�������
    countTemp :=0;
    --����
    select count(1) into countTemp from w_acc_st.w_st_cm_result_report_loss
    where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber))
    and substr(apply_bill,1,8)=to_char(sysdate,'yyyymmdd') and trim(card_logical_id)=trim(i_cardLogicalID);
    --����
    if countTemp=0 then
      select count(1) into countTemp from w_acc_st.w_st_list_report_loss
      where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID);
    end if;
    if countTemp=0 then
       o_result := resultNoLoss;
    else

      --�Ǽ�ʱ�˿�ȴ���
      select cfg_value into waitDay from w_acc_st.w_st_cfg_sys_base where cfg_key='sys.non_return_day' and record_flag='0';
      --06:�������˿�/����(�й�ʧ��¼���ɽ�ҺͲ����������ȴ��ղ������ҺͲ���)
      countTemp :=0;
      select count(1) into countTemp from w_acc_st.w_st_list_report_loss
      where busniss_type = bReportLoss and identify_type = i_idCardType and upper(trim(identity_id)) = upper(trim(i_idNumber)) and trim(card_logical_id)=trim(i_cardLogicalID)
      and trunc(APPLY_DATETIME)+waitDay < trunc(sysdate);
      if countTemp>0 then
         o_result := resultRefund;
      end if;

    end if;
  end if;


  --�����ѯ��¼
  insert into w_acc_st.w_st_cm_log_report_loss
    (water_no, apply_bill, apply_action, return_flag, return_time)
  values
    (w_acc_st.w_seq_w_st_cm_log_report_loss.nextval, i_applybill, i_applyaction, o_result, sysdate);
  commit;

  ---------��ѯ����------------------
end w_up_cm_report_loss;
/
