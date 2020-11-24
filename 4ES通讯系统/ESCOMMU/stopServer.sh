#!/bin/sh
a=`ps -ef | grep 'prog=escommu' |grep -v 'grep'|awk '{print $2}'`
kill  $a
