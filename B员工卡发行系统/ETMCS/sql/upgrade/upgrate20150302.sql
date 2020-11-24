-- 菜单
insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED , SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7304' , '退卡', 'com.goldsign.etmcs.ui.panel.ReturnCardPanel' , '7304.png', '73' , '73', '0', '07' , '', 'M');
commit;

-- 插入版本信息
insert into acc_tk.ic_et_sys_version (VERSION_NO, OPERATOR_ID, VALID_DATE, DEL_DESC, UPDATE_DESC, ADD_DESC, NOTE)
values ('1.2', 'lindq', sysdate, null, '新增员工卡异常退卡处理：输入工号和逻辑卡号查询强制退卡；读卡后直接退卡。', null, null);
commit;