#!/bin/sh
LANG=zh_CN.gbk
export LANG
echo '�����������ɳ���'
nohup ./runReport.sh >reportConsole.log &
tail -f reportConsole.log
