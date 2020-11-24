set JDK_HOME="C:\Program Files\Java\jdk1.6.0_45\bin"
set CLASS_PATH_1=.\classes;
SET LIB_PATH=.\lib
set CLASS_PATH_2=%LIB_PATH%\jconn2.jar;%LIB_PATH%\xmlParserAPIs.jar;%LIB_PATH%\ant.jar;%LIB_PATH%\jaxen-core.jar;%LIB_PATH%\jaxen-jdom.jar;%LIB_PATH%\jdom.jar;%LIB_PATH%\saxpath.jar;%LIB_PATH%\xalan.jar;%LIB_PATH%\xerces.jar;%LIB_PATH%\xml-apis.jar;
%JDK_HOME%\java -classpath %CLASS_PATH_1%;%CLASS_PATH_2%  stationDraw.showFrame

pause