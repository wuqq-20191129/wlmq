#!/bin/sh
LANG=zh_CN.gbk
export LANG
echo '启动报表生成程序'
nohup ./runReport.sh >reportConsole.log &
tail -f reportConsole.log
