create or replace procedure w_up_ol_chg_verify(p_message_id      in varchar2, --��Ϣ����
                                             p_termina_no      in varchar2, --�ն˱��
                                             p_sam_logical_id  in varchar2, --sam���߼�����
                                             p_transation_seq  in number, --�ն˽������к�
                                             p_branches_code   in varchar2, --�������
                                             p_pub_main_code   in varchar2, --������������
                                             p_pub_sub_code    in varchar2, --�������ӱ���
                                             p_card_type       in varchar2, --Ʊ������
                                             p_tk_logic_no     in varchar2, --Ʊ���߼�����
                                             p_tk_phy_no       in varchar2, --Ʊ��������
                                             p_is_test_tk      in varchar2, --�Ƿ���Կ�
                                             p_onl_tran_times  in number, --Ʊ���������׼���
                                             p_offl_tran_times in number, --Ʊ���ѻ����׼���
                                             p_buss_type       in varchar2, --ҵ������
                                             p_value_type      in varchar2, --ֵ����
                                             p_charge_fee      in number, --�������
                                             p_balance         in number, --Ʊ�����
                                             p_effective_min   in number, --������Ч�ڣ���λ������
                                             p_result          out varchar2)

  ----------------------------------------------��α�֤��ֵ�ͳ�������ȷ����Ϣ-----------------------------------
  --����:���߳�ֵϵͳ�����У��
  --���: 23-�豸δ����򼤻����Ч��
  --      24-�����豸���ֵ�豸��һ��
  --      45-����������ֵ��һ��
  --      61-������Ӧ�ĳ�ֵԭʼ��¼������
  -- ����ISM�豸���ͣ�ͬʱ�޸��豸���Ͳ�ѯ��OL_PUB_FLAG
  -------------------------------------------------------------------------------
 as
  v_temp_number integer;
  --v_temp_charge_fee number;
  v_temp_date       date;
  v_temp_deviceType varchar(2); --�豸����
begin
  begin
    select substr(p_termina_no, 5, 2) into v_temp_deviceType from dual;
  end;
  --BOM ����TVM/ISM�豸���ܽ��г�ֵ�Ȳ��� (�޸��豸���Ͳ�ѯ��OL_PUB_FLAG)
  select count(1) into v_temp_number from w_acc_ol.w_ol_pub_flag where code=v_temp_deviceType and type='1';
  if v_temp_number=0 then
    p_result := '60';
    return;
  end if;
  /**
  ---------------------�豸�Ƿ񼤻�򼤻����Ч��-----------------------------------------------------------------------

  --ֻ��BOM�豸����Ҫ����
  if v_temp_deviceType = '03' then
    begin
      select count(*)
        into v_temp_number
        from w_acc_ol.w_ol_chg_activation t
       where return_code = '00'
         and t.err_code = '00'
         and termina_no = p_termina_no
         and sam_logical_id = p_sam_logical_id
         and sysdate between update_date and update_date + p_effective_min;
    exception
      when no_data_found then
        p_result := '23';
        return;
    end;
    if v_temp_number = 0 then
      p_result := '23';
      return;
    end if;
  end if;

  --dbms_output.put_line('�豸δ����򼤻����Ч��');**/
  ---------------------��ֵ��������--------------------------------------------------------------------------------------
  ---1������ǰ���г�ֵ��¼��2����������Ч�ڣ�3��������������
  if p_message_id = '53' then
    begin
      select count(*)
        into v_temp_number
        from w_acc_ol.w_ol_chg_plus
       where termina_no = p_termina_no
         and sam_logical_id = p_sam_logical_id
         and transation_seq = p_transation_seq
         and branches_code = p_branches_code
         and pub_main_code = p_pub_main_code
         and pub_sub_code = p_pub_sub_code
         and card_type = p_card_type
         and tk_logic_no = p_tk_logic_no
         and tk_phy_no = p_tk_phy_no
         and is_test_tk = p_is_test_tk
            -- and onl_tran_times = p_onl_tran_times
            --and offl_tran_times = p_offl_tran_times
         and buss_type = '14'
         and value_type = p_value_type
         and charge_fee = p_charge_fee
         and balance = p_balance
         and status = '2';
    exception
      when no_data_found then
        insert into w_acc_ol.w_ol_chg_log_test
          (insert_time, msg)
        values
          (sysdate, '������Ӧ�ĳ�ֵԭʼ��¼������');
        p_result := '61';
        return;
    end;
    if v_temp_number = 0 then
      insert into w_acc_ol.w_ol_chg_log_test
        (insert_time, msg)
      values
        (sysdate, '������Ӧ�ĳ�ֵԭʼ��¼������');
      p_result := '61';
      return;
    end if;
    --��Ч���жϣ��Ȼ�ȡ��ֵ�ɹ���������кţ������һ�γ�ֵ�ɹ������ٻ�ȡ��ֵ�ɹ���ʱ�䣬�����ǰʱ���ȥ��ֵʱ������Ч���ڣ�����Գ���
    begin
      select max(water_no)
        into v_temp_number
        from w_acc_ol.w_ol_chg_plus
       where termina_no = p_termina_no
         and sam_logical_id = p_sam_logical_id
         and transation_seq = p_transation_seq-1
         and branches_code = p_branches_code
         and pub_main_code = p_pub_main_code
         and pub_sub_code = p_pub_sub_code
         and card_type = p_card_type
         and tk_logic_no = p_tk_logic_no
         and tk_phy_no = p_tk_phy_no
         and is_test_tk = p_is_test_tk
         and buss_type = '14'
         and value_type = p_value_type
         and charge_fee = p_charge_fee
         and balance = p_balance
         and status = '2';
    end;

    begin
      select update_date
        into v_temp_date
        from w_acc_ol.w_ol_chg_plus
       where water_no = v_temp_number;
    end;
    if sysdate - v_temp_date >  p_effective_min / 1440 then
      insert into w_acc_ol.w_ol_chg_log_test
        (insert_time, msg)
      values
        (sysdate, '����������Ч�ڣ�Ĭ��' || p_effective_min || '����');
      p_result := '51';
      return;
    end if;

    --�����ظ�����
    begin
      select count(*)
        into v_temp_number
        from w_acc_ol.w_ol_chg_sub
       where termina_no = p_termina_no
         and sam_logical_id = p_sam_logical_id
         and transation_seq = p_transation_seq
         and branches_code = p_branches_code
         and pub_main_code = p_pub_main_code
         and pub_sub_code = p_pub_sub_code
         and card_type = p_card_type
         and tk_logic_no = p_tk_logic_no
         and tk_phy_no = p_tk_phy_no
         and is_test_tk = p_is_test_tk
            -- and onl_tran_times = p_onl_tran_times
            --and offl_tran_times = p_offl_tran_times
         and buss_type = '18'
         and value_type = p_value_type
         and charge_fee = p_charge_fee
         and balance = p_balance
         and status = '2';
      if v_temp_number > 0 then
        insert into w_acc_ol.w_ol_chg_log_test
          (insert_time, msg)
        values
          (sysdate, '�����ظ�����');
        p_result := '61';
        return;
      end if;
    end;
  end if;

  p_result := '00';
  return;
  -----------------------------------------------------------------------------------------------------

end w_up_ol_chg_verify;
/
