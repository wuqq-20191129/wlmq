@title 员工卡发行系统
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

%APPHOME%\jre\bin\java -classpath %JARLIB%\ETMCS.jar;%JARLIB%\* com.goldsign.etmcs.Main  

echo 系统返回参数: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

echo 返回88,则系统注销,其他重新登录
if %EXITTYPE% NEQ 88  call run.bat

pause
