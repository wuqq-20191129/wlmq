INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('91', '工卡制作', '', '91.png', '91', '91', '0', '06');
INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('9101', '员工卡制卡', 'com.goldsign.etmcs.ui.panel.MadeCardPanel', '9101.png', '91', '91', '0', '06');
INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('9102', '票卡分析', 'com.goldsign.etmcs.ui.panel.ReadCardPanel', '9102.png', '91', '91', '0', '06');

INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('72', '报表统计', '', '72.png', '72', '72', '0', '06');
INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('7201', '制卡查询', 'com.goldsign.etmcs.ui.panel.MadeCardQueryPanel', '7201.png', '72', '72', '0', '06');  
INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('7202', '制卡统计', 'com.goldsign.etmcs.ui.panel.MadeCardStatPanel', '7202.png', '72', '72', '0', '06');  
INSERT INTO op_sys_menu
  (menu_id, menu_name, menu_url, menu_icon, top_menu_id, parent_id, LOCKED, sys_flag)
VALUES
  ('7203', '操作日志', 'com.goldsign.etmcs.ui.panel.OperateLogPanel', '7203.png', '72', '72', '0', '06');          
      