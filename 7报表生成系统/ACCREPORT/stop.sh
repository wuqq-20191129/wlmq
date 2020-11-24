a=`ps -ef|grep wweb |grep java|grep 'report.application.MainApp'|grep -v 'grep'|awk '{print $2}'`
kill -9 $a

