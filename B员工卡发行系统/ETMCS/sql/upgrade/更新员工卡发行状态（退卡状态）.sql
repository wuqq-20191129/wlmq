-- ��д���޷���ȡƱ����Ϣ������Ϣ ���·���״̬Ϊ�˿�
update ACC_TK.IC_ET_ISSUE set use_state='1', return_oper='longjy', return_time=sysdate 
where use_state='0' and employee_id ='400074'  and logical_id='4100000010000435';

-- Ա������ʧ ���·���״̬Ϊ�˿�
update ACC_TK.IC_ET_ISSUE set use_state='1', return_oper='longjy', return_time=sysdate 
where use_state='0' and employee_id ='100610'  and logical_id='4100000010000353';

commit;
