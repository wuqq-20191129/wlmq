#!/bin/sh
LANG=Zh_CN.GB18030
export LANG

export ORACLE_BASE=/opt/oracle/app                          
export ORACLE_HOME=/opt/oracle/app/oracle/product/11.2.0/db_1    
export ORACLE_SID=acctest                                         
export PATH=$PATH:$ORACLE_HOME/bin

nohup ./runServer.sh >>./log/EsCommu_`date +%Y%m%d`.log& 
tail -f ./log/EsCommu_`date +%Y%m%d`.log