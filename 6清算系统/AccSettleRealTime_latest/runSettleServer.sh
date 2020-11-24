#JAVA_HOME="/usr/java/jdk1.6.0_26"

#LIB_PATH="./lib"

#JAVA_CLASSPATH_1="${LIB_PATH}/AccSettle.jar:${LIB_PATH}/jaxrpc.jar:${LIB_PATH}/xerces.jar"
#JAVA_CLASSPATH_2="${LIB_PATH}/commons-collections.jar:${LIB_PATH}/commons-dbcp-1.4.jar:${LIB_PATH}/commons-discovery-0.2.jar:${LIB_PATH}/commons-logging-1.0.4.jar:${LIB_PATH}/commons-net-2.2.jar:${LIB_PATH}/commons-pool-1.6.jar"
#JAVA_CLASSPATH_3="${LIB_PATH}/GoldsignLibDB.jar:${LIB_PATH}/GoldsignLibJMSClient.jar:${LIB_PATH}/jms.jar:${LIB_PATH}/message.jar:${LIB_PATH}/log4j-1.2.8.jar:${LIB_PATH}/ojdbc6.jar:${LIB_PATH}/weblogic.jar"


##
##echo "JAVA=${JAVA_HOME}/bin/java"
#${JAVA_HOME}/bin/java -Xms128m -Xmx1000m -classpath ${JAVA_CLASSPATH_1}:${JAVA_CLASSPATH_2}:${JAVA_CLASSPATH_3} com.goldsign.settle.frame.application.AppSettle



APP_HOME=/root/acc/settle
CLASSPATH=$APP_HOME/lib
for i in "$APP_HOME"/lib/*.jar
do
 CLASSPATH="$CLASSPATH":"$i"
done
export CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}
${JAVA_HOME}/bin/java -Xms128m -Xmx1000m  com.goldsign.settle.frame.application.AppSettle