set JDKHOME="E:\waccapp\jdk"
set APPHOME=E:\acc_home\settle
set JARLIB=E:\acc_home\settle\lib
set CLASSPATH_1=%JARLIB%\AccSettleRealTime.jar;%JARLIB%\GoldsignLibDB.jar;%JARLIB%\GoldsignLibJMSClient.jar;
set CLASSPATH_2=%JARLIB%\jms.jar;%JARLIB%\message.jar;%JARLIB%\log4j-1.2.8.jar
set CLASSPATH_3=%JARLIB%\ojdbc6.jar;%JARLIB%\jaxrpc.jar;%JARLIB%\xerces.jar;
set CLASSPATH_4=%JARLIB%\commons-dbcp.jar;%JARLIB%\commons-dbcp-1.4.jar;%JARLIB%\commons-discovery-0.2.jar;
set CLASSPATH_5=%JARLIB%\commons-logging-1.0.4.jar;%JARLIB%\commons-net-2.2.jar;%JARLIB%\commons-pool-1.6.jar

set ORACLE_BASE=D:\oracle11g
set ORACLE_HOME=D:\oracle11g\product\11.2.0\dbhome_1
set ORACLE_SID=wacc
                                       

set PATH=%PATH%;%ORACLE_HOME%\bin

%JDKHOME%\bin\java -Dprog=settle -classpath %CLASSPATH_1%;%CLASSPATH_2%;%CLASSPATH_3%;%CLASSPATH_4%;%CLASSPATH_5% com.goldsign.settle.realtime.frame.application.AppSettle  
