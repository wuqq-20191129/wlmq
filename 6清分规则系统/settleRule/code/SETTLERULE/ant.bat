@title 清分规则系统升级
echo off
echo 设置ant目录   
set ANTHOME="C:\Program Files\Java\apache-ant-1.9.2"

echo 应用程序目录就是当前bat的目录路径   
echo current cd is:%CD%
set APPHOME=%CD%
echo APPHOME is  %APPHOME%  
  
CD %APPHOME%
%ANTHOME%\bin\ant -f deploy_sit.xml sendFilesToFTP

echo 系统返回参数: %ERRORLEVEL%
set EXITTYPE=%ERRORLEVEL%

pause
