@title Ա��������ϵͳ
echo off

echo Ӧ�ó���Ŀ¼���ǵ�ǰbat��Ŀ¼·��   
echo current cd is:%CD%
set APPHOME=%CD%
echo APPHOME is  %APPHOME%  
echo ����lib·��  
set JARLIB=%APPHOME%\lib
echo JARLIB is %JARLIB%

::echo ����CLASSESPATH·��  
::set _CLASSESPATH=%APPHOME%\classes

set OPTS=-Xms512M -Xmx512M -Xss128k -XX:+AggressiveOpts -XX:+UseParallelGC -XX:NewSize=64M

%APPHOME%\jre\bin\java -classpath %JARLIB%\ETMCS.jar;%JARLIB%\* com.goldsign.etmcs.Main  

echo ϵͳ���ز���: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

echo ����88,��ϵͳע��,�������µ�¼
if %EXITTYPE% NEQ 88  call run.bat

pause
