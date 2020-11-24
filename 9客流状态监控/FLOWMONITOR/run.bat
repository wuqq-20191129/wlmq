@title 状态客流监视系统
echo off

echo 应用程序目录就是当前bat的目录路径   
echo current cd is:%CD%
set APPHOME=%CD%
echo APPHOME is  %APPHOME%  
echo 设置lib路径  
set JARLIB=%APPHOME%\lib
echo JARLIB is %JARLIB%

::echo 设置CLASSESPATH路径  
::set _CLASSESPATH=%APPHOME%\classes

set OPTS=-Xms512M -Xmx512M -Xss128k -XX:+AggressiveOpts -XX:+UseParallelGC -XX:NewSize=64M

%APPHOME%\jre\bin\java -classpath %JARLIB%\FLOWMONITOR.jar;%JARLIB%\* com.goldsign.fm.application.Main  
 
pause
