-- ɾ������ְ����������
delete acc_tk.ic_et_pub_flag where type='8' and code='10' and code_text='�ܾ�������';
delete acc_tk.ic_et_pub_flag where type='8' and code='10' and code_text='��������';
commit;

-- �޸�ְ����ϯ���Ĵ���Ϊ��11��
update acc_tk.ic_et_pub_flag set code='11' where type='8' and code='10' and code_text='��ϯ';
commit;

-- �޸�����⣬Ф  ����Ա��ְ��Ϊ����ϯ�������롰11��
update acc_tk.ic_et_issue set employee_position='11' where employee_id='100295' and phy_id='722A9CC0';
update acc_tk.ic_et_issue set employee_position='11' where employee_id='100296' and phy_id='712C9CC0';
commit;

-- ����汾��Ϣ
insert into acc_tk.ic_et_sys_version (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.11', 'lindq', sysdate, null, '����Ա������Ϣ�޸Ĺ��ܣ��޸�ְ�����10��ʱ�������ȱ�ݡ�', null, null);
commit;
