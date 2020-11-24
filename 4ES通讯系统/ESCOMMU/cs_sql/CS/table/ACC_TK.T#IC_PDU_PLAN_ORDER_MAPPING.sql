create global temporary table ACC_TK.T#IC_PDU_PLAN_ORDER_MAPPING
(
  PLAN_BILL_NO CHAR(12) not null,
  ORDER_NO     CHAR(14) not null,
  FINISH_FLAG  CHAR(1),
  OUT_BILL_NO  CHAR(12),
  OUT_WATER_NO VARCHAR2(18)
)
on commit preserve rows;