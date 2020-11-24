-- 增加记名卡票面打印系统 菜单

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('910', '记名卡票面打印系统', '', '', '', '0', '0', '10', '', 'S');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('74', '票面打印', '', '74.png', '74', '910', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('75', '报表查询', '', '75.png', '75', '910', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7401', '打印', 'com.goldsign.scpmcs.ui.panel.SignCardPrintPanel', '7401.png', '74', '74', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7501', '打印统计', 'com.goldsign.scpmcs.ui.panel.PrintQueryPanel', '7501.png', '75', '75', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7502', '操作日志', 'com.goldsign.scpmcs.ui.panel.OperateLogPanel', '7502.png', '75', '75', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('76', '基本信息', '', '76.png', '76', '910', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7601', '参数查询', 'com.goldsign.scpmcs.ui.panel.ParamPanel', '7601.png', '76', '76', '0', '10', '', 'M');

insert into acc_st.op_sys_module (MODULE_ID, MODULE_NAME, MENU_URL, MENU_ICON, TOP_MENU_ID, PARENT_ID, LOCKED, SYS_FLAG, BTN_ID, MODULE_TYPE)
values ('7602', '记名卡查询', 'com.goldsign.scpmcs.ui.panel.SignCardPanel', '7602.png', '76', '76', '0', '10', '', 'M');

commit;