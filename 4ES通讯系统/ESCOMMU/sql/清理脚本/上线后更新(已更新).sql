select * from w_ic_es_again_info;
select * from w_ic_es_again_info_svt;
select * from w_ic_es_hunch_info;
select * from w_ic_es_initi_info;
select * from w_ic_es_logout_info;

update w_ic_es_again_info set logical_id=trim(logical_id),phy_id=trim(phy_id),print_id=trim(print_id);
update w_ic_es_again_info_svt set logical_id=trim(logical_id),phy_id=trim(phy_id),print_id=trim(print_id);
update w_ic_es_hunch_info set logical_id=trim(logical_id),phy_id=trim(phy_id),print_id=trim(print_id);
update w_ic_es_initi_info set logical_id=trim(logical_id),phy_id=trim(phy_id),print_id=trim(print_id);
update w_ic_es_logout_info set logical_id=trim(logical_id),phy_id=trim(phy_id),print_id=trim(print_id);
commit;
