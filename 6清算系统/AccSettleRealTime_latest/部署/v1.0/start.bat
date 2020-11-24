ECHO OFF
set JDKHOME="C:\Program Files (x86)\Java\jdk1.6.0_20"
set APPDRIVE=d:
set APPHOME=D:\en_work\netbeansproject\长沙ACC\ACC清算系统\AccSettle\部署
set JARLIB=%APPHOME%\lib
set CP_1=%JARLIB%\AccSettle.jar;%JARLIB%\commons-collections.jar;%JARLIB%\commons-dbcp-1.4.jar;%JARLIB%\commons-discovery-0.2.jar;%JARLIB%\commons-logging-1.0.4.jar;%JARLIB%\commons-net-2.2.jar;
set CP_2=%JARLIB%\commons-pool-1.6.jar;%JARLIB%\GoldsignLibDB.jar;%JARLIB%\GoldsignLibJMSClient.jar;%JARLIB%\jaxrpc.jar;%JARLIB%\jms.jar;%JARLIB%\log4j-1.2.8.jar;
set CP_3=%JARLIB%\message.jar;%JARLIB%\ojdbc6.jar;%JARLIB%\weblogic.jar;%JARLIB%\xerces.jar;

%APPDRIVE%
CD %APPHOME%

%JDKHOME%\bin\java -Xmx512m -classpath .;%CP_1%;%CP_2%;%CP_3% com.goldsign.settle.frame.application.AppSettle

pause
