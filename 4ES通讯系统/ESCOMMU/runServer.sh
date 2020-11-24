
APP_HOME=/home/acc/commu_es/ESCOMMU
CLASSPATH=$APP_HOME/lib
for i in "$APP_HOME"/lib/*.jar
do
 CLASSPATH="$CLASSPATH":"$i"
done
export CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}

${JAVA_HOME}/bin/java -Xms128m -Xmx1500m -Dprog=escommu com.goldsign.escommu.Main
