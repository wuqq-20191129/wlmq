echo '设置应用目录'
APP_HOME=/home/wweb/report
CLASSPATH=$APP_HOME/lib
for i in "$APP_HOME"/lib/*.jar
do
 CLASSPATH="$CLASSPATH":"$i"
done
export CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}
echo ${JAVA_HOME}'/bin/java -Xms128m -Xmx1000m  com.goldsign.acc.report.application.MainApp'
${JAVA_HOME}/bin/java -Xms128m -Xmx1000m  com.goldsign.acc.report.application.MainApp
