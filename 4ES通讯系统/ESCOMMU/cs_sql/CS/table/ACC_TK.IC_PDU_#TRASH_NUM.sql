CREATE GLOBAL TEMPORARY TABLE ACC_TK.IC_PDU_#TRASH_NUM
(
    order_no       char(14)       NULL,
    trash_num      int null
)ON COMMIT PRESERVE ROWS;