APP_HOME=/home/wweb/ACCCommu
CLASSPATH=$APP_HOME/lib
for i in "$APP_HOME"/lib/*.jar
do
 CLASSPATH="$CLASSPATH":"$i"
done
export CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}
${JAVA_HOME}/bin/java -Xms128m -Xmx1000m -Dprog=ACCCommu com.goldsign.commu.frame.server.CommuServer