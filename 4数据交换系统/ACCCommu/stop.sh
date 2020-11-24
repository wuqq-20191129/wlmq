a=`ps -ef|grep java|grep 'prog=ACCCommu'|grep -v 'grep'|awk '{print $2}'`
kill -9  $a