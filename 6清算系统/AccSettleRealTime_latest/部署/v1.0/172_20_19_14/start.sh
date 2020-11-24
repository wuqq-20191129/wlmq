#!/bin/sh
LANG=zh_CN.gbk
export LANG

export ORACLE_BASE=/u01/oracle                         
export ORACLE_HOME=/u01/oracle/product/11.2/db_1     
export ORACLE_SID=wacc                                         
export PATH=$PATH:$ORACLE_HOME/bin
nohup ./runSettleServer.sh >settleServer.log& 
tail -f settleServer.log
