@title 自动更新系统--服务端
echo off
echo 设置JDK目录   
set JDKHOME="C:\Program Files\Java\jdk1.6.0_22"

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
%JDKHOME%\bin\java -Xmx512m -classpath .;%_CLASSESPATH%;%_LIBJARS%; com.goldsign.autoupdate.MainServer


echo OLD_PATH is: %OLD_PATH%      
echo path is :%PATH%      
 
set PATH=%OLD_PATH% 
echo 系统返回参数: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%
