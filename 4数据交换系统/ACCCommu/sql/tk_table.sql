-- Create table
create table W_IC_INF_PRETICKET_SALE
(
  dept_id            CHAR(4) not null,
  tickettype_id      CHAR(4) not null,
  value              INTEGER not null,
  quantity           INTEGER not null,
  logical_begin      varchar2(20),
  logical_end        varchar2(20),
  sale_time          DATE not null,
  operator_id        varchar2(6),
  report_date        DATE not null,
  file_flag          CHAR(1) default '0' not null,
  file_name          VARCHAR2(18)
);
-- Add comments to the table 
comment on table W_IC_INF_PRETICKET_SALE
  is '预制票发售数据';
-- Add comments to the columns 
comment on column W_IC_INF_PRETICKET_SALE.dept_id
  is '车站代码';
comment on column W_IC_INF_PRETICKET_SALE.quantity
  is '数量';
comment on column W_IC_INF_PRETICKET_SALE.report_date
  is '报表日期';
comment on column W_IC_INF_PRETICKET_SALE.sale_time
  is '发售时间';
comment on column W_IC_INF_PRETICKET_SALE.tickettype_id
  is '票卡类型';
comment on column W_IC_INF_PRETICKET_SALE.value
  is '面值';
comment on column W_IC_INF_PRETICKET_SALE.logical_begin
  is '起始逻辑卡号';
comment on column W_IC_INF_PRETICKET_SALE.logical_end
  is '结束逻辑卡号';
comment on column W_IC_INF_PRETICKET_SALE.operator_id
  is '操作员';
comment on column W_IC_INF_PRETICKET_SALE.file_flag
  is '是否已生成交易文件0:否,1:是';
comment on column W_IC_INF_PRETICKET_SALE.file_name
  is '生成文件名';
grant select, insert, update, delete on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_CM_APP;
grant select, insert, update, delete on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_TK_APP;
grant select on W_ACC_TK.W_IC_INF_PRETICKET_SALE to W_ACC_TK_DEV;
