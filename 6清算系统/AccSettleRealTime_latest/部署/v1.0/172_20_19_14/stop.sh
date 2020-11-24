a=`ps -ef|grep wweb |grep java|grep 'AppSettle'|grep -v 'grep'|awk '{print $2}'`
kill -9 $a
