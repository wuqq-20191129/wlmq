update fm_dev_monitor set description=replace(description,'-OO','-00') where node_type='03' and description like '%-OO%';
commit;
update fm_dev_monitor set description=replace(description,'-O','-0') where node_type='03' and description like '%-O%';
commit;