LANG=zh_CN.gb18030
export LANG
nohup ./runCommuServer.sh >commuserver.log& 
tail -f commuserver.log