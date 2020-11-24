-- Add/modify columns 
alter table w_st_list_sign_card add card_logical_id VARCHAR2(20);
alter table w_st_list_sign_card_del add card_logical_id VARCHAR2(20);
alter table w_st_list_sign_card_pend add card_logical_id VARCHAR2(20);
alter table w_st_list_sign_card_remake add card_logical_id VARCHAR2(20);
comment on column w_st_list_sign_card.card_logical_id is 'Âß¼­¿¨ºÅ';
comment on column w_st_list_sign_card_del.card_logical_id is 'Âß¼­¿¨ºÅ';
comment on column w_st_list_sign_card_pend.card_logical_id is 'Âß¼­¿¨ºÅ';
comment on column w_st_list_sign_card_remake.card_logical_id is 'Âß¼­¿¨ºÅ';

alter table w_st_cm_result_report_loss add card_logical_id VARCHAR2(20);
alter table w_st_cm_result_report_loss add line_id CHAR(2);
alter table w_st_cm_result_report_loss add station_id CHAR(2);
alter table w_st_cm_result_report_loss add card_main_id CHAR(2);
alter table w_st_cm_result_report_loss add card_sub_id CHAR(2);
comment on column W_ST_CM_RESULT_REPORT_LOSS.card_logical_id is 'Âß¼­¿¨ºÅ';
comment on column W_ST_CM_RESULT_REPORT_LOSS.line_id is 'ÉêÇëÏßÂ·';
comment on column W_ST_CM_RESULT_REPORT_LOSS.station_id is 'ÉêÇë³µÕ¾';
comment on column W_ST_CM_RESULT_REPORT_LOSS.card_main_id is 'Æ±¿¨Ö÷ÀàÐÍ';
comment on column W_ST_CM_RESULT_REPORT_LOSS.card_sub_id is 'Æ±¿¨×ÓÀàÐÍ';

