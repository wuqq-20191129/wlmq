#!/bin/sh
LANG=zh_CN
export LANG

export ORACLE_BASE=/home/u01/app/oracle                         
export ORACLE_HOME=/home/u01/app/oracle/product/oracle/db_1     
export ORACLE_SID=wacctstdb                                         
export PATH=$PATH:$ORACLE_HOME/bin
nohup ./runSettleServer.sh >settleServer.log& 
tail -f settleServer.log
