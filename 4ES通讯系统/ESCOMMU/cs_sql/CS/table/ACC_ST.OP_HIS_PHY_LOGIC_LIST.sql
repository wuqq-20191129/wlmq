-- Create table
create table OP_HIS_PHY_LOGIC_LIST
(
  PHYSIC_NO VARCHAR2(20) not null,
  LOGIC_NO  VARCHAR2(20) not null
);
-- Add comments to the table 
comment on table OP_HIS_PHY_LOGIC_LIST
  is '物理卡号逻辑卡号对照表历史表';
-- Create/Recreate primary, unique and foreign key constraints 
alter table OP_HIS_PHY_LOGIC_LIST
  add constraint PK_OP_HIS_PHY_LOGIC_LIST primary key (PHYSIC_NO)
  disable;
alter table OP_HIS_PHY_LOGIC_LIST
  add constraint UK_OP_HIS_PHY_LOGIC_LIST unique (LOGIC_NO)
  disable;
