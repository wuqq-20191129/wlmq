@title �Զ�����ϵͳ--�����
echo off
echo ����JDKĿ¼   
set JDKHOME="C:\Program Files\Java\jdk1.6.0_22"

echo Ӧ�ó���Ŀ¼���ǵ�ǰbat��Ŀ¼·��   
echo current cd is:%CD%
set APPHOME=%CD%
echo APPHOME is  %APPHOME%  
echo ����lib·��  
set JARLIB=%APPHOME%\lib

echo ����CLASSESPATH·��  
set _CLASSESPATH=%APPHOME%\classes

echo ���ݾɵ�·��  
set OLD_PATH=%PATH%
echo OLD_PATH IS: %OLD_PATH%   

echo �����µ�·��    
set PATH=%PATH%;%APPHOME%
echo NEW_PATH IS: %PATH%  
  
CD %APPHOME%
set _LIBJARS=
  for %%i in (%JARLIB%\*.jar) do call addJar.bat %%i
%JDKHOME%\bin\java -Xmx512m -classpath .;%_CLASSESPATH%;%_LIBJARS%; com.goldsign.autoupdate.MainServer


echo OLD_PATH is: %OLD_PATH%      
echo path is :%PATH%      
 
set PATH=%OLD_PATH% 
echo ϵͳ���ز���: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%
