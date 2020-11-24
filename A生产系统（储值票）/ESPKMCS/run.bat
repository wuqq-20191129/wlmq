@title 储值票生产系统
echo off
echo 设置JDK目录   
set JDKHOME="C:\Program Files\Java\jdk1.8.0_144"

echo 应用程序目录就是当前bat的目录路径   
echo current cd is:%CD%
set APPHOME=%CD%
echo APPHOME is  %APPHOME%  
echo 设置lib路径  
set JARLIB=%APPHOME%\lib

echo 设置CLASSESPATH路径  
set _CLASSESPATH=%APPHOME%\classes

echo 备份旧的路径  
set OLD_PATH=%PATH%
echo OLD_PATH IS: %OLD_PATH%   

echo 设置新的路径    
set PATH=%PATH%;%APPHOME%
echo NEW_PATH IS: %PATH%  
  
CD %APPHOME%
set _LIBJARS=
  for %%i in (%JARLIB%\*.jar) do call addJar.bat %%i
%JDKHOME%\bin\java -Xms512m -Xmx1024m -classpath .;%_CLASSESPATH%;%_LIBJARS%; com.goldsign.esmcs.Main
 
set PATH=%OLD_PATH% 

echo 系统返回参数: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

echo 返回88,则系统注销,其他重新登录
if %EXITTYPE% NEQ 88  call run.bat
#pause
