#!/bin/sh
LANG=zh_CN.GBK
export LANG

export ORACLE_BASE=/u01/app/oracle/                           
export ORACLE_HOME=/u01/app/oracle/product/11.2.0/dbhome_1    
export ORACLE_SID=ACC                                         
export PATH=$PATH:$ORACLE_HOME/bin
nohup ./runSettleServer.sh >settleServer.log& 
tail -f settleServer.log