@title ������Ʊ���ӡϵͳ
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

::echo ���ݾɵ�·��  
::set OLD_PATH=%PATH%
::echo OLD_PATH IS: %OLD_PATH%   

::echo �����µ�·��    
::set PATH=%PATH%;%APPHOME%
::echo NEW_PATH IS: %PATH%  

set OPTS=-Xms1024m -Xmx1024m -Xss1m -XX:+AggressiveOpts -XX:+UseParallelGC -XX:NewSize=256M

%APPHOME%\jre\bin\java -classpath %JARLIB%\ECPMCS.jar;%JARLIB%\* com.goldsign.ecpmcs.Main  

::set PATH=%OLD_PATH% 

echo ϵͳ���ز���: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

echo ����88,��ϵͳע��,�������µ�¼
if %EXITTYPE% NEQ 88  call run.bat
 
pause
