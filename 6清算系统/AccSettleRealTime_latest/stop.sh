a=`ps -ef|grep root|grep java|grep 'AppSettle'|grep -v 'grep'|awk '{print $2}'`
kill  $a
