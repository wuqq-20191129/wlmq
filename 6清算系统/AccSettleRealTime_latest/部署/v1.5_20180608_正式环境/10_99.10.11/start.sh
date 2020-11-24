#!/bin/sh
LANG=zh_CN.UTF-8
export LANG

export ORACLE_BASE=/u01/app/oracle                         
export ORACLE_HOME=/u01/app/oracle/product/11.2.0/db_1     
export ORACLE_SID=waccdb1                                         
export PATH=$PATH:$ORACLE_HOME/bin
export NLS_LANG=american_america.al32utf8
nohup ./runSettleServer.sh >settleServer.log& 
tail -f settleServer.log
