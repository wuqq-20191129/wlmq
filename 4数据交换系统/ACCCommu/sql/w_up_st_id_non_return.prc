create or replace procedure w_up_st_id_non_return(o_id out int) --�Ựid
  -------------------------------------------------------------------------------
  --�����������Ǽ�ʱ�˿����Ӵ����ʶ
  --���������
  --����ֵ����ʶ
  --�����ߣ�  �ν���
  --�޸����ڣ�
  -------------------------------------------------------------------------------
 as
  v_temp_counts int;
begin
  select count(id) into v_temp_counts from w_acc_st.w_st_id_non_rtn;
  if v_temp_counts = 0 then
    insert into w_acc_st.w_st_id_non_rtn (id) values (1);
  end if;

  select id into o_id from w_acc_st.w_st_id_non_rtn;
  update w_acc_st.w_st_id_non_rtn set id = id + 1;

end w_up_st_id_non_return;
