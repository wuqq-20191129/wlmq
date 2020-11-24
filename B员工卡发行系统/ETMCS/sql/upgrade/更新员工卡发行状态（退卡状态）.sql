-- 读写器无法读取票卡信息其他信息 更新发行状态为退卡
update ACC_TK.IC_ET_ISSUE set use_state='1', return_oper='longjy', return_time=sysdate 
where use_state='0' and employee_id ='400074'  and logical_id='4100000010000435';

-- 员工卡遗失 更新发行状态为退卡
update ACC_TK.IC_ET_ISSUE set use_state='1', return_oper='longjy', return_time=sysdate 
where use_state='0' and employee_id ='100610'  and logical_id='4100000010000353';

commit;
