-- 删除错误“职务”配置数据
delete acc_tk.ic_et_pub_flag where type='8' and code='10' and code_text='总经理助理';
delete acc_tk.ic_et_pub_flag where type='8' and code='10' and code_text='经理助理';
commit;

-- 修改职务“主席”的代码为“11”
update acc_tk.ic_et_pub_flag set code='11' where type='8' and code='10' and code_text='主席';
commit;

-- 修改张曙光，肖  华的员工职务为“主席”，代码“11”
update acc_tk.ic_et_issue set employee_position='11' where employee_id='100295' and phy_id='722A9CC0';
update acc_tk.ic_et_issue set employee_position='11' where employee_id='100296' and phy_id='712C9CC0';
commit;

-- 插入版本信息
insert into acc_tk.ic_et_sys_version (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.11', 'lindq', sysdate, null, '完善员工卡信息修改功能，修复职务大于10个时不能添加缺陷。', null, null);
commit;
