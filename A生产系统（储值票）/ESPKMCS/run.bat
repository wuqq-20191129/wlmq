@title ��ֵƱ����ϵͳ
echo off
echo ����JDKĿ¼   
set JDKHOME="C:\Program Files\Java\jdk1.8.0_144"

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
%JDKHOME%\bin\java -Xms512m -Xmx1024m -classpath .;%_CLASSESPATH%;%_LIBJARS%; com.goldsign.esmcs.Main
 
set PATH=%OLD_PATH% 

echo ϵͳ���ز���: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

echo ����88,��ϵͳע��,�������µ�¼
if %EXITTYPE% NEQ 88  call run.bat
#pause
