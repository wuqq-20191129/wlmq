-- Grant/Revoke object privileges 
grant select, insert, update, delete on w_rl_distance_change to w_acc_rl_app;
grant select on w_rl_distance_change to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_distance_change to w_acc_st_app;

grant select, insert, update, delete on w_rl_distance_od to w_acc_rl_app;
grant select on w_rl_distance_od to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_distance_od to w_acc_st_app;

grant select, insert, update, delete on w_rl_log to w_acc_rl_app;
grant select on w_rl_log to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_log to w_acc_st_app;

grant select, insert, update, delete on w_rl_params_station to w_acc_rl_app;
grant select on w_rl_params_station to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_params_station to w_acc_st_app;

grant select, insert, update, delete on w_rl_params_sys to w_acc_rl_app;
grant select on w_rl_params_sys to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_params_sys to w_acc_st_app;

grant select, insert, update, delete on w_rl_params_threshold to w_acc_rl_app;
grant select on w_rl_params_threshold to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_params_threshold to w_acc_st_app;

grant select, insert, update, delete on w_rl_proportion to w_acc_rl_app;
grant select on w_rl_proportion to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_proportion to w_acc_st_app;

grant select, insert, update, delete on w_rl_sys_version to w_acc_rl_app;
grant select on w_rl_sys_version to w_acc_rl_dev;
grant select, insert, update, delete on w_rl_sys_version to w_acc_st_app;

grant select, insert, update, delete on w_t#rl_min_avg_distance to w_acc_rl_app;
grant select on w_t#rl_min_avg_distance to w_acc_rl_dev;
grant select, insert, update, delete on w_t#rl_min_avg_distance to w_acc_st_app;

grant select on w_seq_w_rl_distance_od to w_acc_rl_app;
grant select on w_seq_w_rl_params_threshold to w_acc_rl_app;
grant select on w_seq_w_rl_log to w_acc_rl_app;



--w_acc_st�û���Ȩ
grant select, insert, update, delete on w_op_sys_operator to w_acc_rl_app;
grant select on w_op_sys_operator to w_acc_rl_dev;
grant select, insert, update, delete on w_op_sys_operator to w_acc_rl;

grant select, insert, update, delete on w_op_sys_group_module to w_acc_rl_app;
grant select on w_op_sys_group_module to w_acc_rl_dev;
grant select, insert, update, delete on w_op_sys_group_module to w_acc_rl;

grant select, insert, update, delete on w_OP_SYS_module to w_acc_rl_app;
grant select on w_OP_SYS_module to w_acc_rl_dev;
grant select, insert, update, delete on w_OP_SYS_module to w_acc_rl;

grant select, insert, update, delete on w_op_sys_group_operator to w_acc_rl_app;
grant select on w_op_sys_group_operator to w_acc_rl_dev;
grant select, insert, update, delete on w_op_sys_group_operator to w_acc_rl;

grant select, insert, update, delete on w_op_log_user_access to w_acc_rl_app;
grant select on w_op_log_user_access to w_acc_rl_dev;
grant select, insert, update, delete on w_op_log_user_access to w_acc_rl;

grant select, insert, update, delete on W_OP_CFG_SYS to w_acc_rl_app;
grant select on W_OP_CFG_SYS to w_acc_rl_dev;
grant select, insert, update, delete on W_OP_CFG_SYS to w_acc_rl;

grant select on w_seq_op_log_user_access to w_acc_rl_app;
